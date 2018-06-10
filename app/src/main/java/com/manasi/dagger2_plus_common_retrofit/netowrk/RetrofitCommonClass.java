package com.manasi.dagger2_plus_common_retrofit.netowrk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.manasi.dagger2_plus_common_retrofit.features.base.MvpView;
import com.manasi.dagger2_plus_common_retrofit.features.login.LoginActivity;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;
import com.manasi.dagger2_plus_common_retrofit.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leftrightmind on 18/07/17.
 */

public class RetrofitCommonClass<T> {

    private static final String TAG = "RetrofitCommonClass";
    private static final String TAG_API = "TAG_API";
    private RetrofitCallInterface retrofitCallInterface;
    private Context context;
    private View rootView;
    private boolean isShowProgress;
    private String callName;
    private ProgressDialog pdialog;
    //private CredaiProgressbarLoading progressbarLoading;
    private boolean isShowSuccessMsg = false;

    MvpView mvpView;

    public RetrofitCommonClass() {
    }


    public ApiInterface getAPIInterface(MvpView mvpView, String callName,
                                        RetrofitCallInterface retrofitCallInterfac) {
        this.isShowSuccessMsg = true;
        this.isShowProgress = true;
        this.context = (Context) mvpView;
        this.callName = callName;
        this.mvpView = mvpView;
        this.retrofitCallInterface = retrofitCallInterfac;

        if (context == null) {
            Log.e(TAG, "******** getAPIInterface: Context is null" + callName);
        }


        return ServiceGenerator.createService(this.context, ApiInterface.class);
    }

    //only for login purpose only
    public ApiInterface getAPIInterface(MvpView mvpView, String callName,
                                        RetrofitCallInterface retrofitCallInterface,
                                        String username, String password) {
        this.isShowSuccessMsg = true;
        this.isShowProgress = true;
        this.context = (Context) mvpView;
        this.mvpView = mvpView;
        this.callName = callName;
        this.retrofitCallInterface = retrofitCallInterface;

        return ServiceGenerator.createService(this.context, ApiInterface.class, username, password);
    }

    public ApiInterface getAPIInterface(MvpView mvpView, String callName,
                                        RetrofitCallInterface retrofitCallInterface,
                                        boolean isShowProgress, boolean isShowSuccessMsg) {
        this.isShowSuccessMsg = isShowSuccessMsg;
        this.isShowProgress = isShowProgress;
        this.context = (Context) mvpView;
        this.mvpView = mvpView;
        this.callName = callName;
        this.retrofitCallInterface = retrofitCallInterface;

        return ServiceGenerator.createService(this.context, ApiInterface.class);
    }



    public void initialiseCall(Call<ResponseModel> tCall) throws Exception {

        if (NetworkUtils.isNetworkConnected(context)) {
            mvpView.showLoading();

            tCall.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {

                    try {
                        //it is true only if generic sts code is 200...
                        if (isValidateGenericStsCode(response)) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {


                                    //if (response.body() instanceof ResponseModel) {
                                    ResponseModel responseParentModel = response.body();

                                    if (responseParentModel != null) {
                                        // Toast.makeText(context, responseParentModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        if (responseParentModel.getStatus().equalsIgnoreCase(ResponseCodeUtils.SERVER_STATUS_SUCCESS)
                                                && responseParentModel.getStatusCode() == 200) {

                                            if (isShowSuccessMsg) {
                                                Toast.makeText(context, responseParentModel.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            retrofitCallInterface.onSuccess(callName, responseParentModel);

                                        } else {
                                            if (responseParentModel.getStatusCode() != null) {
                                                checkForServerInternalCodes(responseParentModel.getStatusCode(), responseParentModel.getMessage());
                                            }
                                            Toast.makeText(context, responseParentModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(context, "Unknown response", Toast.LENGTH_SHORT).show();
                                    }
                                    // }

                                } else {
                                    Toast.makeText(context, "Response body is null", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "Response not successfull", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Server response error - " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }

                    mvpView.hideLoading();

                    /*if (isShowProgress) {
                        if (rootView != null) {
                            //Log.d(TAG, "onResponse: Hide progress...."+call.request().url());
                            progressbarLoading.hideProgressDialog();
                        } else {
                            pdialog.hideProgressDialog();
                        }
                    }*/

                }

                @Override
                public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {
                    /*if (isShowProgress) {
                        if (rootView != null) {
                            progressbarLoading.hideProgressDialog();
                        } else {
                            pdialog.hideProgressDialog();
                        }
                    }*/

                    mvpView.hideLoading();
                    retrofitCallInterface.onFailure(callName, t);
                    Log.e(TAG, "onFailure: Throwable " + t.toString());
                    if (t.getLocalizedMessage() != null) {
                        if (t.getLocalizedMessage().contains("Failed to connect to")) {
                            Toast.makeText(context, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                        Log.e(TAG, "----------- onFailure LocMsg:----------- " + t.getLocalizedMessage());
                    } else {
                        Toast.makeText(context, "Error: " + "context.getString(R.string.err_unknown)", Toast.LENGTH_SHORT).show();
                    }


                }
            });

        } else {
            Toast.makeText(context, "No network", Toast.LENGTH_SHORT).show();
            retrofitCallInterface.noNetworkConnection();
        }
    }



    private void checkForServerInternalCodes(int statusCodeFromSercver, String msgFromServer) {
        Log.d(TAG, "checkForServerInternalCodes() called with: statusCodeFromSercver = [" + statusCodeFromSercver + "], msgFromServer = [" + msgFromServer + "]");
        switch (statusCodeFromSercver) {
            case ResponseCodeUtils.R_SESSION_TIME_OUT_SERVER_CODE:
                /*showPopup(ResponseCodeUtils.TYPE_SESSION_TIME_OUT_MSG,
                        context.getString(R.string.nw_err_session_timeout_title),
                        context.getString(R.string.nw_err_session_timeout_msg),
                        context.getString(R.string.nw_err_session_timeout_positive_btn_txt),
                        "");*/
                break;
            case ResponseCodeUtils.R_NO_TOKEN_SERVER_CODE:
             /*   showPopup(ResponseCodeUtils.TYPE_NO_TOKEN_MSG,
                        context.getString(R.string.nw_err_no_token_title),
                        context.getString(R.string.nw_err_no_token_msg),
                        context.getString(R.string.nw_err_no_token_positive_btn_txt),
                        "");*/


                break;
            case ResponseCodeUtils.R_SERVER_FAILED_SERVER_CODE:
                break;
            case ResponseCodeUtils.R_VALIDTION_ERROR_SERVER_CODE:
                break;
            case ResponseCodeUtils.R_SERVER_MAINTAINANCE_SERVER_CODE:
                /*showPopup(ResponseCodeUtils.TYPE_SERVER_MAINTAINANCE_MSG,
                        context.getString(R.string.nw_err_server_maintainence_title),
                        msgFromServer,
                        context.getString(R.string.nw_err_server_maintainence_positive_btn_txt),
                        "");*/

                break;
            case ResponseCodeUtils.R_APP_UPGRADE_SERVER_CODE:
               /* showPopup(ResponseCodeUtils.TYPE_UPDATE_APP_MSG,
                        context.getString(R.string.nw_err_upgrade_app_title),
                        msgFromServer,
                        context.getString(R.string.nw_err_upgrade_app_positive_btn_txt),
                        context.getString(R.string.nw_err_upgrade_app_negative_btn_txt));*/
                break;


        }

    }


    private boolean isValidateGenericStsCode(Response<ResponseModel> response) {
        Log.d(TAG_API, "--HTTP CODE " + response.code());

        switch (response.code()) {
            case 200:
                return true;

            case 204:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_204, Toast.LENGTH_SHORT).show();
                return false;

            case 400:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_400, Toast.LENGTH_SHORT).show();
                return false;

            case 401:
                //redirect user to the login window...
                if (callName.equals(ApiEndPoint.LOGIN)) {
                    retrofitCallInterface.onFailure(callName, null);
                    Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, ResponseCodeUtils.R_CODE_401, Toast.LENGTH_SHORT).show();
                    logoutUser(context);
                }
                return false;

            case 403:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_403, Toast.LENGTH_SHORT).show();
                return false;

            case 404:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_404, Toast.LENGTH_SHORT).show();
                return false;

            case 405:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_405, Toast.LENGTH_SHORT).show();
                return false;

            case 500:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_500, Toast.LENGTH_SHORT).show();
                return false;

            case 502:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_502, Toast.LENGTH_SHORT).show();
                return false;

            case 594:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_594, Toast.LENGTH_SHORT).show();
                return false;


            case 598:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_598, Toast.LENGTH_SHORT).show();
                return false;

            default:
                Toast.makeText(context, ResponseCodeUtils.R_CODE_UNKNOWN, Toast.LENGTH_SHORT).show();
                return false;


        }

    }

    private void logoutUser(Context context) {
        // SharedPrefUtils.clearAllPrefs(context, Constants.USER_PREF);


        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

            /*400	Session not valid
        401	No token passed
        402	Server failed to serve request
        403	Validation error
        404	API not found
        405	Server maintainance
        406	App needs to upgarde*/


    private synchronized void showPopup(final String type, String title, String msg, String positiveButtonText, String negativeButtonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        if (!title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setCancelable(false);


        String positiveText = positiveButtonText;
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (type) {
                            case ResponseCodeUtils.TYPE_SESSION_TIME_OUT_MSG:
                            case ResponseCodeUtils.TYPE_NO_TOKEN_MSG:
                                logoutUser(context);
                                break;
                            case ResponseCodeUtils.TYPE_SERVER_MAINTAINANCE_MSG:
                                //showServerMaintainenceWindowPopup();
                                System.exit(1);
                            case ResponseCodeUtils.TYPE_UPDATE_APP_MSG:
                                //TODO - need to handle app update using broadcast...
                                //refer - https://stackoverflow.com/questions/6952643/detect-android-app-upgrade-and-set-application-class-boolean-for-show-hide-of-eu
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PLAY_STORE_URL));
                                context.startActivity(intent);

                                break;
                        }


                        dialog.dismiss();
                    }
                });

        if (!negativeButtonText.equals("")) {
            String negativeText = negativeButtonText;
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // negative button logic
                            if (type.equals(ResponseCodeUtils.TYPE_UPDATE_APP_MSG)) {
                                System.exit(1);
                            }
                            dialog.dismiss();
                        }
                    });
        }

        AlertDialog dialog = builder.create();
        // display dialog
        if (!dialog.isShowing()) {
            dialog.show();
        }

    }

}
