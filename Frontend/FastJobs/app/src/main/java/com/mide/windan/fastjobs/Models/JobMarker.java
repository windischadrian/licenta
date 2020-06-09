package com.mide.windan.fastjobs.Models;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mide.windan.fastjobs.Enums.JobIcon;
import com.mide.windan.fastjobs.Enums.JobType;


public class JobMarker {
    private MarkerOptions markerOptions;
    private JobType jobType;
    private Job job;

    public JobMarker(){
    }

    public void setJobMarkerDetails(MarkerOptions mo, JobType jt, Job j){
        this.markerOptions = mo;
        this.setJobType(jt);
        this.job = j;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public void setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
        markerOptions.icon(BitmapDescriptorFactory.fromResource(JobIcon.getJobIcon(jobType)));
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
