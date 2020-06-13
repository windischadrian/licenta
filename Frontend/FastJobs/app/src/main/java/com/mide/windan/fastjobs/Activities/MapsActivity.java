package com.mide.windan.fastjobs.Activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.Models.JobMarker;
import com.mide.windan.fastjobs.R;
import com.mide.windan.fastjobs.Utils.DevTools;
import com.mide.windan.fastjobs.Utils.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ui.AppBarConfiguration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mide.windan.fastjobs.Enums.JobType.*;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private List<JobMarker> markers = new ArrayList<>();
    private HashMap<Marker,JobMarker> markersMap = new HashMap<>();

    @BindView(R.id.usernameTextView)
    TextView usernameTextView;

    @BindView(R.id.hamburgerButton)
    Button hamburgerButton;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.job_info_view)
    LinearLayout job_info_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButterKnife.bind(this);
        usernameTextView.setText(User.username);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        addMarkers();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng politehnica = new LatLng(44.4244133, 26.0682237);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(politehnica, 16));
        markers.forEach((jobMarker) -> {
           Marker marker =  mMap.addMarker(jobMarker.getMarkerOptions());
           markersMap.put(marker, jobMarker);
        });
        mMap.setOnMarkerClickListener(this);
    }

    private void addMarkers() {
        //fetch din backend
        //momentan din devtools

        markers = DevTools.setDevJobMarkers();

    }

    @Override
    public boolean onMarkerClick(final Marker marker){

        JobMarker jobMarker = markersMap.get(marker);

        Toast.makeText(this, jobMarker.getJob().getDescription(), Toast.LENGTH_LONG).show();

        job_info_view.setVisibility(View.VISIBLE);

        return false;
    }

    @OnClick(R.id.hamburgerButton)
    public void openNavigationDrawer(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void doHideJobInfo(View view) {
        job_info_view.setVisibility(View.GONE);
    }
}
