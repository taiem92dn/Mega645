package vn.dinosys.mega645.util;

import android.util.Log;

import com.google.gson.Gson;

import vn.dinosys.mega645.util.security.ObscuredSharedPreferences;
import vn.dinosys.mega645.util.security.SecurityUtil;


/**
 * Created by htsi.
 * Since: 2/18/16 on 2:11 PM
 * Project: DinoHR_Android
 */
public class BaseRuntime {

    public static final String PREF_USER_REGISTERED = SecurityUtil.sha256("PREF_USER_REGISTERED");
    public static final String PREF_USER_LOGGEDIN = SecurityUtil.sha256("PREF_USER_LOGGEDIN");
    public static final String PREF_USER_TOKEN_KEY = SecurityUtil.sha256("PREF_USER_TOKEN_KEY");
    public static final String PREF_USERNAME_KEY = SecurityUtil.sha256("PREF_USERNAME_KEY");
    public static final String PREF_PASSWORD_KEY = SecurityUtil.sha256("PREF_PASSWORD_KEY");
    public static final String PREF_EXPIRES_KEY = SecurityUtil.sha256("PREF_EXPIRES_KEY");
    public static final String PREF_BASE_URL_KEY = SecurityUtil.sha256("PREF_BASE_URL_KEY");
    public static final String PREF_DEVICE_TOKEN = SecurityUtil.sha256("PREF_DEVICE_TOKEN");
    public static final String PREF_DEVICE_ID = SecurityUtil.sha256("PREF_DEVICE_ID");

    protected Gson mGson;
    protected ObscuredSharedPreferences mSharedPreferences;

    protected boolean mUserLoggedIn;
    protected String mTokenKey;
    protected String mUsername;
    protected String mPassword;
    protected String mDeviceToken;
    protected String mDeviceId;
    protected long mExpires;

    public BaseRuntime(ObscuredSharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
        mGson = new Gson();
        mUserLoggedIn = mSharedPreferences.getBoolean(PREF_USER_LOGGEDIN, false);
        mTokenKey = mSharedPreferences.getString(PREF_USER_TOKEN_KEY, "");
        mUsername = mSharedPreferences.getString(PREF_USERNAME_KEY, "");
        mPassword = mSharedPreferences.getString(PREF_PASSWORD_KEY, "");
        mExpires = mSharedPreferences.getLong(PREF_EXPIRES_KEY, 0);
        mDeviceToken = mSharedPreferences.getString(PREF_DEVICE_TOKEN, null);
        mDeviceId = mSharedPreferences.getString(PREF_DEVICE_ID, null);
    }

    //---------------------------------------------------------------------------------
    public boolean isUserRegistered() {
        return mSharedPreferences.getBoolean(PREF_USER_REGISTERED, false);
    }

    public void setUserRegistered(boolean registered) {
        mSharedPreferences.edit().putBoolean(PREF_USER_REGISTERED, registered).commit();
    }

    public void setUserRegistered() {
        mSharedPreferences.edit().putBoolean(PREF_USER_REGISTERED, true).commit();
    }
    //---------------------------------------------------------------------------------

    public String getUserTokenKey() {
        if (mExpires != 0 && mExpires > System.currentTimeMillis()) {
            return mTokenKey;
        }
        Log.d("TOKEN", "expire token: " + mExpires);
        return null;
    }

    public void setUserTokenKey(String tokenKey) {
        mTokenKey = tokenKey;
        mSharedPreferences.edit().putString(PREF_USER_TOKEN_KEY, tokenKey).commit();
    }

    public String getUsernameKey() {
        return mUsername;
    }

    public void setUserLoggedIn(boolean b) {
        mUserLoggedIn = b;
        mSharedPreferences.edit().putBoolean(PREF_USER_LOGGEDIN, mUserLoggedIn).commit();
    }

    public boolean getUserLoggedIn() {
        return mUserLoggedIn;
    }

    public void setUsernameKey(String username) {
        mUsername = username;
        mSharedPreferences.edit().putString(PREF_USERNAME_KEY, username).commit();
    }

    public String getPasswordKey() {
        return mPassword;
    }

    public void setPasswordKey(String password) {
        mPassword = password;
        mSharedPreferences.edit().putString(PREF_PASSWORD_KEY, password).commit();
    }

    public long getExpires() {
        return mExpires;
    }

    public void setExpires(long expires) {
//        expires = System.currentTimeMillis() + 60 * 1000;
        mExpires = expires;
        mSharedPreferences.edit().putLong(PREF_EXPIRES_KEY, expires).commit();
    }

    public String getDeviceToken() {
        return mDeviceToken;
    }

    public void setDeviceToken(String pDeviceToken) {
        mDeviceToken = pDeviceToken;
        mSharedPreferences.edit().putString(PREF_DEVICE_TOKEN, mDeviceToken).commit();
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String pDeviceId) {
        mDeviceId = pDeviceId;
        mSharedPreferences.edit().putString(PREF_DEVICE_ID, mDeviceId).commit();
    }

    public ObscuredSharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public String getKey(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void setKey(String key, String value) {
        mSharedPreferences.edit().putString(key, value).commit();
    }

    public void deleteKey(String key) {
        mSharedPreferences.edit().remove(key).commit();
    }


    public void logOut() {
        mUserLoggedIn = false;
        mTokenKey = "";
//        mUsername = "";
        mPassword = "";
        mDeviceToken = null;
//        mExpires = 0;
        deleteKey(PREF_USER_LOGGEDIN);
        deleteKey(PREF_USER_TOKEN_KEY);
//        deleteKey(PREF_USERNAME_KEY);
        deleteKey(PREF_PASSWORD_KEY);
        deleteKey(PREF_EXPIRES_KEY);
        deleteKey(PREF_DEVICE_TOKEN);
        Log.d("TAG", "log out");
    }

}
