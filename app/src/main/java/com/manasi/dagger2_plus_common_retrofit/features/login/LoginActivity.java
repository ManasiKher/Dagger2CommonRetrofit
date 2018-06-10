package com.manasi.dagger2_plus_common_retrofit.features.login;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.custom.AlertDialogButtonsClickListener;
import com.manasi.dagger2_plus_common_retrofit.features.base.BaseActivity;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements
        AlertDialogButtonsClickListener,
        LoginContract.View,
        LoginMbNumFragement.OnFragmentInteractionListener,
        LoginOTPFragement.OnFragmentInteractionListener {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    /*@BindView(R.id.btnLogin)
    Button btnLogin;*/

    Fragment mobileNumberFragment, otpFragement;

    private static final int APP_PERMISSION_READ_SMS = 111;

    private static final int GOTO_DASHBOARD = 1;
    private static final int GOTO_PREFERENCES = 2;
    private static final int GOTO_WELCOME = 3;

    String mobileNumberEntered = "";

    private static final String TAG = "LoginActivity";

    @Inject
    LoginPresenter<LoginContract.View> loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));
        loginPresenter.onAttach(this);

        setUp();
        pushFragment(-1, mobileNumberFragment, false);

    }

    private void checkReadSMSPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                Log.d(TAG, "checkReadSMSPermissions:Permission is not granted. Explain the reason...");

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, APP_PERMISSION_READ_SMS);

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, APP_PERMISSION_READ_SMS);
                Log.d(TAG, "checkReadSMSPermissions: No explanation needed; request the permission");
                // APP_PERMISSION_READ_SMS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            callGetOTPAPI();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case APP_PERMISSION_READ_SMS: {
                // If request is cancelled, the result arrays are empty.


                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    //Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                //irrespective of user acceptance/rejection callgetOTP
                callGetOTPAPI();
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    protected void setUp() {
        //push login main frgament in container
        ivBack.bringToFront();
        mobileNumberFragment = new LoginMbNumFragement();//the fragment you want to show
        otpFragement = new LoginOTPFragement();//the fragment you want to show
    }



    public void pushFragment(int animationType, Fragment fragment, boolean isBackvisibility) {
        if (isBackvisibility) {
            ivBack.setVisibility(View.VISIBLE);
        } else {
            ivBack.setVisibility(View.GONE);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (animationType == 1) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        } else if (animationType == 0) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        Bundle arguments = new Bundle();
        arguments.putString("mobile_number", mobileNumberEntered);
        fragment.setArguments(arguments);
        //fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fl_container, fragment);//R.id.content_frame is the layout you want to replace
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void doScreenTransition(int gotoWhere) {
    }

    private void callGetOTPAPI() {

        //loginPresenter.generateOTP_API(mobileNumberEntered);
        //mobileNumberEntered = value;
    }



    @Override
    public void onFragmentInteraction(String type, String value) {
        if (type.equalsIgnoreCase("login")) {
            mobileNumberEntered = value;
            checkReadSMSPermissions();

        } else {
            //means otp
           // loginPresenter.login_API(mobileNumberEntered, value);

        }

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        //getSupportFragmentManager().popBackStack();
        if (ivBack.getVisibility() == View.VISIBLE) {
            pushFragment(0, mobileNumberFragment, false);
        } else {
            finish();
        }
    }

    @Override
    public void onAlertDialogButtonClick(String type, boolean isPositiveButtonClicked) {
        if (type.equalsIgnoreCase(Constants.DIALOG_TYPE_INVALID_STATE)) {
            if (isPositiveButtonClicked) {
                //CommonUtils.openPhoneDialer(this,nutritionistNumber);
            }
        }

    }
}
