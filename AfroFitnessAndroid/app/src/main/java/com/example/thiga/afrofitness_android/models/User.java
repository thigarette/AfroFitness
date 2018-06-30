package com.example.thiga.afrofitness_android.models;

public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String gender;

    //Constructors
    public User(String first_name,String last_name,String email,String password,String gender){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    public User(String first_name,String last_name,String email,String gender){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.gender = gender;
    }

    public User(int id,String first_name,String last_name,String email,String password,String gender){
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }
    //getters
    public int getId(){
        return id;
    }

    public String getFirstName(){
        return first_name;
    }

    public String getLastName(){
        return last_name;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String getGender() {
        return gender;
    }
}
