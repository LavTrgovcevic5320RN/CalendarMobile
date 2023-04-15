package com.example.myapplication.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.model.Task;

public class TaskDiffItemCallBack extends DiffUtil.ItemCallback<Task> {
    @Override
    public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getName().equals(newItem.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
        return oldItem.getName().equals(newItem.getName())
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getTime().equals(newItem.getTime());
    }
}