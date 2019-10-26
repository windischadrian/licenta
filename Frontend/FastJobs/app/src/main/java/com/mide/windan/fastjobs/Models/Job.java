package com.mide.windan.fastjobs.Models;

public class Job {

    private String longitude = "111.111";
    private String latitude = "222.222";
    private String description = "default description";
    private Long cost = 123L;
    private String type = "default type";
    private String beginDate = "2019-10-19";
    private String endDate = "2019-10-19";

    public Job() {
    }

    public Job(String longitude, String latitude, String description, Long cost, String type, String beginDate, String endDate) {
        if(!latitude.equals("")) this.latitude = latitude;
        if(!longitude.equals("")) this.longitude = longitude;
        if(!description.equals("")) this.description = description;
        if(!cost.toString().equals("")) this.cost = cost;
        if(!type.equals("")) this.type = type;
        if(!beginDate.equals("")) this.beginDate = beginDate;
        if(!endDate.equals("")) this.endDate = endDate;
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
        return "latitude:" + latitude
                + "\nlongitude: " + longitude
                + "\ndescription: " + description
                + "\ncost: " + cost
                + "\ntype: " + type
                + "\nbeginDate: " + beginDate
                + "\nendDate: " + endDate;
    }
}
