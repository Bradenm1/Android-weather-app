package bit.mckebk2.mobileprojectweatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

/*----------------------------------------------------
-- A class used to get the current location of
-- the device
----------------------------------------------------*/
public class GPS {

    // Constants
    private static final int LOCATION_REQUEST_PERMISSION_CODE = 42;
    private static final int MIN_TIME_BW_UPDATES = 10000;
    private static final int MIN_DISTANCE_CHANGE_FOR_UPDATES = 10000;

    // Fields
    public static LocationManager locationManager;
    public static String providerName;
    public static LatLng devicePosition;
    private final MainActivity activity;

    /*----------------------------------------------------
    -- Constructor()
    ----------------------------------------------------*/
    public GPS(MainActivity activity) {this.activity = activity;}

    /*----------------------------------------------------
    -- getCurrentLatLng()
    -- Return current position of the device
    ----------------------------------------------------*/
    public LatLng getCurrentLatLng() {

        // Get the location service
        locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        // Check if users has given permission to get device position
        boolean locationPermissionOK = checkLocationPermission();
        if (locationPermissionOK) {
            providerName = locationManager.getBestProvider(new Criteria(), false);
            locationManager.requestLocationUpdates(providerName, 1000, 1, new CustomLocationListener());
        } else {
            requestLocationPermission();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, new CustomLocationListener());
        // Get current location given the type of connection
        //Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location currentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        // Check if the device already has a position
        if (devicePosition != null) return devicePosition;
        // Get position
        if (currentLocation != null) {
            double lat = currentLocation.getLatitude();
            double lng = currentLocation.getLongitude();

            return new LatLng(lat, lng);
        } else {
            // Else return null
            return null;
        }
    } // End getCurrentLatLng

    /*----------------------------------------------------
    -- checkLocationPermission()
    -- @return Boolean: if it was ture or false
    ----------------------------------------------------*/
    public boolean checkLocationPermission() {
        int fineLocationOK = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocationOK = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);

        return !((fineLocationOK != PackageManager.PERMISSION_GRANTED) || (coarseLocationOK != PackageManager.PERMISSION_GRANTED));
    } // End checkLocationPermission

    /*----------------------------------------------------
    -- requestLocationPermission()
    -- Requests permission for location
    ----------------------------------------------------*/
    private void requestLocationPermission() {
        String[] permissionsIWant = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(activity, permissionsIWant, LOCATION_REQUEST_PERMISSION_CODE);
    } // End requestLocationPermission

    /*----------------------------------------------------
    -- Custom location listener
    ----------------------------------------------------*/
    public static class CustomLocationListener implements LocationListener {
        @Override
        // Called when the location has changed.
        public void onLocationChanged(Location location) {
            devicePosition = new LatLng(location.getLatitude(), location.getLongitude());
        } // End onLocationChanged

        @Override
        // Called when the provider is disabled by the user.
        public void onStatusChanged(String s, int i, Bundle bundle) { } // End onStatusChanged

        @Override
        // Called when the provider is enabled by the user.
        public void onProviderEnabled(String s) {
            providerName = s;
        } // End onProviderEnabled

        @Override
        // Called when the provider status changes.
        public void onProviderDisabled(String s) { } // End onProviderDisabled
    } // End CustomLocationListener
} // End Class
