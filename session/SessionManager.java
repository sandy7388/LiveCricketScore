package com.sandy.wayzon_android.livecricketscore.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sandy.wayzon_android.livecricketscore.activity.LoginActivity;
import com.sandy.wayzon_android.livecricketscore.model.Login;


/**
 * Created by sandy on 25/11/17.
 */

@SuppressLint("StaticFieldLeak")
public class SessionManager
{

    private static SessionManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "agentSharedPref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";
//    private static final String KEY_MEMBER_ID = "member_id";
//    private static final String KEY_SUB_DOMAIN = "subdomain";

//    private static final String KEY_LOCATION_NAME = "location";
//    private static final String KEY_WORKING_STATUS = "working_status";

    public SessionManager(Context context)
    {
        mCtx = context;
    }
    public static synchronized SessionManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new SessionManager(context);
        }
        return mInstance;
    }

    // for user login to store  the user session
    public boolean userLogin(Login login)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, login.getMobile());
        editor.putString(KEY_USER_PASSWORD, login.getPassword());
        editor.putString(KEY_NAME,login.getOperatorName());
        editor.putString(KEY_USER_ID, login.getOperatorId());
        //editor.putString(KEY_SUB_DOMAIN, agentLogin.getSubdomain());

        editor.apply();
        editor.commit();

        return true;
    }


    //check whether the user is logged in or not
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    // Get Username
    public String getUsername()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) ;
    }

    // Get Subdomain
//    public static String getSubdomain()
//    {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_SUB_DOMAIN, null) ;
//    }

    // Get User id
    public String getUserID()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null) ;
    }

    // Get User id
//    public String getMemberID()
//    {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_MEMBER_ID, null) ;
//    }

    // Get Users Name
//    public String getName()
//    {
//        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getString(KEY_NAME,null);
//    }

    // this method is used to logout the session
    public void logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

}
