package com.manasi.dagger2_plus_common_retrofit.features;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class InitializeModel {
    @SerializedName("state")
    @Expose
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
