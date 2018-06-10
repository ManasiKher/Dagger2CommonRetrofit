package com.manasi.dagger2_plus_common_retrofit.di.component;

import android.app.Application;
import android.content.Context;

import com.manasi.dagger2_plus_common_retrofit.MainApplication;
import com.manasi.dagger2_plus_common_retrofit.di.ApplicationContext;
import com.manasi.dagger2_plus_common_retrofit.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainApplication rdSchedulerApplication);

    @ApplicationContext
    Context context();

    Application application();


}
