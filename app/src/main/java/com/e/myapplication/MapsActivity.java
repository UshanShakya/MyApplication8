package com.e.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import model.LatitudeLongitude;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<LatitudeLongitude> latitudeLongitudes = new ArrayList<>();
        latitudeLongitudes.add(new LatitudeLongitude(27.7052354, 85.3294158, "Softwarica College"));
        latitudeLongitudes.add(new LatitudeLongitude(27.6726, 85.3249, "Patan"));

        CameraUpdate center, zoom;

        for (int i = 0; i < latitudeLongitudes.size(); i++) {
            center = CameraUpdateFactory.newLatLng(new LatLng(latitudeLongitudes.get(i).getLat(), latitudeLongitudes.get(i).getLon()));
            zoom = CameraUpdateFactory.zoomTo(16);


            // Add a marker in Sydney and move the camera
            // LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(new LatLng(latitudeLongitudes.get(i).getLat(), latitudeLongitudes.get(i).getLon())).title(latitudeLongitudes.get(i).getMarker()));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setRotateGesturesEnabled(true);


            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(latitudeLongitudes.get(i).getLat(),latitudeLongitudes.get(i).getLon()))
                    .radius(1)
                    .strokeColor(Color.RED)
                    );

        }

    }
}
