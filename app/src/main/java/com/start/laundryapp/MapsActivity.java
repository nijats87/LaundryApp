package com.start.laundryapp;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private double longitude;
    private double latitude;

    public String terminalPointName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("longitude", 40.435630);
        latitude = intent.getDoubleExtra("latitude", 49.865741);
        terminalPointName = intent.getStringExtra("terminalPointName");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LatLng terminalPointCoordinates = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(terminalPointCoordinates)
                .title(terminalPointName))
                .showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(terminalPointCoordinates, 13));

        mMap.setMyLocationEnabled(true);


        final LocationManager locationManager = (LocationManager) MapsActivity.this.getSystemService(Context.LOCATION_SERVICE);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    Toast.makeText(MapsActivity.this, "GPS is disable!", Toast.LENGTH_LONG).show();
                } else {

                    Criteria criteria = new Criteria();

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(),
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        Location location = locationManager.getLastKnownLocation(locationManager
                                .getBestProvider(criteria, false));
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        LatLng myCurrentLocation = new LatLng(lat, lon);

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myCurrentLocation, 12));
                    }

                }
                return false;
            }
        });

    }
}
