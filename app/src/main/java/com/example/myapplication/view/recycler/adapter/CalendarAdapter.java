package com.example.myapplication.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Day;
import com.example.myapplication.view.activities.MainActivity;

import androidx.annotation.NonNull;

import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Consumer;

public class CalendarAdapter extends ListAdapter<Day, CalendarAdapter.ViewHolder> {
    private final Consumer<Day> onDayClicked;

    public CalendarAdapter(@NonNull DiffUtil.ItemCallback<Day> diffCallback, Consumer<Day> onDayClicked) {
        super(diffCallback);
        this.onDayClicked = onDayClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_day_item, parent, false);
        return new ViewHolder(view, view.getContext(), position -> {
            Day day = getItem(position);
            onDayClicked.accept(day);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = getItem(position);
        holder.bind(day);

        holder.itemView.setOnClickListener(view -> {
            ((MainActivity)holder.itemView.getContext()).openDay(day);
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView dayOfWeek;
        private TextView dayOfMonth;


        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });

            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);
            dayOfMonth = itemView.findViewById(R.id.dayOfMonth);

        }

        public void bind(Day day) {
//            Timber.d("Dan 1:  %s", day.getLocalDate());
//            Timber.d("Dan 2:  %s", day.getLocalDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
//            Timber.d("Dan 3:  %s", day.getLocalDate().getDayOfMonth());
//            Timber.d("------------------------------------------------");

            dayOfWeek.setText(day.getLocalDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            dayOfMonth.setText(String.valueOf(day.getLocalDate().getDayOfMonth()));

        }

    }

}