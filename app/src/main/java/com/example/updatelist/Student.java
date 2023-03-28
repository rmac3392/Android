package com.example.updatelist;

import android.net.Uri;

import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    public long getStudentId() {
        return studentId;
    }

    private long studentId;
    private int imageId;

    private String name;

    private String course;

    private String uriString;

    public Student(int imageId, String text, String course){
        this.imageId = imageId;
        this.name = text;
        this.course = course;
    }

    public Student(Uri uri, String name, String course){
        this.uriString = uri.toString();
        this.course = course;
        this.name = name;
    }

    public void setStudentId(long id) {
        this.studentId = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse(){  return  course; }

    public void setCourse(String course) { this.course = course; }

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }


}
