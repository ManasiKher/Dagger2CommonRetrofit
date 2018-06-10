package com.manasi.dagger2_plus_common_retrofit.netowrk;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by leftrightmind on 27/06/17.
 */

/*this class can be avoided if there  is no authentication..!!!*/
public class AuthenticationInterceptor implements Interceptor {
    private static final String TAG = "AuthenticationIntercept";

    /*add or update the headers as per the requirements...*/
    //private static final String HEADER_ACCESS_TOKEN = "x-access-token";
    private static final String HEADER_ACCESS_TOKEN = "token";
    private static final String HEADER_AUTHORISATION = "Authorization";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_CONTENT_TYPE_VALUE_DEFAULT = "application/json";


    private String accessToken;
    //private boolean isLoginAPI;

    public AuthenticationInterceptor(/*boolean isLoginAPI,*/ String token) {
        this.accessToken = token;
        //this.isLoginAPI = isLoginAPI;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder builder;


    /*    if (isLoginAPI) {

            builder = original.newBuilder().header(HEADER_ACCESS_TOKEN, accessToken);

        } else {

            builder = original.newBuilder().header(HEADER_AUTHORISATION, accessToken);

        }*/

        Log.d(TAG, "intercept: Authtoken - " + accessToken);
        builder = original.newBuilder().header(HEADER_ACCESS_TOKEN, accessToken);
        builder.addHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE_DEFAULT);

        Request request = builder.build();
        return chain.proceed(request);
    }

}
