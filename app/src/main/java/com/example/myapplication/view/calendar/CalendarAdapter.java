package com.example.myapplication.view.calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.Date;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private List<Date> dates;
    private int highestPriorityTaskDateIndex;

    public CalendarAdapter(List<Date> dates, int highestPriorityTaskDateIndex) {
        this.dates = dates;
        this.highestPriorityTaskDateIndex = highestPriorityTaskDateIndex;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendar_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Date date = dates.get(position);
        holder.dayOfMonthTextView.setText(String.valueOf(date.getDate()));

        if (position == highestPriorityTaskDateIndex) {
            holder.dayOfMonthTextView.setTextColor(Color.RED);
        } else {
            holder.dayOfMonthTextView.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dayOfMonthTextView;

        public ViewHolder(View view) {
            super(view);
            dayOfMonthTextView = (TextView) view.findViewById(R.id.dayOfMonthTextView);
        }
    }
}
