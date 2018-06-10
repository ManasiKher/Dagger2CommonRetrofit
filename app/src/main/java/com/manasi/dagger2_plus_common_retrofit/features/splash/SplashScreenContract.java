package com.manasi.dagger2_plus_common_retrofit.features.splash;

import com.manasi.dagger2_plus_common_retrofit.features.base.MvpPresenter;
import com.manasi.dagger2_plus_common_retrofit.features.base.MvpView;


public interface SplashScreenContract {

    public interface View extends MvpView {

        void doScreenTransition();


        void apiSuccessTransition(String apiName, Object object);

    }


    interface Presenter<V extends View> extends MvpPresenter<V> {

        void callInitialiseAPI();

    }
}
