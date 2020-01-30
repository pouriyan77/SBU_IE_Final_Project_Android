package ir.ac.sbu.crisismanaging.UI.SelectOptionDialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.ac.sbu.crisismanaging.Pojos.Option;
import ir.ac.sbu.crisismanaging.R;

public class SelectOptionDialog extends DialogFragment
{
    private Button okBtn;
    private Button cancelBtn;
    private RadioGroup radioGroup;
    private TextView titleTextView;

    private EditText fillEditText;
    private List<Option> optionList;
    private String title;

    public SelectOptionDialog(String title, List<Option> optionList, EditText editText)
    {
        this.optionList = optionList;
        this.title = title;
        fillEditText = editText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.select_option_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null)
        {
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT);
        }

        bindViews(view);
        buildRadios();
    }

    private void buildRadios()
    {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/iransans.ttf");

        for (Option option : optionList)
        {
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setTypeface(typeface);
            radioButton.setText(option.getValue());

            radioGroup.addView(radioButton, new RadioGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private void bindViews(View view)
    {
        okBtn = view.findViewById(R.id.okBtn);
        cancelBtn = view.findViewById(R.id.cancelBtn);
        radioGroup = view.findViewById(R.id.radioGroup);
        titleTextView = view.findViewById(R.id.dialogTitleTextView);
        titleTextView.setText(title);

        cancelBtn.setOnClickListener(v -> dismiss());
        okBtn.setOnClickListener(v -> {
            int checkedId = radioGroup.getCheckedRadioButtonId();
            if (checkedId != -1)
            {
                RadioButton checkedRadioButton = view.findViewById(checkedId);
                fillEditText.setText(String.valueOf(checkedRadioButton.getText()));
                dismiss();
            }
            else
            {
                Toast.makeText(getContext(), "لطفا موردی را انتخاب کنید", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
