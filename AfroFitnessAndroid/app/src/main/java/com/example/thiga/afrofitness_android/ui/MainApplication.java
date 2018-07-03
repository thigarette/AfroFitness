package com.example.thiga.afrofitness_android.ui;

import android.app.Application;
import android.content.Context;

import com.example.thiga.afrofitness_android.helper.LocaleHelper;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base,"en")); //default language
    }
}
