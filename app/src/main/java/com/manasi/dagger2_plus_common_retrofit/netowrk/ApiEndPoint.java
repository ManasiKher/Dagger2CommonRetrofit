package com.manasi.dagger2_plus_common_retrofit.netowrk;

import com.manasi.dagger2_plus_common_retrofit.BuildConfig;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;


public class ApiEndPoint {

    public static final String USED_URL = BuildConfig.SERVER_URL;

    /*Login*/
    public static final String GENERATE_OTP = "otp/generateOTP";
    public static final String LOGIN = "login";


    public static final String GET_DASHBOARD = "dashboard";


}
