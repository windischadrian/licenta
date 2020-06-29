package com.mide.windan.fastjobs.Models;

public class Job {

    private String jobOwner = "Default Job Owner";
    private String _id;
    private String longitude = "111.111";
    private String latitude = "222.222";
    private String jobTitle = "job title";
    private String description = "default description";
    private Long cost = 123L;
    private String type = "default type";
    private String beginDate = "2019-10-19";
    private String endDate = "2019-10-19";
    private Boolean fullJob = true;

    public Job() {
    }

    public Job(String jobOwner, String longitude, String latitude, String jobTitle, String description, Long cost, String type, String beginDate, String endDate, Boolean fullJob) {
        if(!jobOwner.equals("")) this.jobOwner = jobOwner;
        if(!latitude.equals("")) this.latitude = latitude;
        if(!longitude.equals("")) this.longitude = longitude;
        if(!jobTitle.equals("")) this.jobTitle = jobTitle;
        if(!description.equals("")) this.description = description;
        if(!cost.toString().equals("")) this.cost = cost;
        if(!type.equals("")) this.type = type;
        if(!beginDate.equals("")) this.beginDate = beginDate;
        if(!endDate.equals("")) this.endDate = endDate;
        this.fullJob = fullJob;
    }

    public String getJobId(){return _id;}

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobOwner() {
        return jobOwner;
    }

    public void setJobOwner(String jobOwner) {
        this.jobOwner = jobOwner;
    }

    public Boolean getFullJob() {
        return fullJob;
    }

    public void setFullJob(Boolean fullJob) {
        this.fullJob = fullJob;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString(){
        return "\nlatitude:" + latitude
                + "\nlongitude: " + longitude
                + "\ndescription: " + description
                + "\ncost: " + cost
                + "\ntype: " + type
                + "\nbeginDate: " + beginDate
                + "\nendDate: " + endDate
                + "\nfullJob: " + fullJob
                + "\njobOwner: " + jobOwner;
    }
}
