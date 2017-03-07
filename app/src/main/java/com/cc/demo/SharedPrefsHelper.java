package com.cc.demo;

import android.content.SharedPreferences;

public class SharedPrefsHelper {
    static final String KEY_NAME = "key_name";
    private final SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public boolean saveName(String name){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        return editor.commit();
    }

    public String getName() {
        return mSharedPreferences.getString(KEY_NAME, "");
    }
}
