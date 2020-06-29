package com.mide.windan.fastjobs.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mide.windan.fastjobs.Enums.JobType;
import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.Models.JobMarker;
import com.mide.windan.fastjobs.Models.UserDetails;
import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Rest.RestAPI;
import com.mide.windan.fastjobs.Utils.JobListAdapter;
import com.mide.windan.fastjobs.Utils.JobListAdapterArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.mide.windan.fastjobs.Enums.JobType.ANIMALE;
import static com.mide.windan.fastjobs.Enums.JobType.ELECTRICIAN;
import static com.mide.windan.fastjobs.Enums.JobType.LOC_DE_MUNCA;
import static com.mide.windan.fastjobs.Enums.JobType.MASINA;
import static com.mide.windan.fastjobs.Enums.JobType.TEHNIC;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    List<Job> jobList;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.hamburgerButton)
    ImageButton hamburgerButton;

    @BindView(R.id.schimbareParolaView)
    LinearLayout schimbareParolaView;

    @BindView(R.id.numeTextView)
    TextView numeTextView;

    @BindView(R.id.emailTextView)
    TextView emailTextView;

    @BindView(R.id.ratingTextView)
    TextView ratingTextView;

    @BindView(R.id.usernameTextView)
    TextView usernameTextView;

    @BindView(R.id.listView)
    ListView listView;

    RestAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fast-jobs-licenta.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestAPI.class);

        populateJobsList();
        setUserInfo();
    }

    private void setUserInfo() {
        usernameTextView.setText(String.valueOf(UserDetails.username));
        emailTextView.setText(String.valueOf(UserDetails.email));
        ratingTextView.setText(String.valueOf(UserDetails.rating));
        numeTextView.setText(String.valueOf(UserDetails.name));
    }

    private void populateJobsList() {
        service.getJobsFromUser(UserDetails._id).enqueue(new Callback<List<Job>>() {
                    @Override
                    public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                        try {
                            List<Job> jobList = response.body();

                            JobListAdapterArray jobListAdapterArray = new JobListAdapterArray(ProfileActivity.this, 0, jobList);

                            listView.setAdapter(jobListAdapterArray);

                            ProfileActivity.this.jobList = jobList;
                        } catch(Exception ex){
                            Toast.makeText(ProfileActivity.this, "Eroare la afisarea rezultatelor.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Job>> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.hamburgerButton)
    public void openNavigationDrawer(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
            Intent toMapsIntent = new Intent(this, MapsActivity.class);
            startActivity(toMapsIntent);
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void schimbareParola(View v){
        schimbareParolaView.setVisibility(View.VISIBLE);
    }

    public void anulareSchimbareParola(View v){
        schimbareParolaView.setVisibility(View.GONE);
    }
}
