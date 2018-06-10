package com.manasi.dagger2_plus_common_retrofit.netowrk;

import com.manasi.dagger2_plus_common_retrofit.features.InitializeModel;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginModelResponse;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {


    @POST(ApiEndPoint.GENERATE_OTP)
    Call<ResponseModel> generateOTP(@Body RequestBody requestBody);

    @GET(ApiEndPoint.LOGIN)
    Call<ResponseModel<LoginModelResponse>> initialize();



}

