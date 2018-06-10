package com.manasi.dagger2_plus_common_retrofit.features.login;

import com.manasi.dagger2_plus_common_retrofit.features.base.MvpPresenter;
import com.manasi.dagger2_plus_common_retrofit.features.base.MvpView;

public interface LoginContract {

    public interface View extends MvpView {

        void doScreenTransition(int gotoWhere);


    }


    interface Presenter<V extends View> extends MvpPresenter<V> {
        void loginButtonClick();

        void generateOTP_API(String mobileNumber);

        void resentOTP_API(String mobileNumber);

        boolean isOTPValid(String otp);

        void callInitialiseAPI();

        void login_API(String mobileNumber, String OTP);

        boolean isMobileNumberValid(String mobileNumber);
    }
}
