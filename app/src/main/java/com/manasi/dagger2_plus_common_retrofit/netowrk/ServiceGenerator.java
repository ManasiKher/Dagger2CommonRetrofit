package com.manasi.dagger2_plus_common_retrofit.netowrk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manasi.dagger2_plus_common_retrofit.utils.Constants;
import com.manasi.dagger2_plus_common_retrofit.utils.NetworkUtils;
import com.manasi.dagger2_plus_common_retrofit.utils.SharedPrefUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String TAG = "ServiceGenerator";

    private static Retrofit retrofit;
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    private static OkHttpClient.Builder okkhttpClientBuilder = null;
    private static boolean isLoginAPI;
    private static AuthenticationInterceptor authInterceptor = null;

    //inorder to avoid json.reader malformed fn setLenient=true...
    private static Gson gson = new GsonBuilder().setLenient().create();


    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiEndPoint.USED_URL)
            .addConverterFactory(GsonConverterFactory.create(gson));


    public ServiceGenerator() {
    }


    private static OkHttpClient.Builder getOkHttpClient() {
        if (okkhttpClientBuilder == null) {
            okkhttpClientBuilder = new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS);
        }
        return okkhttpClientBuilder;
    }


    //this method is called only while loggin in...
    public static <S> S createService(Context context, Class<S> serviceClass, String username, String password) {
        Log.d(TAG, "createService: 2: auth>> " + username + ":" + password);
        //isLoginAPI = false;
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            String accessToken = Credentials.basic(username, password);
            //isLoginAPI = true;
            return createService(context, serviceClass, accessToken);
        }/*else{
        }*/
        return createService(context, serviceClass, null, null);
    }

    public static <S> S createService(Context context, Class<S> serviceClass) {
        Log.d(TAG, "createService: 1");

        //isLoginAPI = false;
        String accessToken = SharedPrefUtils.getString(context, Constants.SPK_LOGIN, Constants.SPV_AUTH_TOKEN);
        isLoginAPI = false;
        if (accessToken == null || accessToken.equalsIgnoreCase("")) {
            accessToken = "tempAccessToken";
            isLoginAPI = true;
        }
        return createService(context, serviceClass, accessToken);
    }

    //for rest other calls this method should get called...
    public static <S> S createService(Context context, Class<S> serviceClass, final String accessToken) {

        Log.d(TAG, "createService: 3: auth>> " + accessToken);

        if (NetworkUtils.isNetworkConnected(context)) {

            if (!TextUtils.isEmpty(accessToken)) {

                if (authInterceptor == null) {
                    authInterceptor = new AuthenticationInterceptor(/*isLoginAPI,*/ accessToken);
                }

                //TODO - if above line didnt worked then use the below code..

                if (!getOkHttpClient().interceptors().contains(authInterceptor)) {
                    Log.d(TAG, "createService: not contains authInterceptor");
                    // set your desired log level
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    // add httpLoggingInterceptor as last authInterceptor
                    getOkHttpClient().addInterceptor(httpLoggingInterceptor);  // <-- this is the important line!
                    getOkHttpClient().addInterceptor(authInterceptor);
                    builder.client(getOkHttpClient().build());
                    retrofit = builder.build();
                }
            } else {
                //logout user.. ask him to login again...
                Toast.makeText(context, "Access token is missing", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        }
        if (retrofit == null) {
            builder.client(getOkHttpClient().build());
            retrofit = builder.build();
        }
        if (isLoginAPI) {
            authInterceptor = null;
        }
        return retrofit.create(serviceClass);
    }
}
