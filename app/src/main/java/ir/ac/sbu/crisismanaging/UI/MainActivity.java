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
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, FormsActivity.class));
//        Form form = new Form();
//        form.getFields().put("name", "Ali");
//        form.getFields().put("age", 23);
//        form.getFields().put("age2", 46);
//        form.getFields().put("location", new Location(23.57, 12.46));
//        WebService.getWebService().sendForm(form).enqueue(new Callback<Object>()
//        {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response)
//            {
//                System.out.println("Sfsadsfc");
//                if (true)
//                {
//                    int i = 10;
//                    i++;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t)
//            {
//                System.out.println("jdsfgsjkv");
//            }
//        });

    }
}
