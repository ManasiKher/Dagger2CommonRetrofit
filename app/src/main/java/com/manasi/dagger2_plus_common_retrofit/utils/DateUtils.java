package com.manasi.dagger2_plus_common_retrofit.utils;

import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by leftrightmind on 22/02/17.
 */

public class DateUtils {


    public static final String df_app_dd_MMM_yy = "dd MMM yyyy";
    public static final String df_app_dd_MMM_yy_time = "dd MMM yyyy , hh:mm a";


    private static final String TAG = "DateUtils";


    public static String getFormattedDateTime(long timeStamp, int type) {

        //Log.d(TAG, "getFormattedDateTime() called with: timeStamp = [" + timeStamp + "], type = [" + type + "]");
        //18 Dec, Thursday
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        SimpleDateFormat desiredFromat = null;
        switch (type) {
            case Constants.DF_TYPE_MEETING_DATE:
                desiredFromat = new SimpleDateFormat("dd, EEEE", Locale.getDefault());
                break;
            case Constants.DF_TYPE_MEETING_DATE_DAY:
                desiredFromat = new SimpleDateFormat("dd MMM, EEEE", Locale.getDefault());
                break;
            case Constants.DF_TYPE_MEETING_TIME:
                desiredFromat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                break;
            case Constants.DF_TYPE_MEETING_REQ_MONTH:
                desiredFromat = new SimpleDateFormat("MMMM", Locale.getDefault());
                break;

            case Constants.DF_TYPE_MEETING_DAY:
                desiredFromat = new SimpleDateFormat("EEEE", Locale.getDefault());
                break;





            case Constants.DF_TYPE_CAL_DAY:
                desiredFromat = new SimpleDateFormat("E", Locale.getDefault());
                //special where only one alphabet needs to be send...
                return String.valueOf(desiredFromat.format(calendar.getTime()).charAt(0));
            //break;
            case Constants.DF_TYPE_CAL_DATE:
                desiredFromat = new SimpleDateFormat("dd", Locale.getDefault());
                break;


            case Constants.DF_TYPE_MONTH_YEAR:
                desiredFromat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
                break;




            default:
                desiredFromat = new SimpleDateFormat("dd MMM, EEEE", Locale.getDefault());


        }

        String result = desiredFromat.format(calendar.getTime());
        //Log.d(TAG, "getMeetingDateOnly: date: "+date);
        return result;

    }

    public static String getDateDifference(long dateStart, long dateEnd, int returnType) {
        Log.d(TAG, "getDateDifference() called with: dateStart = [" + dateStart + "], dateEnd = [" + dateEnd + "], returnType = [" + returnType + "]");
        //String dateStart = "01/14/2012 09:29:58";
        // String dateStop = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateStart);
        Date d1 = calendar.getTime();
        calendar.setTimeInMillis(dateEnd);
        Date d2 = calendar.getTime();

        //HH converts hour in 24 hours format (0-23), day calculation
        //d1 = format.parse(dateStart);
        //d2 = format.parse(dateStop);

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.print(diffSeconds + " seconds.");

        switch (returnType) {
            case Constants.DD_HOURS:
                return diffHours + "";

            case Constants.DD_MINUTES:
                if (diffHours == 1 && diffMinutes == 0) {
                    return 60 + "";
                } else {
                    return diffMinutes + "";
                }

            case Constants.DD_SECONDS:
                return diffSeconds + "";

            default:
                return diffHours + " days, " + diffHours + ":" + diffMinutes + ":" + diffSeconds;


        }


    }



    public static String convertToDateFromTimeStamp(long timestamp){


        SimpleDateFormat simpleDateFormat_input = new SimpleDateFormat(df_app_dd_MMM_yy, Locale.getDefault());
        simpleDateFormat_input.setTimeZone(TimeZone.getTimeZone("IST"));
        Timestamp stamp = new Timestamp(timestamp);
        Date date = new Date(stamp.getTime());
        System.out.println(simpleDateFormat_input.format(date));
        return simpleDateFormat_input.format(date);

    }


}
