package com.example.thiga.afrofitness_android.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thiga.afrofitness_android.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn,buttonSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSignIn = findViewById(R.id.button_sign_in);
        buttonSignUp = findViewById(R.id.button_sign_up);

        buttonSignIn.setOnClickListener(this);
        buttonSignUp.setOnClickListener(this);
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
