package com.example.thiga.afrofitness_android.api;

import com.example.thiga.afrofitness_android.models.GymLocation;
import com.example.thiga.afrofitness_android.models.GymLocations;
import com.example.thiga.afrofitness_android.models.Instructors;
import com.example.thiga.afrofitness_android.models.Result;
import com.example.thiga.afrofitness_android.models.Session;
import com.example.thiga.afrofitness_android.models.Sessions;
import com.example.thiga.afrofitness_android.models.User;
import com.example.thiga.afrofitness_android.models.WorkoutSessionResult;
import com.example.thiga.afrofitness_android.ui.MapsActivity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("register")
    Call<Result> createUser(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("gender") String gender
    );

    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update/{id}")
    Call<Result> updateUser(
            @Path("id") int id,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("email") String email,
            @Field("preferred_workout_location") String preferred_workout_location,
            @Field("age") int age,
            @Field("gender") String gender,
            @Field("weight_kg") int weight,
            @Field("target_weight_kg") int target_weight
    );
    @FormUrlEncoded
    @POST("addsession")
    Call<WorkoutSessionResult>addSession(
            @Field("exercise_type") String exercise_type,
            @Field("date") String date,
            @Field("location_name") String location_name,
            @Field("number_of_reps") String number_of_reps,
            @Field("number_of_sets") String number_of_sets,
            @Field("user_id")int user_id
    );

    @GET("sessions/{id}")
    Call<Sessions> getSessions(@Path("id") int id);

    @GET("instructors")
    Call<Instructors> getInstructors();
}
