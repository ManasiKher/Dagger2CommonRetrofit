package com.manasi.dagger2_plus_common_retrofit.features.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;


public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    private static final String TAG = "SmsReceiver";
    String abcd;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //You must check here if the sender is your provider and not another one with same text.

            String messageBody = smsMessage.getMessageBody();
            abcd=messageBody.replaceAll("[^0-9]","");
            Log.d(TAG, "onReceive: messageBody- " + messageBody+" "+abcd);

            //Pass on the text to our listener.
            if(mListener!=null) {
                mListener.messageReceived(abcd);
            }
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
