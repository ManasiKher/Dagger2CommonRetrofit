package com.manasi.dagger2_plus_common_retrofit.di.module;

import android.app.Application;
import android.content.Context;

import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.di.ApplicationContext;
import com.manasi.dagger2_plus_common_retrofit.di.PreferenceInfo;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;



@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }



    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/sfprodisplay_regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }


}
