package com.example.myapplication.model;

import java.time.LocalDate;

public class Day {
    LocalDate localDate;

    public Day(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
