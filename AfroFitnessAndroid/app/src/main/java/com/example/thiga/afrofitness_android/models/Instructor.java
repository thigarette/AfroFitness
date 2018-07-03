package com.example.thiga.afrofitness_android.models;

import java.util.ArrayList;

public class Instructor {
    private int id;
    private String name;
    private String phone_number;
    private String email;
    private String gender;

    public Instructor(){

    }

    public Instructor(int id, String name, String phone_number, String email, String gender) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.gender = gender;
    }

    public Instructor(String name, String phone_number, String email, String gender) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
