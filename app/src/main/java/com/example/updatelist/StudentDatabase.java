package com.example.updatelist;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StudentDatabase extends SQLiteOpenHelper {
    public StudentDatabase(Context c) {
        super(c, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE dinotine (id INTEGER PRIMARY KEY, name TEXT,course TEXT,pic TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public List<Student> getStudents() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("dinotine", new String[] { "id", "name", "course", "pic" }, null, null, null, null, null);

        List<Student> students = new ArrayList<>();

        if (!cursor.moveToFirst()) return students;

        while (!cursor.isAfterLast()) {
            @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String course = cursor.getString(cursor.getColumnIndex("course"));
            @SuppressLint("Range") String pic = cursor.getString(cursor.getColumnIndex("pic"));

            Log.e("STUD", id + "");

            Student st = new Student(Uri.parse(pic), name, course);
            st.setStudentId(id);
            students.add(st);
            cursor.moveToNext();
        }

        return students;
    }

    public long addStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", student.getName());
        values.put("course", student.getCourse());
        values.put("pic", student.getUriString());

        return db.insert("dinotine", null, values);
    }
    public void editStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", student.getName());
        values.put("course", student.getCourse());
        values.put("pic", student.getUriString());

        Log.e("asdcasd", student.getStudentId() + "");

        db.update("dinotine", values, "id=?", new String[] {String.valueOf(student.getStudentId())});
    }

    public void removeStudent(Student student){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("dinotine", "id = ?", new String[] { student.getStudentId() + "" });
    }
}
