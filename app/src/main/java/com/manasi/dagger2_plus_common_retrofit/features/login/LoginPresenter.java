package com.manasi.dagger2_plus_common_retrofit.features.login;

import android.content.Intent;
import android.util.Log;

import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.di.PerActivity;
import com.manasi.dagger2_plus_common_retrofit.features.InitializeModel;
import com.manasi.dagger2_plus_common_retrofit.features.base.BasePresenter;
import com.manasi.dagger2_plus_common_retrofit.features.dashboard.DashboardNavigationActivity;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ApiEndPoint;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ApiInterface;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ResponseModel;
import com.manasi.dagger2_plus_common_retrofit.netowrk.RetrofitCallInterface;
import com.manasi.dagger2_plus_common_retrofit.utils.CommonUtils;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;
import com.manasi.dagger2_plus_common_retrofit.utils.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;



@PerActivity
public class LoginPresenter<V extends LoginContract.View> extends BasePresenter<V>
        implements LoginContract.Presenter<V>, RetrofitCallInterface {

    private static final String TAG = "LoginPresenter";

    @Inject
    public LoginPresenter() {

    }


    @Override
    public void loginButtonClick() {
        getMvpView().showMessage("");
    }

    @Override
    public void generateOTP_API(String mobileNumber) {
        Log.d(TAG, "generateOTP_API() called with: mobileNumber = [" + mobileNumber + "]");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contactNumber", mobileNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.GENERATE_OTP, this, true, false);
        callAPI(apiInterface.generateOTP(CommonUtils.getRequestBody(jsonObject)));


    }

    @Override
    public void resentOTP_API(String mobileNumber) {
        Log.d(TAG, "resentOTP_API() called with: mobileNumber = [" + mobileNumber + "]");


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contactNumber", mobileNumber);
            jsonObject.put("retryType", "text");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.RESEND_OTP, this);
        //callAPI(apiInterface.resendOTP(CommonUtils.getRequestBody(jsonObject)));


    }

    @Override
    public boolean isOTPValid(String otp) {
        if (otp.length() >= 4) {
            return true;
        } else {
            getMvpView().showMessage(R.string.invalid_otp);
        }
        return false;
    }

    @Override
    public void callInitialiseAPI() {
        ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.LOGIN, this, true, false);
        callAPI(apiInterface.initialize());

    }

    @Override
    public void login_API(String mobileNumber, String otp) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("contactNumber", mobileNumber);
            jsonObject.put("otp", otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (getMvpView() != null) {
           // ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.LOGIN, this);
           // callAPI(apiInterface.loginUser(CommonUtils.getRequestBody(jsonObject)));
        } else {
            Log.d(TAG, "login_API: getMVPview is null");
        }
    }

    @Override
    public boolean isMobileNumberValid(String mobileNumber) {
        Log.d(TAG, "isMobileNumberValid() called with: mobileNumber = [" + mobileNumber + "]");
        if (mobileNumber.length() >= 10) {
            return true;
        } else {
            if (getMvpView() != null) {
                getMvpView().showMessage(R.string.invalid_mobile_number);
            }
            return false;
        }
    }

    @Override
    public void onSuccess(String callName, Object response) {
        switch (callName) {
            case ApiEndPoint.LOGIN:
               getMvpView().getContext().startActivity(new Intent( getMvpView().getContext(), DashboardNavigationActivity.class));
                break;
        }
    }

    @Override
    public void onFailure(String callName, Throwable throwable) {

    }

    @Override
    public void noNetworkConnection() {

    }

    @Override
    public void onNoDataFound(String callName, String errorMsg) {

    }
}
