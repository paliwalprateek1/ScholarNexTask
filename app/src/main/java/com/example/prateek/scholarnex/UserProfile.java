package com.example.prateek.scholarnex;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class UserProfile extends AppCompatActivity {

    private SessionManager sessionManager;
    private EditText name;
    private EditText username;
    private EditText email;
    private EditText number;
    private HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name = (EditText)findViewById(R.id.name);
        username = (EditText)findViewById(R.id.username);
        email = (EditText)findViewById(R.id.email);
        number = (EditText)findViewById(R.id.number);

        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();

        name.setText(user.get(SessionManager.KEY_NAME));
        username.setText(user.get(SessionManager.KEY_USERNAME));
        email.setText(user.get(SessionManager.KEY_EMAIL));
        number.setText(user.get(SessionManager.KEY_NUMBER));
    }

    public void saveChanges(View view) {
        sessionManager.editUserDetail("name", name.getText().toString());
        sessionManager.editUserDetail("username", username.getText().toString());
        sessionManager.editUserDetail("email", email.getText().toString());
        sessionManager.editUserDetail("number", number.getText().toString());
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Changes Not Saved", Toast.LENGTH_SHORT).show();
        finish();
        return;
    }
}
