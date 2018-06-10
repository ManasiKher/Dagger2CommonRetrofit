package com.manasi.dagger2_plus_common_retrofit.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;

import com.manasi.dagger2_plus_common_retrofit.R;
import com.manasi.dagger2_plus_common_retrofit.custom.AlertDialogButtonsClickListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CommonUtils {
    private static final String TAG = "CommonUtils";

    private CommonUtils() {
        // This utility class is not publicly instantiable
    }

    public static RequestBody getRequestBody(JSONObject jsonObject) {
        MediaType mediaType = MediaType.parse("application/json");
        assert jsonObject != null;
        return RequestBody.create(mediaType, jsonObject.toString());


    }

    public static void openPhoneDialer(Context context, String nutritionistNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + nutritionistNumber));
        context.startActivity(intent);
    }

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat(DateConstants.TIMESTAMP_FORMAT, Locale.US).format(new Date());
    }

    private static AlertDialogButtonsClickListener alertDialogButtonsClickListener;

    public static void showCommoneAlertDialog(final Context context, final String type,
                                              String title, String msg,
                                              String negativeButtonText, String positiveButtonText) {
        alertDialogButtonsClickListener = (AlertDialogButtonsClickListener) context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!title.isEmpty()) {
            builder.setTitle(title);
        }
        builder.setMessage(msg);
        builder.setCancelable(false);

        if (negativeButtonText.length() > 0) {
            String negativeText = negativeButtonText;
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialogButtonsClickListener.onAlertDialogButtonClick(type, false);
                            dialog.dismiss();
                        }
                    });
        }


        if (positiveButtonText.length() > 0) {
            String positiveText = positiveButtonText;
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialogButtonsClickListener.onAlertDialogButtonClick(type, true);
                            dialog.dismiss();
                        }
                    });
        }



        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();
    }

    public static void viewClickEffect(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}

