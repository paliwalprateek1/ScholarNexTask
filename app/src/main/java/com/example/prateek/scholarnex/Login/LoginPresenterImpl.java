package com.example.prateek.scholarnex.Login;

/**
 * Created by prateek on 16/3/17.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener{

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();

    }

    @Override
    public void checkCredentials(String name, String username,
                          String mobileNumber, String email, String password) {
        if (loginView != null) {
        }
        loginInteractor.register(name, username, mobileNumber, email, password, this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onError() {
        if (loginView != null) {
            loginView.showError();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.goToSubjectClass();
        }//add user sharedprefs here here

    }

}
