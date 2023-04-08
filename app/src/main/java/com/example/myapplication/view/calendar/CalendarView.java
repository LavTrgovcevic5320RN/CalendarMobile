package com.example.myapplication.view.calendar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CalendarView extends AppCompatActivity {
    private RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;
    private List<Date> dates;
    private int highestPriorityTaskDateIndex;

    private static final int MAX_CALENDAR_DAYS = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);

        dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (dates.size() < MAX_CALENDAR_DAYS) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        highestPriorityTaskDateIndex = determineHighestPriorityTaskDateIndex();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarAdapter = new CalendarAdapter(dates, highestPriorityTaskDateIndex);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private int determineHighestPriorityTaskDateIndex() {
        // TODO: Implement logic to determine the index of the date with the highest priority task
        // For this example, we'll just return a random index
        return new Random().nextInt(MAX_CALENDAR_DAYS);
    }

}
