package com.mide.windan.fastjobs.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mide.windan.fastjobs.R;

public class PlaceholderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeholder);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.restAPIButton)
    public void restAPIClick(View v){
        Intent i = new Intent(this, RestAPIActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.mapButton)
    public void mapClick(View v){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
}
