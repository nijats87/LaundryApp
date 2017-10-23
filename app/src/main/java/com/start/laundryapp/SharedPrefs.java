package com.start.laundryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;


public class SharedPrefs {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    private static final String tokenKey = "Authorization";

    static String getToken() {
        return prefs.getString(tokenKey, null);
    }

    static void setToken(String token) {
        editor.putString(tokenKey, token);
        editor.apply();
    }
    static void removeToken() {
        editor.remove(tokenKey);
        editor.apply();
    }
}
