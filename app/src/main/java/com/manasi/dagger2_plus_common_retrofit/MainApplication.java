package com.manasi.dagger2_plus_common_retrofit;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.manasi.dagger2_plus_common_retrofit.di.component.ApplicationComponent;
import com.manasi.dagger2_plus_common_retrofit.di.component.DaggerApplicationComponent;
import com.manasi.dagger2_plus_common_retrofit.di.module.ApplicationModule;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;
import com.manasi.dagger2_plus_common_retrofit.utils.SharedPrefUtils;

import javax.inject.Inject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);


        CalligraphyConfig.initDefault(mCalligraphyConfig);

    }

    public static boolean isUserLoggedIn(Context context) {
        return SharedPrefUtils.getBoolean(context, Constants.SPK_LOGIN, Constants.SPV_IS_LOGGEDIN);
    }


    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
