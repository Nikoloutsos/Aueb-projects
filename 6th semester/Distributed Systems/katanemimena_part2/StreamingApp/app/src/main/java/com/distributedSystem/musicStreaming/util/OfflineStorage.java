package com.distributedSystem.musicStreaming.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * A class for easier handling user data. (It uses sharedPreferences)
*/
public class OfflineStorage{
    private SharedPreferences pref = null;
    private Context parentActivity;
    public static String APP_KEY;

    public OfflineStorage(Context context) {
        parentActivity = context;
        APP_KEY = context.getPackageName().replaceAll("\\.", "_").toLowerCase();
    }

    /**
     * Storing a string in local storage
     */
    public void setString(String key, String value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Retrieving a string from local storage
     */
    public String getString(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");

    }
}
