package com.example.prateek.scholarnex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {

    private EditText studentName;
    private EditText studentNumber;
    private DataBase dataBase = new DataBase(this);
    private String program;
    private List<StudentData> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        studentName = (EditText)findViewById(R.id.studentName);
        studentNumber = (EditText)findViewById(R.id.studentMobile);
        program = getIntent().getStringExtra("programName");
    }

    public void addStudent(View view) {
        list = dataBase.getStudents(program);
        if(!list.contains(studentName.getText().toString())) {
            dataBase.addStudents(program, studentName.getText().toString(), studentNumber.getText().toString());
            studentName.setText(null);
            studentNumber.setText(null);
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "Already Exists", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel(View view) {
        finish();
    }

}
