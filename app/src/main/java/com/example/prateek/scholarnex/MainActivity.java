package com.example.prateek.scholarnex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prateek.scholarnex.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.checkLogin()){
            Intent intent = new Intent(this, Programs.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        finish();

    }
}
