package com.example.locationsaver;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener,Dialog.DialogListener {


    private AppDatabase mDb;
    protected LocationManager locationManager;
    private double longitude = 0.0;
    private double latitude = 0.0;
    private String location_Name="";
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageButton button = (ImageButton) findViewById(R.id.circle_button);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
        }
        else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            MyViewModel viewmodel=new MyViewModel(getApplicationContext());
            LiveData<List<LocationDetails>> Location=viewmodel.getLocations();
            Location.observe(this, new Observer<List<LocationDetails>>() {
                @Override
                public void onChanged(List<LocationDetails> locationDetails) {
                    adapter = new LocationAdapter(locationDetails, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println("clicked");
                    didTapButton(button);

                    if (latitude != 0.0 && longitude != 0.0) {
                        openDialog();

                    }

                }
            });

        }



    }

    public void openDialog()
    {
            Dialog  dialog=new Dialog(longitude,latitude,mDb);
            dialog.show(getSupportFragmentManager(),"Dialog");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length>0) {
            if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                recreate();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }

    }
    @Override
    public void onLocationChanged(Location location)
    {
        longitude =location.getLongitude();
        latitude =location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }





    @Override
    public void applyTexts(String locationName) {
        location_Name=locationName;
    }


    public void didTapButton(ImageButton button) {

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);


        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        button.startAnimation(myAnim);
    }
    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }

}
