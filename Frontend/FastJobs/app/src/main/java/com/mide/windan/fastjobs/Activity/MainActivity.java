package com.mide.windan.fastjobs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Rest.RestAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//47239585728-5htkm6q8hfhscebfrpiddvq7244g0j6t.apps.googleusercontent.com
//client id

public class MainActivity extends AppCompatActivity {

    Button sendButton,getButton;
    EditText descriptionEditText, costEditText, typeEditText, beginDateEditText, endDateEditText;

    RestAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton = findViewById(R.id.sendButton);
        getButton = findViewById(R.id.getButton);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        costEditText = findViewById(R.id.costEditText);
        typeEditText = findViewById(R.id.typeEditText);
        beginDateEditText = findViewById(R.id.beginDateEditText);
        endDateEditText = findViewById(R.id.endDateEditText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://powerful-eyrie-10439.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RestAPI.class);

    }

    public void sendData(View v){

        String description = descriptionEditText.getText().toString();
        Long cost = Long.parseLong(costEditText.getText().toString().equals("") ? "0" : costEditText.getText().toString());
        String type = typeEditText.getText().toString();
        String beginDate = beginDateEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        String longitude = "111.222";
        String latitude = "123.456";

        Job job = new Job(latitude, longitude, description, cost, type, beginDate, endDate);

        System.out.println(job.toString());

        service.newJob(job).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> callbackResponse) {
                String response = callbackResponse.body();
                System.out.println("@@@@@@@@@@@@@@ PUT RESPONSE: ");
                System.out.println(response);
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getData(View v){
        service.getData().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> callbackResponse) {
                String response = callbackResponse.body();
                System.out.println("@@@@@@@@@@@@@@ GET RESPONSE: ");
                System.out.println(response);
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
