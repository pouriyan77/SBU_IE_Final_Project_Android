package ir.ac.sbu.crisismanaging.UI.FillFormActvity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ir.ac.sbu.crisismanaging.Pojos.Field;
import ir.ac.sbu.crisismanaging.Pojos.Form;
import ir.ac.sbu.crisismanaging.Pojos.FormDescriptor;
import ir.ac.sbu.crisismanaging.Pojos.Location;
import ir.ac.sbu.crisismanaging.R;
import ir.ac.sbu.crisismanaging.UI.GoogleMapActivity.GoogleMapActivity;
import ir.ac.sbu.crisismanaging.Utils.JDF;
import ir.ac.sbu.crisismanaging.Utils.UiUtils;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ir.ac.sbu.crisismanaging.UI.FormsActivity.FormsActivity.FORM_DESC_KEY;

public class FillFormActivity extends AppCompatActivity implements FillFormActivityContract.View
{
    public static final int LOCATION_REQUEST_CODE = 203;

    private enum InputType
    {
        number, text, date, location
    }

    public static final String LOCATION_KEY = "LOCATION_KEY";
    public static final String LAT_KEY = "LAT_KEY";
    public static final String LON_KEY = "LON_KEY";

    private TextView toolbarTitleTextView;
    private ImageView toolbarImg;
    private LinearLayout fieldsLinearLayout;
    private RelativeLayout retryBtn;
    private FrameLayout progressBar;
    private Button sendFormBtn;
    private Button refreshFormBtn;
    private ViewAndKey currentLocViewAndKey;

    private String formDescriptorId;
    private FillFormActivityContract.Presenter presenter;
    private List<ViewAndKey> viewAndKeyList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);
        presenter = new FillFormPresenterImpl(this);
        setupIntent();
        findViews();
        setupToolbar();
        presenter.getFormDescriptorFromServer(formDescriptorId);
    }

    private void generateForm(List<Field> fields)
    {
        viewAndKeyList = new ArrayList<>();
        for (Field field : fields)
        {
            ViewAndKey viewAndKey = new ViewAndKey();

            Space space = new Space(this);
            space.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , UiUtils.dpToPx(8, this)));
            TextInputLayout textInputLayout = new TextInputLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER;
            textInputLayout.setLayoutParams(layoutParams);
            textInputLayout.setHint(field.getTitle().concat(field.getRequired() ? "*" : ""));
            textInputLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            editText.setTextDirection(View.TEXT_DIRECTION_RTL);
//            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            editText.setHintTextColor(getResources().getColor(R.color.gray));
            editText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/iransans.ttf"));
            editText.setTextSize(UiUtils.dpToPx(8, this));
            InputType inputType = InputType.valueOf(field.getType());
            switch (inputType)
            {
                case text:
                    editText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                    break;
                case number:
                    editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                    break;
                case date:
                    Drawable img = getResources().getDrawable(R.drawable.ic_date);
                    int imgSize = UiUtils.dpToPx(24, this);
                    img.setBounds(0, 0, imgSize, imgSize);
                    editText.setCompoundDrawables(img, null, null, null);
                    editText.setFocusable(false);
                    editText.setOnClickListener(v -> showTimePicker(this, editText));
                    break;
                case location:
                    Drawable img1 = getResources().getDrawable(R.drawable.ic_google_map);
                    int imgSize1 = UiUtils.dpToPx(24, this);
                    img1.setBounds(0, 0, imgSize1, imgSize1);
                    editText.setCompoundDrawables(img1, null, null, null);
                    editText.setFocusable(false);
//                    editText.setMaxLines(1);
                    editText.setSingleLine();
                    Intent intent = new Intent(new Intent(this,
                            GoogleMapActivity.class));
                    intent.putExtra(LOCATION_KEY, field.getTitle());
                    editText.setOnClickListener(v ->
                    {
                        if (isMapServiceOk())
                        {
                            currentLocViewAndKey = viewAndKey;
                            startActivityForResult(intent, LOCATION_REQUEST_CODE);
                        }
                    });
            }
            textInputLayout.addView(editText);
            fieldsLinearLayout.addView(space);
            fieldsLinearLayout.addView(textInputLayout);

            viewAndKey.key = field.getName();
            viewAndKey.required = field.getRequired();
            viewAndKey.editText = editText;
            viewAndKey.type = InputType.valueOf(field.getType());
            viewAndKeyList.add(viewAndKey);
        }

        for (int i = 0; i < viewAndKeyList.size() - 1; i++)
        {
            ViewAndKey viewAndKey = viewAndKeyList.get(i);
            viewAndKey.editText.setNextFocusDownId(viewAndKeyList.get(i + 1).editText.getId());
        }
    }

    private void showTimePicker(Context context, TextView textView)
    {
        JDF jdf = new JDF();
        PersianCalendar initDate = new PersianCalendar();
        initDate.setPersianDate(PersianDatePickerDialog.THIS_YEAR, jdf.getIranianMonth(), jdf.getIranianDay());

        PersianDatePickerDialog persianDatePicker = new PersianDatePickerDialog(context)
                .setPositiveButtonString("تایید")
                .setNegativeButton("انصراف")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR + 1)
                .setInitDate(initDate)
                .setActionTextColor(Color.BLACK)
                .setListener(new Listener()
                {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar)
                    {
                        String date = String.valueOf(persianCalendar.getPersianYear()).concat("-")
                                .concat(String.valueOf(persianCalendar.getPersianMonth()))
                                .concat("-").concat(String.valueOf(persianCalendar.getPersianDay()));
                        textView.setText(date);
                    }

                    @Override
                    public void onDismissed()
                    {

                    }
                });

        persianDatePicker.show();
    }

    private void setupIntent()
    {
        Intent intent = getIntent();
        formDescriptorId = intent.getStringExtra(FORM_DESC_KEY);
    }

    private void setupToolbar()
    {
        toolbarImg.setImageResource(R.drawable.ic_arrow_back);
        toolbarImg.setOnClickListener(v -> finish());
    }

    private void findViews()
    {
        toolbarTitleTextView = findViewById(R.id.toolbarTitleTextView);
        toolbarImg = findViewById(R.id.toolbarImg);
        fieldsLinearLayout = findViewById(R.id.fieldsLinearLayout);
        sendFormBtn = findViewById(R.id.sendFormBtn);
        sendFormBtn.setOnClickListener(v -> sendFormToServer());
        refreshFormBtn = findViewById(R.id.refreshFormBtn);
        refreshFormBtn.setOnClickListener(v -> refreshForm());
        progressBar = findViewById(R.id.progressBar);
        retryBtn = findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(v -> presenter.getFormDescriptorFromServer(formDescriptorId));
    }

    private void refreshForm()
    {
        for (ViewAndKey viewAndKey : viewAndKeyList)
        {
            viewAndKey.editText.setText("");
        }
    }

    private void sendFormToServer()
    {
        progressBar.setVisibility(View.VISIBLE);
        Form form = new Form(formDescriptorId);
        boolean canSend = checkFields(form);
        if (canSend)
        {
            presenter.sendFormToServer(form);
        } else
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "لطفا همه فیلدهای اجباری را پر کنید",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkFields(Form form)
    {
        Map<String, Object> filledFields = new HashMap<>();
        for (ViewAndKey viewAndKey : viewAndKeyList)
        {
            String editTextValue = viewAndKey.editText.getText().toString().trim();
            if (viewAndKey.required)
            {
                if (editTextValue.isEmpty())
                {
                    return false;
                }
            }
            if (viewAndKey.type == InputType.number)
            {
                filledFields.put(viewAndKey.key, Double.valueOf(editTextValue));
            } else if (viewAndKey.type == InputType.location)
            {
                filledFields.put(viewAndKey.key, viewAndKey.location);
            } else
            {
                filledFields.put(viewAndKey.key, editTextValue);
            }
        }
        form.setFields(filledFields);
        return true;
    }

    @Override
    public void onFormSentListener()
    {
        refreshForm();
    }

    @Override
    public void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setupForm(FormDescriptor formDescriptor)
    {
        toolbarTitleTextView.setText(formDescriptor.getTitle());
        generateForm(formDescriptor.getFields());
    }

    @Override
    public void hideProgressBar()
    {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetryBtn()
    {
        retryBtn.setVisibility(View.VISIBLE);
    }

    public boolean isMapServiceOk()
    {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS)
        {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,
                    available, 9001);
            dialog.show();
        } else
        {
            Toast.makeText(this, "خطا در گوگل پلی سرویس", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_REQUEST_CODE)
        {
            if (data != null)
            {
                double lat = data.getDoubleExtra(LAT_KEY, 0);
                double lon = data.getDoubleExtra(LON_KEY, 0);
                currentLocViewAndKey.location = new Location(lat, lon);
                currentLocViewAndKey.editText.setText(String.format(getResources().getString(
                        R.string.latLongStr), String.valueOf(lon), String.valueOf(lat)));
            }

        }
    }

    private class ViewAndKey
    {
        private EditText editText;
        private String key;
        private boolean required;
        private InputType type;
        private Location location;
    }
}
