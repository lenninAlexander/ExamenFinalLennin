package com.example.googlemaps;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MapsActivity extends AppCompatActivity {

    TextView tvMensaje;
    private static final long MIN_TIME=10000000;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvMensaje=findViewById(R.id.tvMensaje);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},10000000);
        }else
        {
            iniciarLocalizacion();
        }
        
    }
    protected  void iniciarLocalizacion(){
        LocationManager locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion local = new Localizacion();
        local.setMainActivity(this,tvMensaje);
        final boolean gpsEnabled =locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnabled){
            Intent intent= new Intent (Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
        !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},10000000);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME,0,local);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,0,local);
        tvMensaje.setText("Localizacion Agregada");
    }
    public  void onRequestPermissionsResult(int requestCode , String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10000000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarLocalizacion();
                return;
            }
        }
    }
}