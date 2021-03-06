package com.example.thiga.afrofitness_android.models;

public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String preferred_workout_location;
    private int age;
    private String gender;
    private int weight_kg;
    private int target_weight;
    //Constructors
    public User(){
        
    }
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

    public User(int id, String first_name, String last_name, String email, String password, String preferred_workout, int age, String gender, int weight, int target_weight) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.preferred_workout_location = preferred_workout;
        this.age = age;
        this.gender = gender;
        this.weight_kg = weight;
        this.target_weight = target_weight;
    }

    public User(int id, String first_name, String last_name, String email, String preferred_workout, int age, String gender, int weight, int target_weight) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.preferred_workout_location = preferred_workout;
        this.age = age;
        this.gender = gender;
        this.weight_kg = weight;
        this.target_weight = target_weight;
    }

    public User(String first_name, String last_name, String email, String password, String preferred_workout, int age, String gender, int weight, int target_weight) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.preferred_workout_location = preferred_workout;
        this.age = age;
        this.gender = gender;
        this.weight_kg = weight;
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
        return preferred_workout_location;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight_kg;
    }

    public int getTargetWeight() {
        return target_weight;
    }
}
