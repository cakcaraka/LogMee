package com.kawung2011.labs.logmee.lib;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Caraka Nur Azmi on 6/12/2014.
 */
public class LocationFinder implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    public boolean is_requesting;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    public LocationFinder(Context ctx,TextView v){
        context = ctx;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        txtLat = v;
        is_requesting = false;
    }

    public void stop(){
        locationManager.removeUpdates(this);
        is_requesting = false;
    }
    public void requestLocation(){
        if(is_requesting){
            locationManager.removeUpdates(this);
            is_requesting = false;
            txtLat.setText("");
        }else {
            is_requesting = true;
            txtLat.setText("requesting location");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat.setText(location.getLatitude() + "," +location.getLongitude());
        locationManager.removeUpdates(this);
        is_requesting = false;

        Log.d("d",location.getLatitude() + "," +location.getLatitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context,"You need to enable location",Toast.LENGTH_SHORT).show();
        requestLocation();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

}
