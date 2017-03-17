package com.example.prateek.scholarnex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.prateek.scholarnex.Login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Programs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private SessionManager sessionManager;
    private HashMap<String, String> user;
    private ListView listView;
    private Button addProgram;
    private EditText editTextAddProgram;
    private DataBase dataBase = new DataBase(this);
    private List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sessionManager is called, it has sharedPreferences and all the userdetails
        sessionManager = new SessionManager(getApplicationContext());
        user = sessionManager.getUserDetails();

        listView = (ListView)findViewById(R.id.listView);

        //List of all the programs for this username
        items = dataBase.getPrograms(user.get(SessionManager.KEY_USERNAME));

        //edit text field to add a new program
        editTextAddProgram = (EditText)findViewById(R.id.editTextAddProgram);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //setting up list view
        listView.setAdapter(new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, items));


        //onClick listener of programs, will lead to adding students and marking attendances
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                new android.app.AlertDialog.Builder(Programs.this)
                        .setTitle(listView.getItemAtPosition(position).toString())
                        .setPositiveButton("Add Student", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Programs.this, AddStudent.class);
                                intent.putExtra("programName", listView.getItemAtPosition(position).toString());
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Mark Attendance", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Programs.this, Attendees.class);
                                intent.putExtra("program", listView.getItemAtPosition(position).toString());
                                startActivity(intent);
                                dialog.dismiss();
                            }
                        }).show();
                return;
            }
        });

        //button that adds program to database
        addProgram = (Button)findViewById(R.id.addProgram);
        addProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.add(editTextAddProgram.getText().toString());
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, items));
                dataBase.addProgram(user.get(SessionManager.KEY_USERNAME),
                        editTextAddProgram.getText().toString());
                editTextAddProgram.setText(null);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_user_profile) {
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(intent);
        } else if (id == R.id.sign_out) {
            sessionManager.logoutUser();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
