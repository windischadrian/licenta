package com.mide.windan.fastjobs.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.mide.windan.fastjobs.Enums.JobType;
import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.Models.JobMarker;
import com.mide.windan.fastjobs.Models.UserDetails;
import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Rest.ResponseUser;
import com.mide.windan.fastjobs.Rest.RestAPI;
import com.mide.windan.fastjobs.Utils.DevTools;
import com.mide.windan.fastjobs.Utils.JobListAdapter;
import com.mide.windan.fastjobs.Utils.JobListAdapterArray;
import com.mide.windan.fastjobs.Utils.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ui.AppBarConfiguration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.mide.windan.fastjobs.Enums.JobType.*;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, NavigationView.OnNavigationItemSelectedListener{

    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 77;

    private GoogleMap mMap;
    private List<JobMarker> markers = new ArrayList<>();
    private List<Long> distance = new ArrayList<>();
    private List<Job> jobList = new ArrayList<>();
    private HashMap<Marker,JobMarker> markersMap = new HashMap<>();

    @BindView(R.id.mapsJobsListView)
    ListView listView;

    @BindView(R.id.usernameTextView)
    TextView usernameTextView;

    @BindView(R.id.hamburgerButton)
    ImageButton hamburgerButton;

    @BindView(R.id.addJobButton)
    FloatingActionButton addJobButton;

    @BindView(R.id.refreshJobsButton)
    FloatingActionButton refreshJobsButton;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.job_info_view)
    LinearLayout job_info_view;

    @BindView(R.id.add_job_layout_id)
    FrameLayout add_job_layout_id;

    @BindView(R.id.contacteazaButton)
    Button contacteazaButton;

    //Adaugare job
    @BindView(R.id.titluJobAddJob)
    EditText titluJobAddJob;
    @BindView(R.id.spinner_jobtype)
    Spinner spinner_jobtype;
    @BindView(R.id.dateStartAddJob)
    EditText dateStartAddJob;
    @BindView(R.id.dateEndAddJob)
    EditText dateEndAddJob;
    @BindView(R.id.descriptionAddJob)
    EditText descriptionAddJob;
    @BindView(R.id.costAddJob)
    EditText costAddJob;

    //Vizualizare job
    @BindView(R.id.jobTitleViewJob)
    TextView jobTitleViewJob;
    @BindView(R.id.jobId)
    TextView jobId;
    @BindView(R.id.numeAngajatorViewJob)
    TextView numeAngajatorViewJob;
    @BindView(R.id.startDateViewJob)
    TextView startDateViewJob;
    @BindView(R.id.endDateViewJob)
    TextView endDateViewJob;
    @BindView(R.id.costViewJob)
    TextView costViewJob;
    @BindView(R.id.descriptionViewJob)
    TextView descriptionViewJob;
    @BindView(R.id.ratingViewJob)
    TextView ratingViewJob;

    //Meniu
    Spinner spinnerFiltrarejoburi;
    EditText filtrareCostEditTextUp;
    EditText filtrareCostEditTextDown;
    EditText filtrareDistantaEditText;

    Boolean locationPermissionGranted = false;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Location lastKnownLocation;
    LocationManager locationManager;

    RestAPI service;

    String numeAngajator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);
        usernameTextView.setText(UserDetails.name);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fast-jobs-licenta.herokuapp.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RestAPI.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addMarkers(); //get markers / jobs from backend
        mapFragment.getMapAsync(this);

        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        spinnerFiltrarejoburi = (Spinner) mNavigationView.getMenu().findItem(R.id.filtrareSpinner).getActionView().findViewById(R.id.spinnerFiltrarejoburi);
        filtrareCostEditTextUp = (EditText) mNavigationView.getMenu().findItem(R.id.filtrareCost).getActionView().findViewById(R.id.filtrareCostEditTextUp);
        filtrareCostEditTextDown = (EditText) mNavigationView.getMenu().findItem(R.id.filtrareCost).getActionView().findViewById(R.id.filtrareCostEditTextDown);
        filtrareDistantaEditText = (EditText) mNavigationView.getMenu().findItem(R.id.filtrareDistanta).getActionView().findViewById(R.id.filtrareDistantaEditText);


        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.job_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_jobtype.setAdapter(adapter);
        spinnerFiltrarejoburi.setAdapter(adapter);
        spinnerFiltrarejoburi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!adapter.getItem(position).equals("Nimic selectat")){
                    filterJobsByType(adapter.getItem(position).toString());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Job job = jobList.get(position);
            service.getNameAndRatingById(job.getJobOwner()).enqueue(new Callback<HashMap<String, String>>() {
                @Override
                public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                    numeAngajator = response.body().get("name");
                    ratingViewJob.setText(response.body().get("rating") + "/5");

                    numeAngajatorViewJob.setText(numeAngajator);
                    jobTitleViewJob.setText(job.getJobTitle());
                    descriptionViewJob.setText(job.getDescription());
                    startDateViewJob.setText("De la: " + job.getBeginDate());
                    endDateViewJob.setText("Pana la: " + job.getEndDate());
                    costViewJob.setText("Cost: " + String.valueOf(job.getCost()) + " LEI");
                    jobId.setText(job.getJobId());

                    drawerLayout.closeDrawers();
                    job_info_view.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

                }
            });
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Disable Map Toolbar:
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        markers.forEach((jobMarker) -> {
            Marker marker =  mMap.addMarker(jobMarker.getMarkerOptions());
            markersMap.put(marker, jobMarker);
        });
        mMap.setOnMarkerClickListener(this);
    }

    @OnClick(R.id.refreshJobsButton)
    public void doRefreshJobs(View v){
        mMap.clear();
        addMarkers();
    }

    private void addMarkers() {
        List<JobMarker> jobMarkers = new ArrayList<>();

        service.getJobs().enqueue(new Callback<List<Job>>() {
            @Override
            public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                List<Job> jobListRes = response.body();
                MapsActivity.this.jobList = jobListRes;
                jobListRes.forEach( job -> {
                    JobMarker jobMarker = new JobMarker();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(Double.parseDouble(job.getLatitude()), Double.parseDouble(job.getLongitude())));
                    JobType jobType;
                    switch(job.getType()){
                        case "Loc de munca": jobType = LOC_DE_MUNCA; break;
                        case "Animale": jobType = ANIMALE; break;
                        case "Electric": jobType = ELECTRICIAN; break;
                        case "Auto": jobType = MASINA; break;
                        case "Tehnic": jobType = TEHNIC; break;
                        default: jobType = LOC_DE_MUNCA; break;
                    }

                    jobMarker.setJobMarkerDetails(markerOptions, jobType, job);
                    jobMarkers.add(jobMarker);

                    Marker marker =  mMap.addMarker(jobMarker.getMarkerOptions());
                    markersMap.put(marker, jobMarker);

                    populareListaJoburiMeniu();
                });
            }

            @Override
            public void onFailure(Call<List<Job>> call, Throwable t) {

            }
        });

        markers = jobMarkers;

    }

    @Override
    public boolean onMarkerClick(final Marker marker){

        JobMarker jobMarker = markersMap.get(marker);

        service.getNameAndRatingById(jobMarker.getJob().getJobOwner()).enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                numeAngajator = response.body().get("name");
                ratingViewJob.setText(response.body().get("rating") + "/5");

                Job job = jobMarker.getJob();
                numeAngajatorViewJob.setText(numeAngajator);
                jobTitleViewJob.setText(job.getJobTitle());
                descriptionViewJob.setText(job.getDescription());
                startDateViewJob.setText("De la: " + job.getBeginDate());
                endDateViewJob.setText("Pana la: " + job.getEndDate());
                costViewJob.setText("Cost: " + String.valueOf(job.getCost()) + " LEI");
                job_info_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

            }
        });

        return false;
    }

    @OnClick(R.id.hamburgerButton)
    public void openNavigationDrawer(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.addJobButton)
    public void doAddJob(View v){
        add_job_layout_id.setVisibility(add_job_layout_id.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    public void doHideJobInfo(View view) {
        job_info_view.setVisibility(View.GONE);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_profile){
            Intent toProfileIntent = new Intent(this, ProfileActivity.class);
            startActivity(toProfileIntent);
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick(R.id.contacteazaButton)
    public void doContacteaza(View v){
        String userPhoneNumber = "0769622085";

        String uri = "tel:" + userPhoneNumber.trim() ;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);

        HashMap<String, String> applicantMap = new HashMap<>();
        applicantMap.put("userId", UserDetails._id);
        applicantMap.put("jobId", jobId.getText().toString());

        service.applyToJob(applicantMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MapsActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void doTrimiteAddJob(View v){
        Job newJob = new Job();
        newJob.setJobOwner(UserDetails._id);
        try {
            newJob.setLongitude(Double.toString(lastKnownLocation.getLongitude()));
            newJob.setLatitude(Double.toString(lastKnownLocation.getLatitude()));
        } catch (Exception ex){
            Toast.makeText(this, "A aparut o eroare. Incercati mai tarziu.", Toast.LENGTH_LONG).show();
        }

        newJob.setJobTitle(titluJobAddJob.getText().toString());
        newJob.setDescription(descriptionAddJob.getText().toString());
        Long cost;
        try{
            cost = Long.parseLong(costAddJob.getText().toString());
        }catch(Exception ex){
            cost = 0L;
        }
        newJob.setCost(cost);
        String jobType = spinner_jobtype.getSelectedItem().toString();
        newJob.setType(jobType);
        newJob.setBeginDate(dateStartAddJob.getText().toString());
        newJob.setEndDate(dateEndAddJob.getText().toString());
        newJob.setFullJob(jobType.equals("Loc de munca"));

        service.newJob(newJob).enqueue(new Callback<Job>() {
            @Override
            public void onResponse(Call<Job> call, Response<Job> callbackResponse) {
                titluJobAddJob.setText("");
                descriptionAddJob.setText("");
                costAddJob.setText("");
                dateStartAddJob.setText("");
                dateEndAddJob.setText("");
                add_job_layout_id.setVisibility(View.GONE);
                Job jobResponse = callbackResponse.body();
                JobMarker jobMarker = new JobMarker();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(Double.parseDouble(jobResponse.getLatitude()), Double.parseDouble(jobResponse.getLongitude())));
                JobType jobType;
                switch(jobResponse.getType()){
                    case "Loc de munca": jobType = LOC_DE_MUNCA; break;
                    case "Animale": jobType = ANIMALE; break;
                    case "Electric": jobType = ELECTRICIAN; break;
                    case "Auto": jobType = MASINA; break;
                    case "Tehnic": jobType = TEHNIC; break;
                    default: jobType = LOC_DE_MUNCA; break;
                }

                System.out.println(jobResponse.getType());
                System.out.println(jobType);

                jobMarker.setJobMarkerDetails(markerOptions, jobType, jobResponse);
                markers.add(jobMarker);

                Marker marker =  mMap.addMarker(jobMarker.getMarkerOptions());
                markersMap.put(marker, jobMarker);

            }

            @Override
            public void onFailure(Call<Job> call, Throwable t) {
                t.printStackTrace();
                String errorMessage = "Job could not be created.";
                Toast.makeText(MapsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), 16));
                            }
                        } else {
                            LatLng politehnica = new LatLng(44.4244133, 26.0682237);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(politehnica, 16));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void populareListaJoburiMeniu(){

        JobListAdapterArray jobListAdapterArray = new JobListAdapterArray(MapsActivity.this, 0, jobList);

        listView.setAdapter(jobListAdapterArray);

    }

    private void filterJobsByType(String type) {
        service.getJobs(type, String.valueOf(lastKnownLocation.getLongitude()), String.valueOf(lastKnownLocation.getLatitude())).enqueue(new Callback<List<HashMap<String,Object>>>() {
            @Override
            public void onResponse(Call<List<HashMap<String,Object>>> call, Response<List<HashMap<String,Object>>> response) {
                try {
                    List<String> distanceList = new ArrayList<>();
                    List<Job> jobList = new ArrayList<>();
                    response.body().forEach(map -> {
                        String distance = (String) map.get("distance");
                        distanceList.add(distance);
                        Job job = new Gson().fromJson(new Gson().toJson(map.get("job")), Job.class);
                        jobList.add(job);
                    });
                    JobListAdapterArray jobListAdapterArray = new JobListAdapterArray(MapsActivity.this, 0, jobList, distanceList);

                    listView.setAdapter(jobListAdapterArray);

                    MapsActivity.this.jobList = jobList;
                } catch(Exception ex){
                    Toast.makeText(MapsActivity.this, "Eroare la filtrarea rezultatelor.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<HashMap<String,Object>>> call, Throwable t) {

            }
        });
    }

    public void doFilterJobByCost(View v){
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex){}

        String costLimitUp = filtrareCostEditTextUp.getText().toString();
        String costLimitDown = filtrareCostEditTextDown.getText().toString();

        if(Long.parseLong(costLimitUp) < Long.parseLong(costLimitDown)){
            Toast.makeText(MapsActivity.this, "Costul maxim nu poate fi mai mare decat cel minim.", Toast.LENGTH_LONG).show();
        } else {
            service.getJobsByCost(costLimitDown, costLimitUp,
                    String.valueOf(lastKnownLocation.getLongitude()),
                    String.valueOf(lastKnownLocation.getLatitude()))
                    .enqueue(new Callback<List<HashMap<String,Object>>>() {
                @Override
                public void onResponse(Call<List<HashMap<String,Object>>> call, Response<List<HashMap<String,Object>>> response) {
                    try {
                        List<String> distanceList = new ArrayList<>();
                        List<Job> jobList = new ArrayList<>();
                        response.body().forEach(map -> {
                            String distance = (String) map.get("distance");
                            distanceList.add(distance);
                            Job job = new Gson().fromJson(new Gson().toJson(map.get("job")), Job.class);
                            jobList.add(job);
                        });
                        JobListAdapterArray jobListAdapterArray = new JobListAdapterArray(MapsActivity.this, 0, jobList, distanceList);

                        listView.setAdapter(jobListAdapterArray);

                        MapsActivity.this.jobList = jobList;
                    } catch(Exception ex){
                        Toast.makeText(MapsActivity.this, "Eroare la filtrarea rezultatelor.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<HashMap<String,Object>>> call, Throwable t) {

                }
            });
        }
    }

    public void doFilterJobByDistance(View v){
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex){}

        String filtrareDistantaLimit = filtrareDistantaEditText.getText().toString();

        service.getJobsByDistance(filtrareDistantaLimit,
                String.valueOf(lastKnownLocation.getLongitude()),
                String.valueOf(lastKnownLocation.getLatitude()))
                .enqueue(new Callback<List<HashMap<String,Object>>>() {
                    @Override
                    public void onResponse(Call<List<HashMap<String,Object>>> call, Response<List<HashMap<String,Object>>> response) {
                        try {
                            List<String> distanceList = new ArrayList<>();
                            List<Job> jobList = new ArrayList<>();
                            response.body().forEach(map -> {
                                String distance = (String) map.get("distance");
                                distanceList.add(distance);
                                Job job = new Gson().fromJson(new Gson().toJson(map.get("job")), Job.class);
                                jobList.add(job);
                            });
                            JobListAdapterArray jobListAdapterArray = new JobListAdapterArray(MapsActivity.this, 0, jobList, distanceList);

                            listView.setAdapter(jobListAdapterArray);

                            MapsActivity.this.jobList = jobList;

                        } catch(Exception ex){
                            Toast.makeText(MapsActivity.this, "Eroare la filtrarea rezultatelor.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<HashMap<String,Object>>> call, Throwable t) {

                    }
                });

    }

}
