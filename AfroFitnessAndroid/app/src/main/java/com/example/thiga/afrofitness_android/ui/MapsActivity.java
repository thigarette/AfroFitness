package com.example.thiga.afrofitness_android.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.api.ApiService;
import com.example.thiga.afrofitness_android.api.ApiUrl;
import com.example.thiga.afrofitness_android.models.GymLocations;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Boolean permissionsGranted;
    private static final int REQUEST_CODE = 1234;
    private static final String TAG = "Maps";
    private FusedLocationProviderClient fused;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getPermission();
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
               return null;
            }

            @Override
            public View getInfoContents(Marker marker) {


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiUrl.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiService service = retrofit.create(ApiService.class);

                Call<GymLocations> call = service.getGymLocations();
                call.enqueue(new Callback<GymLocations>() {
                    @Override
                    public void onResponse(Call<GymLocations> call, Response<GymLocations> response) {

                    }

                    @Override
                    public void onFailure(Call<GymLocations> call, Throwable t) {

                    }
                });
                return null;            }
        });
    }

    private void getPermission() {
        Log.d(TAG, "Attaining location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                permissionsGranted = true;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            } else {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsGranted = false;
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            permissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permission Not Granted");
                            return;
                        }
                    }
                    permissionsGranted = true;
                    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                    Log.d(TAG, "onRequestPermissionsResult: Permission Granted");

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                }
            }
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: duh");
        fused = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (permissionsGranted) {
                Task location = fused.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Location Found");
                            Location curLoc = (Location) task.getResult();
                            moveCamera(new LatLng(curLoc.getLatitude(), curLoc.getLongitude()), 15f);
                        } else {
                            Toast.makeText(MapsActivity.this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                location.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
//                    }
//                });
//                location.addOnCanceledListener(new OnCanceledListener() {
//                    @Override
//                    public void onCanceled() {
//                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
//                    }
//                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom)); // Moves camera to specified coordinates
    }

    public void GPSCheck(Context context){
        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000/2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse>task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                //getDeviceLocation();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof ResolvableApiException){
                    try{
                        ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                        resolvableApiException.startResolutionForResult(MapsActivity.this,REQUEST_CODE);
                        Intent intent = getIntent();
                        onActivityResult(REQUEST_CODE,8090,intent);
                    }
                    catch (IntentSender.SendIntentException sendEx){

                    }
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        if (permissionsGranted) {
            GPSCheck(context);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)Support
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }
}
