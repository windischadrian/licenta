package com.mide.windan.fastjobs.Activities;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mide.windan.fastjobs.Models.Markers;
import com.mide.windan.fastjobs.R;

import androidx.fragment.app.FragmentActivity;

import static com.mide.windan.fastjobs.Enums.JobType.*;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Markers markers = new Markers();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        markers.getMarkers().forEach((marker, jobType) -> mMap.addMarker(marker));
        mMap.setOnMarkerClickListener(this);
    }

    private void addMarkers() {
        MarkerOptions mo1 = new MarkerOptions();
        mo1.position(new LatLng(44.425952, 26.068764));
        markers.addMarker(mo1, ANIMALE);
        MarkerOptions mo2 = new MarkerOptions();
        mo2.position(new LatLng(44.421993, 26.073632));
        markers.addMarker(mo2, ELECTRICIAN);
        MarkerOptions mo3 = new MarkerOptions();
        mo3.position(new LatLng(44.423301, 26.062346));
        markers.addMarker(mo3, MASINA);
        MarkerOptions mo4 = new MarkerOptions();
        mo4.position(new LatLng(44.418104, 26.068438));
        markers.addMarker(mo4, TEHNIC);
    }

    @Override
    public boolean onMarkerClick(final Marker marker){

        return false;
    }
}
