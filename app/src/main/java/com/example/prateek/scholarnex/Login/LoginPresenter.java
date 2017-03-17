package com.example.prateek.scholarnex.Login;

/**
 * Created by prateek on 16/3/17.
 */

public interface LoginPresenter {
    void checkCredentials(String name, String username,
                      String mobileNumber, String email, String password);

    void onDestroy();
}
