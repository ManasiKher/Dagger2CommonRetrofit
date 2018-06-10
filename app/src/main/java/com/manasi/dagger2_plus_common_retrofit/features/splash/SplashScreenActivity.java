package com.manasi.dagger2_plus_common_retrofit.features.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.manasi.dagger2_plus_common_retrofit.features.dashboard.DashboardNavigationActivity;
import com.manasi.dagger2_plus_common_retrofit.MainApplication;
import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginActivity;


public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        showSplashImage(3000);
    }




    //TODO - Move this in presenter,.,
    public void showSplashImage(long timeOut) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                doScreenTransition();
            }
        }, timeOut);
    }

    private void doScreenTransition() {
        Intent intent = null;
        if (MainApplication.isUserLoggedIn(this))
        {//true
            //skip login and otp screen
                        intent = new Intent(this, DashboardNavigationActivity.class);
        }
        else {
            //goto login screen...
            intent = new Intent(this, LoginActivity.class);
        }


        startActivity(intent);
        finish();
    }
}
