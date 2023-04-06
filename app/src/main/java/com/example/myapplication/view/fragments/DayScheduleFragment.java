package com.example.myapplication.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
//import com.example.myapplication.view.recycler.adapter.ScheduleAdapter;
//import com.example.myapplication.view.recycler.adapter.ScheduleItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.List;

public class DayScheduleFragment extends Fragment {

    private RecyclerView recyclerView;
//    private ScheduleAdapter adapter;

    public DayScheduleFragment() {
        // Required empty public constructor
    }

    public static DayScheduleFragment newInstance(CalendarDay day) {
        DayScheduleFragment fragment = new DayScheduleFragment();
        Bundle args = new Bundle();
        args.putParcelable("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_schedule, container, false);

        recyclerView = view.findViewById(R.id.schedule_recycler_view);
//        adapter = new ScheduleAdapter(getScheduleItems());
//        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

//    private List<ScheduleItem> getScheduleItems() {
//        List<ScheduleItem> items = new ArrayList<>();
//        // Get schedule items for the selected day and add to the list
//        // ...
//        return items;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            CalendarDay day = getArguments().getParcelable("day");
            if (day != null) {
                // Do something with the selected day
            }
        }
    }
}
