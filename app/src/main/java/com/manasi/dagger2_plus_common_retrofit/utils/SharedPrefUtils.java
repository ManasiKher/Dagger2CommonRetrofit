package com.manasi.dagger2_plus_common_retrofit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leftrightmind on 22/02/17.
 */

public class SharedPrefUtils {

    public static void setString(Context context, String prefName,
                                 String key, String value) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static String getString(Context context,
                                   String prefName, String key) {
        String toReturn = "";
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            toReturn = preferences.getString(key, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toReturn;
    }


    public static void setBoolean(Context context, String prefName,
                                  String key, boolean value) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            editor.apply();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static boolean getBoolean(Context context,
                                     String prefName, String key) {
        boolean toReturn = false;
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            toReturn = preferences.getBoolean(key, false);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return toReturn;
    }


    public static void setInt(Context context, String prefName,
                              String key, int value) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.apply();
        } catch (Exception e1) {
            e1.printStackTrace();

        }
    }


    public static int getInt(Context context,
                             String prefName, String key) {
        int toReturn = -1;
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            toReturn = preferences.getInt(key, -1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return toReturn;
    }


    ///object....

/*    public static void setUserModel(Context context, String prefName, String key, UserModel value) {

        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String jsonValue = gson.toJson(value);
        editor.putString(key, jsonValue);
        editor.commit();

    }

    public static UserModel getUserModel(Context context, String prefName, String key) {

        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String jsonValue = preferences.getString(key, "");
        Gson gson = new Gson();
        return gson.fromJson(jsonValue, UserModel.class);
    }*/

    public static void clearAllPrefs(Context context, String prefName) {
        SharedPreferences preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // clear all the flags except the device reset flag
        editor.clear();
        editor.commit();
    }


    public static List<Integer> getIntegerList(Context context,
                                               String prefName, String key) {
        List<Integer> integerListToReturn = new ArrayList<>();
        String toReturn = "";
        try {
            SharedPreferences preferences = context.getSharedPreferences(prefName,
                    Context.MODE_PRIVATE);
            toReturn = preferences.getString(key, "");

            Gson gson = new Gson();
            Type type = new TypeToken<List<Integer>>() {
            }.getType();
            integerListToReturn = gson.fromJson(toReturn, type);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return integerListToReturn;


    }


    public static void setIntegerList(Context context, String prefName, String key, List<Integer> integerList) {
        SharedPreferences preferences = context.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(integerList);
        editor.putString(key, json);
        editor.apply();
        editor.commit();
    }


/*
     try {
        SharedPreferences preferences = context.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    } catch (Exception e) {
        e.printStackTrace();
    }*/


}
