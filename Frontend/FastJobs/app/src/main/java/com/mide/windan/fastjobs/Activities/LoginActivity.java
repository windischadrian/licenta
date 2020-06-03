package com.mide.windan.fastjobs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Utils.DevTools;
import com.mide.windan.fastjobs.Utils.User;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.usernameEditText)
    EditText usernameEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.loginButton)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.loginButton)
    public void doLogin(View v){
        //TODO: Login flow
        //Placeholder for admin-admin
        String username = usernameEditText.getText().toString();
        String password = usernameEditText.getText().toString();

        if ((DevTools.admins.containsKey(username)) && DevTools.admins.get(username).equals(password)) {
            User.username=username;
            Intent toMapActivityIntent = new Intent(this, MapsActivity.class);
            startActivity(toMapActivityIntent);
            finish();
        }
        else{
            String errorMessage = "Incorrect username or password.";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
