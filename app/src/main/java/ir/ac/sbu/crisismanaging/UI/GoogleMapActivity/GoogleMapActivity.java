package ir.ac.sbu.crisismanaging.UI.GoogleMapActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ir.ac.sbu.crisismanaging.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import static ir.ac.sbu.crisismanaging.UI.FillFormActvity.FillFormActivity.LAT_KEY;
import static ir.ac.sbu.crisismanaging.UI.FillFormActvity.FillFormActivity.LOCATION_KEY;
import static ir.ac.sbu.crisismanaging.UI.FillFormActvity.FillFormActivity.LOCATION_REQUEST_CODE;
import static ir.ac.sbu.crisismanaging.UI.FillFormActvity.FillFormActivity.LON_KEY;

public class GoogleMapActivity extends AppCompatActivity
{
    private static final int REQUEST_CODE = 110;
    private TextView toolbarTitleTextView;
    private ImageView toolbarImg;
    private ImageView toolbarGpsBtn;
    private Button confirmBtn;

    private GoogleMap googleMap;
    private boolean hasPermission;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        findViews();
        setupToolbar();
        getLocationPermission();
    }

    private void findViews()
    {
        toolbarTitleTextView = findViewById(R.id.toolbarTitleTextView);
        toolbarImg = findViewById(R.id.toolbarImg);
        toolbarGpsBtn = findViewById(R.id.toolbarRightImg);
        confirmBtn = findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(v -> {
            if (locationMarker != null)
            {
                Intent intent = new Intent();
                intent.putExtra(LON_KEY, locationMarker.getPosition().longitude);
                intent.putExtra(LAT_KEY, locationMarker.getPosition().latitude);
                setResult(LOCATION_REQUEST_CODE, intent);
                finish();
            }
        });
    }

    private void setupToolbar()
    {
        toolbarImg.setImageResource(R.drawable.ic_arrow_back);
        toolbarImg.setOnClickListener(v -> finish());
        toolbarTitleTextView.setText("انتخاب ".concat(getIntent().getStringExtra(LOCATION_KEY)));
        toolbarGpsBtn.setImageResource(R.drawable.ic_location);
        toolbarGpsBtn.setOnClickListener(v -> {
            if (hasPermission)
            {
                getCurrentLocation();
            }
        });
    }

    private void initMap()
    {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (supportMapFragment != null)
        {
            supportMapFragment.getMapAsync(googleMap ->
            {
                this.googleMap = googleMap;
                if (hasPermission)
                {
                    getCurrentLocation();
                }
                googleMap.setOnMapLongClickListener(latLng ->
                {
                    moveCamera(latLng);
                });
            });
        }
    }

    private void getCurrentLocation()
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Task locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnCompleteListener(task ->
        {
            if (task.isSuccessful())
            {
                Location currentLocation = (Location) task.getResult();
                if (currentLocation != null)
                {
                    moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "لطفا مکان یاب را روشن کرده و برنامه را دوباره اجرا کنید",
                            Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(this, "خطا در دریافت مکان کاربر", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getLocationPermission()
    {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE);
            return;
        }
        hasPermission = true;
        initMap();
    }

    private void moveCamera(LatLng latLng)
    {
        if (locationMarker != null)
        {
            locationMarker.remove();
        }
        locationMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title(
                getIntent().getStringExtra(LOCATION_KEY)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12f);
        googleMap.animateCamera(cameraUpdate);
        Toast.makeText(this, String.format(getResources().getString(
                R.string.latLongStr), String.valueOf(latLng.longitude),
                String.valueOf(latLng.latitude)), Toast.LENGTH_LONG).show();
    }
}
