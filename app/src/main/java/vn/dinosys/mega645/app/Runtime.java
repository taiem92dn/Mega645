package vn.dinosys.mega645.app;

import android.util.Log;

import vn.dinosys.mega645.util.BaseRuntime;
import vn.dinosys.mega645.util.security.ObscuredSharedPreferences;
import vn.dinosys.mega645.util.security.SecurityUtil;

/**
 * Created by huutai.
 * Since: 8/18/16 on 11:57 AM
 * Project: Mega645
 */
public class Runtime extends BaseRuntime {

    public static final String PREF_LOGIN_TYPE = SecurityUtil.sha256("PREF_LOGIN_TYPE");

    protected int mLoginType;

    public Runtime(ObscuredSharedPreferences sharedPreferences) {
        super(sharedPreferences);

        mLoginType = mSharedPreferences.getInt(PREF_LOGIN_TYPE, -1);
    }

    public void setLoginType(int pType) {
        mLoginType = pType;
        mSharedPreferences.edit().putInt(PREF_LOGIN_TYPE, pType).commit();
    }

    public int getLoginType() {
        return mLoginType;
    }

    public void saveUser(String email, String accessToken, int type) {
        this.setUsernameKey(email);
        this.setUserTokenKey(accessToken);
        this.setLoginType(type);
        this.setUserLoggedIn(true);
        Log.d("Runtime", email + " " + accessToken + " " + type);
    }

    @Override
    public void logOut() {
        super.logOut();
        mLoginType = -1;
        deleteKey(PREF_LOGIN_TYPE);
    }
}
