package com.example.myapplication.view.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.view.fragments.Day;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {
    private ArrayList<Day> daysList;

    public CalendarAdapter(ArrayList<Day> daysList) {
        this.daysList = daysList;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        Day day = daysList.get(position);
        holder.dayTextView.setText(String.valueOf(day.getDay()));
        // Dodajte kod za postavljanje klika na dan u kalendaru
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ovde možete otvoriti prozor za prikaz svih obaveza za taj dan ili izvršiti željenu akciju na klik.
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
        }
    }
}