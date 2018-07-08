package com.manasi.dagger2_plus_common_retrofit.features.dashboard;

import android.util.Log;

import com.manasi.dagger2_plus_common_retrofit.features.base.BasePresenter;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginModelResponse;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ApiEndPoint;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ApiInterface;
import com.manasi.dagger2_plus_common_retrofit.netowrk.ResponseModel;
import com.manasi.dagger2_plus_common_retrofit.netowrk.RetrofitCallInterface;

import java.util.ArrayList;

import javax.inject.Inject;

public class DashboardPresenter<V extends DashboardContract.View> extends BasePresenter<V>
        implements DashboardContract.Presenter<V>, RetrofitCallInterface {

    private static final String TAG = "DashboardPresenter";
    @Inject
    public DashboardPresenter() {
    }


    @Override
    public void getDashboardAPI() {
       ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.GET_DASHBOARD, this,true,false);
        callAPI(apiInterface.getDashboardData());

    }

    @Override
    public void logoutUserAPI() {
//        ApiInterface apiInterface = getRCommonInstance().getAPIInterface(getMvpView(), ApiEndPoint.LOGOUT, this);
//        callAPI(apiInterface.logout());

    }


    @Override
    public void onSuccess(String callName, Object response) {
        switch (callName) {
            case ApiEndPoint.GET_DASHBOARD:
                if(response!=null){
                    ArrayList<LoginModelResponse> arrayList= (ArrayList<LoginModelResponse>) response;
                    Log.d(TAG, "onSuccess: "+ arrayList.get(0).getLoginUserName());
                }

                 else {
                    getMvpView().showMessage("No data found");
                    getMvpView().dismissRefreshLayout();
                }

                break;

        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void onFailure(String callName, Throwable throwable) {
        getMvpView().dismissRefreshLayout();
    }

    @Override
    public void noNetworkConnection() {
        getMvpView().dismissRefreshLayout();
    }

    @Override
    public void onNoDataFound(String callName, String errorMsg) {

    }
}
