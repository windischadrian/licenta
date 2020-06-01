package com.mide.windan.fastjobs.Models;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mide.windan.fastjobs.Enums.JobIcon;


import java.util.HashMap;

public class Markers {
    private HashMap<MarkerOptions, JobType> markers = new HashMap<>();

    public Markers(){
    }

    public void addMarker(MarkerOptions markerOptions, JobType jobType){
        markerOptions.icon(BitmapDescriptorFactory.fromResource(JobIcon.getJobIcon(jobType)));
        markers.put(markerOptions, jobType);
    }

    public void removeMarker(MarkerOptions markerOptions){
        markers.remove(markerOptions);
    }

    public HashMap<MarkerOptions, JobType> getMarkers(){
        return markers;
    }
}
