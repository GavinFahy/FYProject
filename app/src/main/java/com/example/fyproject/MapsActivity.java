package com.example.fyproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.fyproject.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

//    LocationManager locationManager;
//    LocationListener locationListener;
//    LatLng userLatLong;
//
//    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                //store user LatLong
//                userLatLong = new LatLng(location.getLatitude(),location.getLongitude());
//                mMap.clear(); //clear old location marker in google map
//                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Your location"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras){
//
//            }
//
//            @Override
//            public void onProviderEnabled(String provider){
//
//            }
//
//            @Override
//            public void onProviderDisabled(String provider){
//
//            }
//        };
//
//        // Check if location permission is granted
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Request permission if not granted
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    1);
//        } else {
//            // Request location updates
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
//
//        //ask for permission with user / location permission
////        askLocationPermission();
//    }

//    private void askLocationPermission() {
//        Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener(){
//            @Override
//            public void onPermissionGranted(PermissionGrantedResponse response){
//                if(ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
//
//                //getting user last location to set the default location marker in the map
//                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                userLatLong = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
//                mMap.clear(); //clear old location marker in google map
//                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Your location"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
//            }
//
//            @Override
//            public void onPermissionDenied(PermissionDeniedResponse response){
//
//            }
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
//
//            }
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token){
//                token.continuePermissionRequest();
//            }
//        }).check();
//    }

    private static final String TAG = "MapsActivity";
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng userLatLong;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (checkMapServices()) {
            getLocationPermission();
        }
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void startLocationUpdates() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(userLatLong).title("Your location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLatLong));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider)
            {
            }
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        // Request location updates
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    startLocationUpdates();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    startLocationUpdates();
                } else {
                    getLocationPermission();
                }
            }
        }
    }
}
