package com.mide.windan.fastjobs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mide.windan.fastjobs.Models.Login;
import com.mide.windan.fastjobs.Models.UserDetails;
import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Rest.ResponseUser;
import com.mide.windan.fastjobs.Rest.RestAPI;
import com.mide.windan.fastjobs.Utils.DevTools;
import com.mide.windan.fastjobs.Utils.User;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usernameEditText)
    EditText usernameEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.loginButton)
    Button loginButton;

    RestAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fast-jobs-licenta.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestAPI.class);

    }

    @OnClick(R.id.loginButton)
    public void doLogin(View v){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (usernameEditText.getText().toString().matches("[a-zA-Z0-9_]{3,}")){
            Login login = new Login(username,password);

            service.loginUser(login).enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> callbackResponse) {
                    try {
                        UserDetails.fromResponse(callbackResponse.body());

                        Intent toMapActivityIntent = new Intent(LoginActivity.this, MapsActivity.class);
                        startActivity(toMapActivityIntent);
                        finish();
                    } catch (Exception ex){
                        String errorMessage = "Incorrect username or password.";
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    t.printStackTrace();
                    String errorMessage = "Incorrect username or password.";
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this,"Username invalid",Toast.LENGTH_LONG).show();
        }


    }
}
