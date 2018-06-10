package com.manasi.dagger2_plus_common_retrofit.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.manasi.dagger2_plus_common_retrofit.di.ActivityContext;
import com.manasi.dagger2_plus_common_retrofit.di.PerActivity;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    //contructor...
    public ActivityModule(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    //TODO add @provides annotation over here also...
    @Provides
    @PerActivity
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter();
    }


}
