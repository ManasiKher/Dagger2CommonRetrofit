package com.manasi.dagger2_plus_common_retrofit.features.base;

import android.content.Context;
import android.support.annotation.StringRes;


public interface MvpView {

    void showLoading();

    void hideLoading();

    void openActivityOnTokenExpire();

    void onError(@StringRes int resId);

    void onError(String message);

    void showMessage(String message);

    void showMessage(@StringRes int resId);

    boolean isNetworkConnected();

    void hideKeyboard();

    Context getContext();


}
