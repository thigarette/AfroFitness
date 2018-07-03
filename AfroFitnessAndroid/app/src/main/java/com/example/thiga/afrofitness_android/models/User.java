package com.example.thiga.afrofitness_android.models;

public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String gender;
    private String preferred_workout;
    private int age;
    private int weight;
    private int target_weight;
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

    public User(int id,String email,String gender){
        this.id = id;
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

    public User(int id, String first_name, String last_name, String email, String password, String gender, String preferred_workout, int age, int weight, int target_weight) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.preferred_workout = preferred_workout;
        this.age = age;
        this.weight = weight;
        this.target_weight = target_weight;
    }

    public User(int id, String first_name, String last_name, String email, String gender, String preferred_workout, int age, int weight, int target_weight) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.gender = gender;
        this.preferred_workout = preferred_workout;
        this.age = age;
        this.weight = weight;
        this.target_weight = target_weight;
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

    public String getPreferredWorkout() {
        return preferred_workout;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getTargetWeight() {
        return target_weight;
    }
}
