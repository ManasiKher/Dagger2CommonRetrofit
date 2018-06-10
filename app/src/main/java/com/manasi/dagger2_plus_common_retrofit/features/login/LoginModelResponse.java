package com.manasi.dagger2_plus_common_retrofit.features.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModelResponse {
    @SerializedName("loginUserName")
    @Expose
    private String loginUserName;
    @SerializedName("password")
    @Expose
    private String password;

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
