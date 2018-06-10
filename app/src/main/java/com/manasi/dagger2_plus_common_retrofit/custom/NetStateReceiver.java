package com.manasi.dagger2_plus_common_retrofit.custom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.manasi.dagger2_plus_common_retrofit.utils.NetworkUtils;

public class NetStateReceiver extends BroadcastReceiver {

    private static final String TAG = "NetStateReceiver";

    public static final String NETWORK_AVAILABLE_ACTION = "NET_ACTION";

    private NetStateInterface netStateInterface;


    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d(TAG, "onReceive: NetworkSts ");
        //2nd method...

        if (netStateInterface != null) {
            netStateInterface.updateUIOnNetChange(NetworkUtils.isNetworkConnected(context));
        } else {
            Log.e(TAG, "onReceive: netStateInterface.is null");
        }
    }

    public void intiListener(NetStateInterface netStateInterfaceI) {
        netStateInterface = netStateInterfaceI;
    }


}
