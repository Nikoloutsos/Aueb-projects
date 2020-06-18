package com.example.studytracker.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.studytracker.App;

/**
 * <p>Used for retrieving and saving key-value pairs in sharedPreferences</p>
 */
public class SharedPrefUtil {

    /**
     * Used as key for storing user's id
     */
    public static final String USER_ID = "USER_ID";
    /**
     * Used as key for storing user's username
     */
    public static final String USER_USERNAME = "USER_USERNAME";


    private SharedPreferences pref;

    public SharedPrefUtil() {
        pref = App.appContext.getSharedPreferences(
                App.appContext.getPackageName().replaceAll("\\.", "_").toLowerCase(),
                Context.MODE_PRIVATE);
    }

    /**
     * Stores a string in sharedPreferences
     *
     * @param key   The key for that value
     * @param value The value which will be paired with this key
     */
    public void setString(String key, String value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Get a string from shared preferences
     *
     * @param key The key for that value
     * @return the string value of that key
     */
    public String getString(String key) {
        return pref.getString(key, "");
    }

    /**
     * Stores a int in sharedPreferences
     *
     * @param key   The key for that value
     * @param value The value which will be paired with this key
     */
    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Get an int from shared preferences
     *
     * @param key The key for that value
     * @return the int value of that key
     */
    public int getInt(String key) {
        return pref.getInt(key, 0);
    }
}
