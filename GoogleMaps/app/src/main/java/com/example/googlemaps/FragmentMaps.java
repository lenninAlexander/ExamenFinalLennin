package com.example.googlemaps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentMaps extends SupportMapFragment implements OnMapReadyCallback {

    double lat,lon;
    public  FragmentMaps(){}


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
       View rootView= super.onCreateView(inflater, container, savedInstanceState);
       if(getArguments()!=null)
       {
           this.lat=getArguments().getDouble("lat");
           this.lon=getArguments().getDouble("lon");
       }
       getMapAsync(this);
       return rootView;
    }

    @Override
    public void onMapReady( GoogleMap googleMap) {
        LatLng latLng=new LatLng(lat,lon);
        float zoom =17 ;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng));
        UiSettings settings=googleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
    }
}
