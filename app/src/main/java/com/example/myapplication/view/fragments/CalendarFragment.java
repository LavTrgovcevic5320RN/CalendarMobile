package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Day;
import com.example.myapplication.view.recycler.adapter.CalendarAdapter;
import com.example.myapplication.view.recycler.differ.DayDiffItemCallBack;
import com.example.myapplication.viewmodels.RecyclerViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewModel recyclerViewModel;
    private CalendarAdapter calendarAdapter;
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recycler, container, false);
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        daysList = getDaysList(Calendar.getInstance());
        calendarAdapter = new CalendarAdapter(new DayDiffItemCallBack(), day -> {
            Toast.makeText(this.getActivity(), day.getId() + "", Toast.LENGTH_SHORT).show();
        });

        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        recyclerView = view.findViewById(R.id.listRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(calendarAdapter);

        recyclerViewModel.getDays().observe(getViewLifecycleOwner(), days -> {
            calendarAdapter.submitList(days);
        });
        return view;
    }

    // Metoda za generisanje liste dana za prikaz u kalendaru
    private ArrayList<Day> getDaysList(Calendar calendar) {
        ArrayList<Day> daysList = new ArrayList<>();
        // Dodajemo dana u listu za prikaz u kalendaru
        // Primer za dodavanje dana, može se prilagoditi prema vašim potrebama
        int j = 1;
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            Day day = new Day(j, i, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            daysList.add(day);
            j++;
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
