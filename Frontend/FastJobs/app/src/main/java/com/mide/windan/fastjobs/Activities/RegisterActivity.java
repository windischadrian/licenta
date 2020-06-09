package com.mide.windan.fastjobs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mide.windan.fastjobs.R;

public class RegisterActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.registerButton)
    public void doRegister(View v){
        //Passwords match
        if(passwordEditText.getText().equals(password2EditText.getText())){
            if(Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()){

            }
            else {

            }
        }
        //Password and password verification do not match.
        else {
            String message = "Passwords do not match";
            Toast.makeText(this,message, Toast.LENGTH_LONG).show();
        }
    }
}
