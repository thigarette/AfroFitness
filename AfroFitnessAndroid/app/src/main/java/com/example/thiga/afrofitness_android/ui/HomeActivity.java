package com.example.thiga.afrofitness_android.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.helper.LocaleHelper;
import com.example.thiga.afrofitness_android.helper.SharedPrefManager;
import com.example.thiga.afrofitness_android.ui.fragments.HomeFragment;
import com.example.thiga.afrofitness_android.ui.fragments.InstructorsFragment;
import com.example.thiga.afrofitness_android.ui.fragments.ProfileFragment;
import com.example.thiga.afrofitness_android.ui.fragments.SessionsFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.sql.Connection;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,SessionsFragment.OnFragmentInteractionListener,
        InstructorsFragment.OnFragmentInteractionListener{

    private TextView textViewFirstName;
    private TextView textViewLastName;
    private static final String TAG = "Home";
    private static final int DIALOG_REQUEST_FOR_ERROR = 8002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(areServicesAvailable()) {
                    startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        View headerView = navigationView.getHeaderView(0);
//        textViewFirstName = (TextView) headerView.findViewById(R.id.text_view_first_name);
//        textViewLastName = (TextView) headerView.findViewById(R.id.text_view_last_name);
//        textViewFirstName.setText(SharedPrefManager.getInstance(this).getUser().getFirstName());
//        textViewLastName.setText(SharedPrefManager.getInstance(this).getUser().getLastName());

        //loading home fragment by default
        displaySelectedScreen(R.id.nav_home);

    }

    public boolean areServicesAvailable(){
        Log.d(TAG,"areServicesAvailable: checking google services version");
        int av = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HomeActivity.this );
        if(av == ConnectionResult.SUCCESS){
            Log.d(TAG,"areServicesAvailable: Google Play Services");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(av)){
            Log.d(TAG,"areServicesAvailable: An error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeActivity.this,av,DIALOG_REQUEST_FOR_ERROR);
            dialog.show();
        }
        else{
            Toast.makeText(this,"Your device is unable to make map requests.",Toast.LENGTH_SHORT);
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(item.getItemId()==R.id.language_en){
            Paper.book().write("language","en");
            updateView((String)Paper.book().read("language"));
        }
        else if(item.getItemId()==R.id.language_sw){
            Paper.book().write("language","sw");
            updateView((String)Paper.book().read("language"));
        }
        return true;
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();
        context.getResources();
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        finish();
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_sessions:
                fragment = new SessionsFragment();
                break;
            case R.id.nav_instructors:
                fragment = new InstructorsFragment();
                break;
            case R.id.nav_logout:
                logout();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void logout(){
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
