package com.mide.windan.fastjobs.Rest;

import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.Models.Login;
import com.mide.windan.fastjobs.Models.UserDetails;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestAPI {

    @POST("/job")
    Call<Job> newJob(@Body Job job);

    @GET("/job")
    Call<List<Job>> getJobs();

    @GET("/job/type")
    Call<List<HashMap<String,Object>>> getJobs(@Query("type") String type,
                                               @Query("longitude") String longitude,
                                               @Query("latitude") String latitude);

    @GET("/job/cost")
    Call<List<HashMap<String,Object>>> getJobsByCost(@Query("lowCost") String lowCost,
                                                     @Query("highCost") String highCost,
                                                     @Query("longitude") String longitude,
                                                     @Query("latitude") String latitude);

    @GET("/job/distance")
    Call<List<HashMap<String,Object>>> getJobsByDistance(@Query("distance") String distance,
                                               @Query("longitude") String longitude,
                                               @Query("latitude") String latitude);

    @POST("/applicant")
    Call<String> applyToJob(@Body HashMap<String,String> applicant);

    @GET("/user/{id}")
    Call<HashMap<String,String>> getNameAndRatingById(@Path("id") String id);

    @GET("/job/{id}")
    Call<List<Job>> getJobsFromUser(@Path("id") String id);

    @PUT("/user")
    Call<String> updateUser(@Body UserDetails user);

    @DELETE("/user")
    Call<String> deleteUser(@Body UserDetails userDetails);

    @POST("/register/credentials")
    Call<ResponseUser> registerUser(@Body HashMap<String, String> userHashmap);

    @POST("/login/credentials")
    Call<ResponseUser> loginUser(@Body Login login);

}
