package com.start.laundryapp.retrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;


public class SharedPrefs {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    private static final String tokenKey = "Authorization";

    public static String getToken() {
        return prefs.getString(tokenKey, null);
    }

    public static void setToken(String token) {
        editor.putString(tokenKey, token);
        editor.apply();
    }
    public static void removeToken() {
        editor.remove(tokenKey);
        editor.apply();
    }
}
