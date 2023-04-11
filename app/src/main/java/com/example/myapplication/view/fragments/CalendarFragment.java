package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.view.calendar.CalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarAdapter calendarAdapter;
    private ArrayList<Day> daysList;
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        daysList = getDaysList(Calendar.getInstance());
        calendarAdapter = new CalendarAdapter(daysList);
        recyclerView.setAdapter(calendarAdapter);
        return view;
    }

    // Metoda za generisanje liste dana za prikaz u kalendaru
    private ArrayList<Day> getDaysList(Calendar calendar) {
        ArrayList<Day> daysList = new ArrayList<>();
        // Dodajemo dana u listu za prikaz u kalendaru
        // Primer za dodavanje dana, može se prilagoditi prema vašim potrebama
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            Day day = new Day(i, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            daysList.add(day);
        }
        return daysList;
    }

    // Metoda za ažuriranje naslova meseca na vrhu ekrana
    private void updateMonthTitle(Calendar calendar) {
        String monthTitle = monthFormat.format(calendar.getTime());
        // Postavljamo naslov meseca na vrhu ekrana
        // Primer kako se može ažurirati naslov u TextView-u sa id-jem monthTitleTextView
        TextView monthTitleTextView = getView().findViewById(R.id.monthTitleTextView);
        monthTitleTextView.setText(monthTitle);
    }
}
