package com.example.thiga.afrofitness_android.models;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("error")
    private Boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public Result(Boolean error,String message,User user){
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean getError(){
        return error;
    }

    public User getUser(){
        return user;
    }
}
