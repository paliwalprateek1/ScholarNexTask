package com.example.prateek.scholarnex.Login;

/**
 * Created by prateek on 16/3/17.
 */
public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onError();
        void onSuccess();
    }

    void register(String name, String username, String number, String email,
                  String password, OnLoginFinishedListener listener);

}