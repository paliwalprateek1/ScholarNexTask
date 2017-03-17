package com.example.prateek.scholarnex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by prateek on 16/3/17.
 */

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScholarNex";
    private static final String TABLE_USER = "user";
    private static final String TABLE_PROGRAM = "ProgramsList";
    private static final String KEY_STUDENT_NAME = "studentName";
    private static final String KEY_PROGRAM = "program";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_STUDENT_NUMBER = "studentNumber";
    private static final String KEY_ATTENDANCE = "attendance";
    private static final String KEY_REMARKS = "remarks";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TABLE = "CREATE TABLE " + TABLE_PROGRAM + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_USERNAME + " TEXT," + KEY_PROGRAM +  " TEXT" + ")";
        db.execSQL(TABLE);

        db.execSQL("CREATE TABLE student("+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + KEY_PROGRAM + " TEXT," + KEY_STUDENT_NAME + " TEXT," + KEY_STUDENT_NUMBER +
        " TEXT," + KEY_ATTENDANCE + " TEXT," + KEY_REMARKS + " TEXT)");
    }


    public void addProgram(String username, String program) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( "username" ,username);
        values.put( "program" ,program);
        db.insert("ProgramsList", null, values);
        db.close();

    }

    public void addStudents(String program, String studentName, String studentNumber){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("program", program);
        values.put("studentName", studentName);
        values.put("studentNumber", studentNumber);
        db.insert("student", null, values);
        db.close();
    }

    public List<String> getPrograms(String username){
        SQLiteDatabase db = getWritableDatabase();
        List<String> list = new ArrayList<>();
        Cursor cursor=db.rawQuery( " SELECT * FROM ProgramsList WHERE username = '" + username +
                        "'" , null);

        if(cursor!=null && cursor.getCount()>0) {
            cursor.moveToFirst();
            do {
                if(cursor.getString(cursor.getColumnIndex("id"))!=null) {
                    String programName = cursor.getString(cursor.getColumnIndex("program"));
                    list.add(programName);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<StudentData> getStudents(String program){
        SQLiteDatabase db = getWritableDatabase();
        List<StudentData> list = new ArrayList<>();
        Cursor cursor= db.rawQuery( " SELECT * FROM student ", null);

        if (cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                if(cursor.getString(cursor.getColumnIndex("id"))!=null){
                    StudentData studentData = new StudentData();
                    studentData.setName(cursor.getString(cursor.getColumnIndex("studentName")));
                    studentData.setAttendance(cursor.getString(cursor.getColumnIndex("attendance")));
                    studentData.setNote(cursor.getString(cursor.getColumnIndex("remarks")));
                    list.add(studentData);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void markAttendance(String studentName, String attendance, String note){
        SQLiteDatabase db = getWritableDatabase();
        String strSQL = "UPDATE student SET attendance = '"+attendance+
                "' WHERE "+"studentName"+" = '"+ studentName+"'";

        String strSQL2 = "UPDATE student SET remarks = '"+note+
                "' WHERE "+"studentName"+" = '"+ studentName+"'";

        db.execSQL(strSQL);
        db.execSQL(strSQL2);
        db.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
