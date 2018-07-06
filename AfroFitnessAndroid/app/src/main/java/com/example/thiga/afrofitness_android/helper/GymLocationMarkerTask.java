package com.example.thiga.afrofitness_android.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.example.thiga.afrofitness_android.ui.MapsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GymLocationMarkerTask extends AsyncTask<Void,Void,String>{
    private static final String LOG_TAG = "Gym Locations";

    private static final String SERVICE_URL = "http://afrofitness.cf/locations";

    //GoogleMap mMap;

    @Override
    protected String doInBackground(Void... args) {

        HttpURLConnection conn = null;
        final StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to API service", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return json.toString();
    }

    // Executed after the complete execution of doInBackground() method
    @Override
    protected void onPostExecute(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("locations");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                LatLng latLng = new LatLng(jsonObj.getDouble("latitude"),
                        jsonObj.getDouble("longitude"));

                //move CameraPosition on first result
//                if (i == 0) {
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(latLng).zoom(13).build();
//                    MapsActivity.mMap.animateCamera(CameraUpdateFactory
//                            .newCameraPosition(cameraPosition));
//                }

                MapsActivity.mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title(jsonObj.getString("location_name"))
                        .snippet("Opening Time: "+jsonObj.getString("opening_time")+"\n"+
                        "Closing Time: "+jsonObj.getString("closing_time"))
                        .position(latLng));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error processing JSON", e);
        }

    }}
