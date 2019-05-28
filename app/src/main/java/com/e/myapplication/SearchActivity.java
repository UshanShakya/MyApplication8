package com.e.myapplication;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import model.LatitudeLongitude;

public class SearchActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AutoCompleteTextView etSearch;
    private Button btnSearch;
    private List<LatitudeLongitude> latitudeLongitudes;

    Marker markerN;
    CameraUpdate center,zoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        etSearch= findViewById(R.id.etSearch);
        btnSearch= findViewById(R.id.btnSearch);
        
        fillAndSet();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etSearch.getText().toString())){
                    etSearch.setError("Please enter a Location");
                    return;
                }
                int position = SearchArrayList(etSearch.getText().toString());
                if (position>-1){
                    loadMap(position);
                }
                else {
                    Toast.makeText(getApplicationContext(),"No such Location found"+etSearch.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            private void loadMap(int position) {
                if (markerN!=null){
                    markerN.remove();
                }
                double lat = latitudeLongitudes.get(position).getLat();
                double lon = latitudeLongitudes.get(position).getLon();
                String marker = latitudeLongitudes.get(position).getMarker();

                center = CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
                zoom = CameraUpdateFactory.zoomTo(16);


                // Add a marker in Sydney and move the camera
                // LatLng sydney = new LatLng(-34, 151);
                markerN = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(marker));
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(true);


                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitudeLongitudes.get(position).getLat(),latitudeLongitudes.get(position).getLon()))
                        .radius(1)
                        .strokeColor(Color.RED)
                );
            }
        });
    }

    private int SearchArrayList(String toString) {
        for (int i =0;i<latitudeLongitudes.size();i++){
            if (latitudeLongitudes.get(i).getMarker().contains(toString)){
                return i;
            }

        }
        return -1;
    }

    private void fillAndSet() {
        latitudeLongitudes = new ArrayList<>();
        latitudeLongitudes.add(new LatitudeLongitude(27.6644,85.3188,"Patan"));
        latitudeLongitudes.add(new LatitudeLongitude(27.7172,85.3240,"Kathmandu"));
        latitudeLongitudes.add(new LatitudeLongitude(27.6710,85.4298,"Bhaktapur"));

        String[] data = new String[latitudeLongitudes.size()];

        for (int i=0; i<data.length;i++){
            data[i] = latitudeLongitudes.get(i).getMarker();
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(
                SearchActivity.this,android.R.layout.activity_list_item,data
        );
        etSearch.setAdapter(adapter);
        etSearch.setThreshold(1);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        center = CameraUpdateFactory.newLatLng(new LatLng(50,85.3239605));
        zoom = CameraUpdateFactory.zoomTo(16);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);


    }
}
