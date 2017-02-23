package com.botree.botree911_client.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bhavin on 1/2/17.
 */

public class PreferenceUtility {

    static String prefName = "botree911";
    static String accesstoken = "accesstoken";
    static String islogin = "islogin";
    static String email = "email";
    static String username = "username";
    static String userid = "userid";
    static String fcmid = "fcmid";
    static String lat = "lat";
    static String lng = "lng";

    static SharedPreferences getPreference(Context mContext){

        SharedPreferences pref = mContext.getSharedPreferences(prefName, Context.MODE_APPEND);
        return pref;

    }// End of getPreference()

    public static void saveIsLogin(Context mContext, boolean isLogin){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putBoolean(islogin, isLogin);
        prefEditor.commit();

    }// End of saveIsLogin()

    public static boolean getIsLogin(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getBoolean(islogin, false);

    }// End of getIsLogin()

    public static void saveAccessToken(Context mContext, String accessToken){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString(accesstoken, accessToken);
        prefEditor.commit();

    }// End of saveAccessToken()

    public static String getAccessToken(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(accesstoken, "");

    }// End of getAccessToken()

    public static void saveUserEmail(Context mContext, String email){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString(PreferenceUtility.email, email);
        prefEditor.commit();

    }// End of saveUserEmail()

    public static String getUserEmail(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(PreferenceUtility.email, "");

    }// End of getUserEmail()

    public static void saveUserId(Context mContext, String userid){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString(PreferenceUtility.userid, userid);
        prefEditor.commit();

    }// End of saveUserEmail()

    public static String getUserId(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(PreferenceUtility.userid, "");

    }// End of getUserEmail()

    public static void saveUserName(Context mContext, String username){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString(PreferenceUtility.username, username);
        prefEditor.commit();

    }// End of saveUserEmail()

    public static String getUserName(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(PreferenceUtility.username, "");

    }// End of getUserEmail()

    public static void saveFCMID(Context mContext, String fcmid){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString(PreferenceUtility.fcmid, fcmid);
        prefEditor.commit();

    }// End of saveFCMID()

    public static String getFCMID(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(PreferenceUtility.fcmid, "");

    }// End of getFCMID()

    public static void saveLocation(Context mContext, String lat, String lng){

        SharedPreferences pref = getPreference(mContext);
        SharedPreferences.Editor prefEditor = pref.edit();

        prefEditor.putString(PreferenceUtility.lat, lat);
        prefEditor.putString(PreferenceUtility.lng, lng);

        prefEditor.commit();

    }// End of saveLocation()

    public static String getLat(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(PreferenceUtility.lat, "");

    }// End of getLat()

    public static String getLng(Context mContext){

        SharedPreferences pref = getPreference(mContext);
        return pref.getString(PreferenceUtility.lng, "");

    }// End of getLng()

}
