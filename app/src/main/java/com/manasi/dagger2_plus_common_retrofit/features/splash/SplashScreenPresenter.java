package com.manasi.dagger2_plus_common_retrofit.features.splash;

import com.manasi.dagger2_plus_common_retrofit.di.PerActivity;
import com.manasi.dagger2_plus_common_retrofit.features.InitializeModel;
import com.manasi.dagger2_plus_common_retrofit.features.base.BasePresenter;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ApiEndPoint;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ApiInterface;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ResponseModel;
import com.manasi.dagger2_plus_common_retrofit.netowrk.RetrofitCallInterface;

import javax.inject.Inject;

@PerActivity
public class SplashScreenPresenter<V extends SplashScreenContract.View> extends BasePresenter<V>
        implements SplashScreenContract.Presenter<V>, RetrofitCallInterface {

    private static final String TAG = "SplashScreenPresenter";

    @Inject
    public SplashScreenPresenter() {

    }
    
   
   
    @Override
    public void callInitialiseAPI() {
//        ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.INTIALIZE, this, true, false);
//        callAPI(apiInterface.initialize());

    }

   
    

    @Override
    public void onSuccess(String callName, Object response) {
        switch (callName) {

           
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
