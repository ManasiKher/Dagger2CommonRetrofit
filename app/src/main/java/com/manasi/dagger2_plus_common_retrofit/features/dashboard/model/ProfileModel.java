package com.manasi.dagger2_plus_common_retrofit.features.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileModel {
    @SerializedName("clientID")
    @Expose
    private String clientID;
    @SerializedName("nutritionistID")
    @Expose
    private String nutritionistID;
    @SerializedName("batchID")
    @Expose
    private String batchID;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("emailID")
    @Expose
    private String emailID;
    @SerializedName("clientImage")
    @Expose
    private String clientImage;
    @SerializedName("courseDuration")
    @Expose
    private Integer courseDuration;
    @SerializedName("startDate")
    @Expose
    private Integer startDate;
    @SerializedName("endDate")
    @Expose
    private Integer endDate;
    @SerializedName("interactions")
    @Expose
    private String interactions;
    @SerializedName("nutritionistNumber")
    @Expose
    private String nutritionistNumber;
    @SerializedName("nutritionistEmailid")
    @Expose
    private String nutritionistEmailid;
    @SerializedName("nutritionistFirstName")
    @Expose
    private String nutritionistFirstName;
    @SerializedName("nutritionistLastName")
    @Expose
    private String nutritionistLastName;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getNutritionistID() {
        return nutritionistID;
    }

    public void setNutritionistID(String nutritionistID) {
        this.nutritionistID = nutritionistID;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getClientImage() {
        return clientImage;
    }

    public void setClientImage(String clientImage) {
        this.clientImage = clientImage;
    }

    public Integer getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getEndDate() {
        return endDate;
    }

    public void setEndDate(Integer endDate) {
        this.endDate = endDate;
    }

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public String getNutritionistNumber() {
        return nutritionistNumber;
    }

    public void setNutritionistNumber(String nutritionistNumber) {
        this.nutritionistNumber = nutritionistNumber;
    }

    public String getNutritionistEmailid() {
        return nutritionistEmailid;
    }

    public void setNutritionistEmailid(String nutritionistEmailid) {
        this.nutritionistEmailid = nutritionistEmailid;
    }

    public String getNutritionistFirstName() {
        return nutritionistFirstName;
    }

    public void setNutritionistFirstName(String nutritionistFirstName) {
        this.nutritionistFirstName = nutritionistFirstName;
    }

    public String getNutritionistLastName() {
        return nutritionistLastName;
    }

    public void setNutritionistLastName(String nutritionistLastName) {
        this.nutritionistLastName = nutritionistLastName;
    }

}
