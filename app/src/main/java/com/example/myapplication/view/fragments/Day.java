package com.example.myapplication.view.fragments;

public class Day {
    private int day; // Dan u mesecu
    private int month;
    private int year;
    private boolean hasEvents; // Da li dan ima obaveze ili ne

    public Day(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public boolean hasEvents() {
        return hasEvents;
    }

    public void setHasEvents(boolean hasEvents) {
        this.hasEvents = hasEvents;
    }
}
