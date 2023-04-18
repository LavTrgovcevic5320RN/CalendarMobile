package com.example.myapplication.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String title;
    private String time;
    private String priority;
    private String picture;
    private String description;

    public Task(String title, String time, String priority, String description) {
        this.title = title;
        this.time = time;
        this.priority = priority;
        this.description = description;
    }

    @Override
    public String toString() {
        return title + "," + time + "," + priority + "," + description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
