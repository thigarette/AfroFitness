package com.example.thiga.afrofitness_android.api;

import com.example.thiga.afrofitness_android.models.Instructors;
import com.example.thiga.afrofitness_android.models.Result;
import com.example.thiga.afrofitness_android.models.Sessions;
import com.example.thiga.afrofitness_android.models.User;
import com.example.thiga.afrofitness_android.models.Users;

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

    @GET("user/{email}")
    Call<Users> getUser(@Path("email")String email);

    @GET("sessions/{id}")
    Call<Sessions> getSessions(@Path("id") int id);

    @GET("instructors")
    Call<Instructors> getInstructors();
}
