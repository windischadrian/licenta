package com.mide.windan.fastjobs.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.mide.windan.fastjobs.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.ViewById;

public class LoginActivity extends AppCompatActivity {


    @ViewById
    EditText usernameEditText;

    @ViewById
    EditText passwordEditText;

    @ViewById
    EditText emailEditText;
    @ViewById
    EditText passwordEditTextVerify;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this); //daca e null => nu s-a logat inca
        //updateUI(account);
    }

    @Click(R.id.registerButton)
    public void registerAction() {
    }

    @Click(R.id.loginButton)
    public void loginAction() {
    }
}