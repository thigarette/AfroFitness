package com.example.thiga.afrofitness_android.models;

public class Session {
    private int id;
    private String date;
//    private int location;
    private String exercise_type;
    private int number_of_reps;
    private int number_of_sets;
    private int user;
    private String location_name;

    public Session(int id, String date, String exercise_type, int number_of_reps, int number_of_sets, int user,String location_name) {
        this.id = id;
        this.date = date;
//        this.location = location;
        this.exercise_type = exercise_type;
        this.number_of_reps = number_of_reps;
        this.number_of_sets = number_of_sets;
        this.user = user;
        this.location_name = location_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setExerciseType(String exercise_type) {
        this.exercise_type = exercise_type;
    }

    public void setNumberOfReps(int number_of_reps) {
        this.number_of_reps = number_of_reps;
    }

    public void setNumberOfSets(int number_of_sets) {
        this.number_of_sets = number_of_sets;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setLocationName(String location_name) {
        this.location_name = location_name;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

//    public int getLocation() {
//        return location;
//    }

    public String getExerciseType() {
        return exercise_type;
    }

    public int getNumberOfReps() {
        return number_of_reps;
    }

    public int getNumberOfSets() {
        return number_of_sets;
    }

    public int getUser() {
        return user;
    }

    public String getLocationName(){
        return location_name;
    }
}
