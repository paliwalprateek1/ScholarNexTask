package com.example.prateek.scholarnex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by prateek on 16/3/17.
 */

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "ScholarNex";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String name, String username,
                                   String mobileNumber, String email, String password){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_NUMBER, mobileNumber);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }


    public String editUserDetail(String key, String newValue){

        editor.putString(key, newValue);
        editor.commit();
        return newValue;
    }
    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            return false;
        }else{
            return true;
        }

    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_NUMBER, pref.getString(KEY_NUMBER, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}