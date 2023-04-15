package com.example.myapplication.model;

import java.util.Date;

public class Task {
    private String name;
    private Date time;
    private String priority;
    private String picture;
    private String title;
    private String opis;

    public Task(String name, Date time, String priority, String picture, String title, String opis) {
        this.name = name;
        this.time = time;
        this.priority = priority;
        this.picture = picture;
        this.title = title;
        this.opis = opis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
