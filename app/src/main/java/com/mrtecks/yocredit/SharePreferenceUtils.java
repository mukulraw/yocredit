package com.mrtecks.yocredit;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

    private static String PREFERENCE_NAME = "yocredit";
    private static SharePreferenceUtils sharePreferenceUtils;
    private SharedPreferences sharedPreferences;

    private SharePreferenceUtils(Context context){
        PREFERENCE_NAME = PREFERENCE_NAME + context.getPackageName();
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    static SharePreferenceUtils getInstance(){
        if (sharePreferenceUtils == null){
            sharePreferenceUtils = new SharePreferenceUtils(Bean.getContext());
        }
        return sharePreferenceUtils;
    }

    // login response user_id 1234
    void saveString(String key, String Val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, Val);
        editor.apply();
    }

    public String getString(String key, String defVal){
        return sharedPreferences.getString(key, defVal);
    }


    String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key, int Val ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, Val);
        editor.apply();
    }

    public int getInteger(String key){ return sharedPreferences.getInt(key, 0 ); }

    void deletePref()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
