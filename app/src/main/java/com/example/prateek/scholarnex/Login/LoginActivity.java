package com.example.prateek.scholarnex.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prateek.scholarnex.Programs;
import com.example.prateek.scholarnex.R;
import com.example.prateek.scholarnex.SessionManager;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener{

    private EditText name;
    private EditText username;
    private EditText mobileNumber;
    private EditText email;
    private EditText password;
    private LoginPresenter presenter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());

        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        mobileNumber = (EditText) findViewById(R.id.number);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.register_button).setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);
    }

    @Override
    public void onClick(View v) {
        presenter.checkCredentials(name.getText().toString(),
                username.getText().toString(),
                mobileNumber.getText().toString(),
                email.getText().toString(), password.getText().toString());
    }

    @Override
    public void showError(){
        Toast.makeText(this, "Fields Cannot be Empty", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void goToSubjectClass() {
        sessionManager.createLoginSession(name.getText().toString(), username.getText().toString(),
                mobileNumber.getText().toString(), email.getText().toString(),
                password.getText().toString());
        startActivity(new Intent(this, Programs.class));
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

}

