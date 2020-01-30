package ir.ac.sbu.crisismanaging.UI;

import androidx.appcompat.app.AppCompatActivity;
import ir.ac.sbu.crisismanaging.Pojos.Form;
import ir.ac.sbu.crisismanaging.Pojos.Location;
import ir.ac.sbu.crisismanaging.R;
import ir.ac.sbu.crisismanaging.Service.WebService;
import ir.ac.sbu.crisismanaging.UI.FormsActivity.FormsActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(this, FormsActivity.class));
        }, 2000);

    }
}
