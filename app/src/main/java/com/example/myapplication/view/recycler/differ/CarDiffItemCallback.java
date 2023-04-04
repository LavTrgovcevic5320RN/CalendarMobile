package com.example.myapplication.view.recycler.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.myapplication.models.Car;

public class CarDiffItemCallback extends DiffUtil.ItemCallback<Car> {
    @Override
    public boolean areItemsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Car oldItem, @NonNull Car newItem) {
        return oldItem.getPicture().equals(newItem.getPicture())
                && oldItem.getManufacturer().equals(newItem.getManufacturer())
                && oldItem.getModel().equals(newItem.getModel());
    }
}
