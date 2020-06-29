package com.mide.windan.fastjobs.Activities;

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

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.common.hash.Hashing;
import com.mide.windan.fastjobs.Models.Login;
import com.mide.windan.fastjobs.Models.UserDetails;
import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Rest.ResponseUser;
import com.mide.windan.fastjobs.Rest.RestAPI;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerDataLinearLayout)
    LinearLayout registerDataLinearLayout;

    @BindView(R.id.nameEditText)
    EditText nameEditText;

    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.usernameEditText)
    EditText usernameEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.password2EditText)
    EditText password2EditText;

    @BindView(R.id.phoneEditText)
    EditText phoneEditText;

    @BindView(R.id.registerButton)
    Button registerButton;

    Boolean provideName = false;

    RestAPI service;

    HashMap<String, String> userHashmap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fast-jobs-licenta.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(RestAPI.class);
    }

    @OnClick(R.id.registerButton)
    public void doRegister(View v){

        if(provideName){
            Toast.makeText(this, "Register", Toast.LENGTH_LONG).show();
            userHashmap.put("name", nameEditText.getText().toString());

            service.registerUser(userHashmap).enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> callbackResponse) {
                    UserDetails.fromResponse(callbackResponse.body());
                    System.out.println(UserDetails.toStringStatic());
                    userHashmap.clear();
                    finish();
                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Eroare la inregistrare.", Toast.LENGTH_LONG).show();
                }
            });

        }else {
            if (passwordEditText.getText().toString().equals(password2EditText.getText().toString())
                    && passwordEditText.getText().toString().length()>=8 && passwordEditText.getText().toString().length()<=24) {
                if (Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    if (usernameEditText.getText().toString().matches("[a-zA-Z0-9_]{3,}")) {
                        final String hashed = Hashing.sha256()
                                .hashString(passwordEditText.getText().toString(), StandardCharsets.UTF_8)
                                .toString();
                        userHashmap.put("email", emailEditText.getText().toString());
                        userHashmap.put("phoneNumber", phoneEditText.getText().toString());
                        userHashmap.put("password", hashed);
                        userHashmap.put("username", usernameEditText.getText().toString());

                        registerDataLinearLayout.setVisibility(View.GONE);
                        nameEditText.setVisibility(View.VISIBLE);
                        provideName=true;
                    }
                    else{
                        Toast.makeText(this, "Eroare 1", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Eroare 2", Toast.LENGTH_LONG).show();
                }
            }
            else {
                String message = "Password error";
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

}
