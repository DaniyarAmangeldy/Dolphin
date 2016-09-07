package com.sample.drawer;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.github.fabtransitionactivity.SheetLayout;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Location extends FragmentActivity implements OnMapReadyCallback,SheetLayout.OnFabAnimationEndListener {
    SheetLayout mSheetLayout;
    private GoogleMap mMap;
    private Intent intent;
    FloatingActionButton myFab;
    double latitude=0;
    double longitude=0;
    Intent received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setTheme(R.style.AppThemeNonDrawer);
        received = getIntent();
        intent = new Intent();
        mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheetLocation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
         myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.hide();
        mSheetLayout.setFab(myFab);
        mSheetLayout.setFabAnimationEndListener(this);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetLayout.expandFab();
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(received.hasExtra("latitude")){
            latitude=received.getDoubleExtra("latitude",0);
            longitude=received.getDoubleExtra("longitude",0);
            LatLng pos = new LatLng(latitude,longitude);
            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            intent.putExtra("latitude",latitude);
            intent.putExtra("longitude",longitude);
            myFab.show();
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude)).zoom(13).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }else {

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(43.255058, 76.912628)).zoom(13).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
            mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(point)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                latitude = point.latitude;
                longitude = point.longitude;
                myFab.show();

            }

        });






    }
    public void onFabAnimationEnd() {

        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        setResult(RESULT_OK,intent);
        finish();
    }


}



