package com.manasi.dagger2_plus_common_retrofit.di.component;

import com.manasi.dagger2_plus_common_retrofit.features.dashboard.DashboardNavigationActivity;
import com.manasi.dagger2_plus_common_retrofit.di.PerActivity;
import com.manasi.dagger2_plus_common_retrofit.di.module.ActivityModule;

import com.manasi.dagger2_plus_common_retrofit.features.login.LoginActivity;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginMbNumFragement;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginOTPFragement;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    //TODO - update this everytime you add any activity or fragment...
    void inject(DashboardNavigationActivity activity);

    void inject(LoginActivity activity);

    void inject(LoginMbNumFragement mbNumFragement);

    void inject(LoginOTPFragement otpFragement);

}
