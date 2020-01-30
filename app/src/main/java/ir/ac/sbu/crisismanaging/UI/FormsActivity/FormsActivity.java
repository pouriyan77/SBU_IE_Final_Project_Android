package ir.ac.sbu.crisismanaging.UI.FormsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ir.ac.sbu.crisismanaging.Pojos.FormDescriptor;
import ir.ac.sbu.crisismanaging.R;
import ir.ac.sbu.crisismanaging.UI.Adapters.FormsRecyclerAdapter;
import ir.ac.sbu.crisismanaging.UI.FillFormActvity.FillFormActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class FormsActivity extends AppCompatActivity implements FormsActivityContract.View,
        FormsRecyclerAdapter.OnItemClickedListener
{
    public static final String FORM_DESC_KEY = "FORM_DESC_KEY";

    private static final String TITLE = "انتخاب فرم";
    private TextView toolbarTitleTextView;
    private ImageView toolbarImg;
    private RecyclerView formsRecyclerView;
    private RelativeLayout retryBtn;
    private FrameLayout progressBar;

    private FormsActivityPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        presenter = new FormsActivityPresenterImpl(this);
        findViews();
        setupToolbar();
        getFormsFromServer();
    }

    private void setupToolbar()
    {
        toolbarTitleTextView.setText(TITLE);
        toolbarImg.setImageResource(R.drawable.retry_toolbar);
        toolbarImg.setOnClickListener(v -> getFormsFromServer());
    }

    private void getFormsFromServer()
    {
        toolbarImg.setVisibility(View.GONE);
        retryBtn.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        presenter.getFormDescriptorsFromServer();
    }

    private void setupRecyclerView(List<FormDescriptor> formDescriptorList)
    {
        FormsRecyclerAdapter formsRecyclerAdapter = new FormsRecyclerAdapter(formDescriptorList,
                this);
        formsRecyclerView.setAdapter(formsRecyclerAdapter);
    }

    private void findViews()
    {
        toolbarTitleTextView = findViewById(R.id.toolbarTitleTextView);
        toolbarImg = findViewById(R.id.toolbarImg);
        formsRecyclerView = findViewById(R.id.formsRecyclerView);
        retryBtn = findViewById(R.id.retryBtn);
        progressBar = findViewById(R.id.progressBar);
        retryBtn.setOnClickListener(v -> getFormsFromServer());
    }

    @Override
    public void onItemClicked(String id)
    {
        Intent intent = new Intent(this, FillFormActivity.class);
        intent.putExtra(FORM_DESC_KEY, id);
        startActivity(intent);
    }

    @Override
    public void updateRecyclerView(List<FormDescriptor> formDescriptorList)
    {
        setupRecyclerView(formDescriptorList);
    }

    @Override
    public void hideProgressBar()
    {
        progressBar.setVisibility(View.GONE);
        toolbarImg.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRetryBtn()
    {
        retryBtn.setVisibility(View.VISIBLE);
    }
}
