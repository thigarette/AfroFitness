package com.example.thiga.afrofitness_android.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.thiga.afrofitness_android.R;
import com.example.thiga.afrofitness_android.helper.LocaleHelper;
import com.example.thiga.afrofitness_android.helper.SharedPrefManager;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn,buttonSignUp;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"en"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
        }

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);

        Paper.init(this);

        String language = Paper.book().read("language");
        if(language==null){
            Paper.book().write("language","en");
        }
        
        updateView((String)Paper.book().read("language"));

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
    }

    private void updateView(String lang) {
        Context context = LocaleHelper.setLocale(this,lang);
        Resources resources = context.getResources();

        buttonSignIn.setText(resources.getString(R.string.sign_in));
        buttonSignUp.setText(resources.getString(R.string.sign_up));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (view == buttonSignUp) {
            startActivity(new Intent(this, SignUpActivity.class));
        }

    }
}
