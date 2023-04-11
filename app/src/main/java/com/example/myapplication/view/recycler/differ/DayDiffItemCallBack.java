package com.example.myapplication.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.model.Day;

public class DayDiffItemCallBack extends DiffUtil.ItemCallback<Day> {

    @Override
    public boolean areItemsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return     oldItem.getDay()   == newItem.getDay()
                && oldItem.getMonth() == newItem.getMonth()
                && oldItem.getYear()  == newItem.getYear();
    }
}
