package com.mide.windan.fastjobs.Rest;

import com.mide.windan.fastjobs.Models.Job;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RestAPI {

    @POST("/")
    Call<String> newJob(@Body Job job);

    @GET("/")
    Call<String> getData();
}
