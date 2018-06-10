package com.manasi.dagger2_plus_common_retrofit.features.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.MainApplication;
import com.manasi.dagger2_plus_common_retrofit.custom.NetStateInterface;
import com.manasi.dagger2_plus_common_retrofit.custom.NetStateReceiver;
import com.manasi.dagger2_plus_common_retrofit.di.component.ActivityComponent;
import com.manasi.dagger2_plus_common_retrofit.di.component.DaggerActivityComponent;
import com.manasi.dagger2_plus_common_retrofit.di.module.ActivityModule;
import com.manasi.dagger2_plus_common_retrofit.utils.CommonUtils;
import com.manasi.dagger2_plus_common_retrofit.utils.NetworkUtils;

import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback, NetStateInterface {

    private Unbinder mUnBinder;
    private ActivityComponent mActivityComponent;

    private ProgressDialog mProgressDialog;

    private static final String MSG_NET_OFFLINE = "You are offline";
    private static final String MSG_NET_ONLINE = "Connected to interenet";

    private static final String TAG = "BaseActivity";

    NetStateReceiver netStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MainApplication) getApplication()).getComponent())
                .build();

        try {
            netStateReceiver = new NetStateReceiver();
            IntentFilter filterData = new IntentFilter();
            filterData.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(netStateReceiver, filterData);
            netStateReceiver.intiListener((NetStateInterface) this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //incase any class needed activity componenet
    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }



    //calligraphy font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //permission related
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    private void showSnackBar(String message) {
        Log.d(TAG, "showSnackBar() called with: message = [" + message + "]");
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        if (message.equals(MSG_NET_OFFLINE)) {
            sbView.setBackground(getResources().getDrawable(R.color.profile_red));
        } else if (message.equals(MSG_NET_ONLINE)) {
            sbView.setBackground(getResources().getDrawable(R.color.greenish_teal));
        }

        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }


    //below this all are MvpView methods...
    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
        /*startActivity(LoginActivity.getStartIntent(this));
        finish();*/
    }

    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }



    //for butterknife unbinder...
    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    @Override
    protected void onDestroy() {

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateUIOnNetChange(boolean isNetAvaialble) {

        Log.d(TAG, "updateUIOnNetChange() NetworkSts :" + isNetAvaialble + ":::: getConnectivityStatus: " + NetworkUtils.getConnectivityStatus(BaseActivity.this));

        if (isNetAvaialble) {
            //show green colored network connected snackbar...
            //showSnackBar(MSG_NET_ONLINE);

        } else {
            // showSnackBar(MSG_NET_OFFLINE);
        }




    }


    protected abstract void setUp();


}
