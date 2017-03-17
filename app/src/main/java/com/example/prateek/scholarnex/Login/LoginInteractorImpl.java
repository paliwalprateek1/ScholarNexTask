package com.example.prateek.scholarnex.Login;

import android.text.TextUtils;

/**
 * Created by prateek on 16/3/17.
 */

public class LoginInteractorImpl implements LoginInteractor {

    @Override
    public void register(final String name, final String username, final String mobieNumber,
                         final String email, final String password, final OnLoginFinishedListener listener) {

            boolean error = false;
            if (TextUtils.isEmpty(name)){
                listener.onError();
                error = true;
                return;
            }
            if (TextUtils.isEmpty(username)){
                error = true;
                listener.onError();
                return;
            }
            if (TextUtils.isEmpty(mobieNumber)){
                error = true;
                listener.onError();
                return;
            }
            if (TextUtils.isEmpty(email)){
                error = true;
                listener.onError();
                return;
            }
            if (TextUtils.isEmpty(password)){
                error = true;
                listener.onError();
                return;
            }
            if (!error){
                listener.onSuccess();
            }
    }
}
