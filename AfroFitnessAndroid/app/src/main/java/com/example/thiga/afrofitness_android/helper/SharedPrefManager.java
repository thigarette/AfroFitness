package com.example.thiga.afrofitness_android.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.thiga.afrofitness_android.models.User;

public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mContext;

    private static final String SHARED_PREF_NAME = "afrofitness";

    private static final String KEY_USER_ID = "keyuserid";
    private static final String KEY_USER_FIRST_NAME = "keyuserfirstname";
    private static final String KEY_USER_LAST_NAME = "keyuserlastname";
    private static final String KEY_USER_EMAIL = "keyuseremail";
    private static final String KEY_USER_PREFERRED_LOCATION = "keyuserpreferredlocation";
    private static final String KEY_USER_AGE = "keyuserage";
    private static final String KEY_USER_GENDER = "keyusergender";
    private static final String KEY_USER_WEIGHT = "keyuserweight";
    private static final String KEY_USER_TARGET_WEIGHT = "keyusertargetweight";

    private SharedPrefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean login(User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_USER_LAST_NAME, user.getLastName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_GENDER, user.getGender());
        editor.putString(KEY_USER_PREFERRED_LOCATION, user.getPreferredWorkout());
        editor.putInt(KEY_USER_AGE, user.getAge());
        editor.putInt(KEY_USER_WEIGHT, user.getWeight());
        editor.putInt(KEY_USER_TARGET_WEIGHT, user.getTargetWeight());
        editor.apply();
        return true;
    }


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_EMAIL,null)!=null)
            return true;
        return false;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_USER_ID,0),
                sharedPreferences.getString(KEY_USER_FIRST_NAME,null),
                sharedPreferences.getString(KEY_USER_LAST_NAME,null),
                sharedPreferences.getString(KEY_USER_EMAIL,null),
                sharedPreferences.getString(KEY_USER_PREFERRED_LOCATION,null),
                sharedPreferences.getInt(KEY_USER_AGE,0),
                sharedPreferences.getString(KEY_USER_GENDER,null),
                sharedPreferences.getInt(KEY_USER_WEIGHT,0),
                sharedPreferences.getInt(KEY_USER_TARGET_WEIGHT,0)
        );
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
}
