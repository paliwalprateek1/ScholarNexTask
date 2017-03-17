package com.example.prateek.scholarnex;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Attendees extends AppCompatActivity {

    private String program;
    private ListView listView;
    private RecyclerView recyclerView;
    private StudentAdapter mAdapter;
    private DataBase dataBase = new DataBase(this);
    private String attendance;
    private List<StudentData> items = new ArrayList<>();
    private StudentData studentData = new StudentData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendees);

        program = getIntent().getStringExtra("program");
        Toast.makeText(this,program, Toast.LENGTH_SHORT).show();
        listView = (ListView)findViewById(R.id.listView);
        items = dataBase.getStudents(program);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new StudentAdapter(items);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                studentData = items.get(position);
                final Dialog dialog = new Dialog(Attendees.this);
                dialog.setContentView(R.layout.attendance);
                dialog.setTitle("Mark Attendance");
                final RadioButton rb1 = (RadioButton) dialog.findViewById(R.id.rb1);
                final RadioButton rb2 = (RadioButton) dialog.findViewById(R.id.rb2);
                final RadioButton rb3 = (RadioButton) dialog.findViewById(R.id.rb3);

                final EditText editText = (EditText)dialog.findViewById(R.id.editTextRemarks);

                rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rb1.setChecked(true);
                        rb2.setChecked(false);
                        rb3.setChecked(false);
                        attendance = "Present";
                    }
                });

                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rb1.setChecked(false);
                        rb2.setChecked(true);
                        rb3.setChecked(false);
                        attendance = "Absent";

                    }
                });

                rb3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rb1.setChecked(false);
                        rb2.setChecked(false);
                        rb3.setChecked(true);
                        attendance = "Sick";

                    }
                });
                final Button markAttendanceButton = (Button) dialog.findViewById(R.id.dialogDone);
                markAttendanceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataBase.markAttendance(studentData.getName(), attendance,
                                editText.getText().toString());
                        studentData.setAttendance(attendance);
                        studentData.setNote(editText.getText().toString());
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }
}

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    private List<StudentData> studentDataList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, attendance, note;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            attendance = (TextView) view.findViewById(R.id.attendance);
            note = (TextView) view.findViewById(R.id.note);
        }
    }


    public StudentAdapter(List<StudentData> studentDataList) {
        this.studentDataList = studentDataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_data, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StudentData studentData = studentDataList.get(position);
        holder.name.setText(studentData.getName());
        holder.attendance.setText(studentData.getAttendance());
        holder.note.setText(studentData.getNote());
    }


    @Override
    public int getItemCount() {
        return studentDataList.size();
    }
}