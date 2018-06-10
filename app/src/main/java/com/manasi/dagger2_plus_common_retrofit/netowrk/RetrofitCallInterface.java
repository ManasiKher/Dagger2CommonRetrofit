package com.manasi.dagger2_plus_common_retrofit.netowrk;


/**
 * Created by leftrightmind on 18/07/17.
 */

public interface RetrofitCallInterface<T> {
    void onSuccess(String callName, ResponseModel response);

    void onFailure(String callName, Throwable throwable);

    void noNetworkConnection();

    void onNoDataFound(String callName, String errorMsg);
}
