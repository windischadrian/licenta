package com.mide.windan.fastjobs.Utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mide.windan.fastjobs.Models.Job;
import com.mide.windan.fastjobs.Models.JobMarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mide.windan.fastjobs.Enums.JobType.ANIMALE;
import static com.mide.windan.fastjobs.Enums.JobType.ELECTRICIAN;
import static com.mide.windan.fastjobs.Enums.JobType.MASINA;
import static com.mide.windan.fastjobs.Enums.JobType.TEHNIC;

public class DevTools {

    public static final HashMap<String,String> admins = new HashMap<String,String>(){{
        put("admin","admin");
    }};


    public static List<JobMarker> setDevJobMarkers(){
        Job job = new Job();
        List<JobMarker> jobMarkers = new ArrayList<>();

        JobMarker jm1 = new JobMarker();
        MarkerOptions mo1 = new MarkerOptions();
        mo1.position(new LatLng(44.425952, 26.068764));
        jm1.setJobMarkerDetails(mo1, ANIMALE, job);
        jobMarkers.add(jm1);

        JobMarker jm2 = new JobMarker();
        MarkerOptions mo2 = new MarkerOptions();
        mo2.position(new LatLng(44.421993, 26.073632));
        jm2.setJobMarkerDetails(mo2, ELECTRICIAN, job);
        jobMarkers.add(jm2);

        JobMarker jm3 = new JobMarker();
        MarkerOptions mo3 = new MarkerOptions();
        mo3.position(new LatLng(44.423301, 26.062346));
        jm3.setJobMarkerDetails(mo3, MASINA, job);
        jobMarkers.add(jm3);

        JobMarker jm4 = new JobMarker();
        MarkerOptions mo4 = new MarkerOptions();
        mo4.position(new LatLng(44.418104, 26.068438));
        jm4.setJobMarkerDetails(mo4, TEHNIC, job);
        jobMarkers.add(jm4);

        return jobMarkers;
    }
}
