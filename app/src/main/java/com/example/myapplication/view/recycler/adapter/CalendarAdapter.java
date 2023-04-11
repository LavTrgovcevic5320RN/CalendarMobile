package com.example.myapplication.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Day;

import androidx.annotation.NonNull;

import java.util.function.Consumer;

import timber.log.Timber;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false);
        return new ViewHolder(view, view.getContext(), position -> {
            Day day = getItem(position);
            onDayClicked.accept(day);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = getItem(position);
        holder.bind(day);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        public void bind(Day day) {
//            Timber.d("-------------------------------------------");
//            Timber.d(day.getPicture());
//            Timber.d("-------------------------------------------");
            ImageView imageView = itemView.findViewById(R.id.carPictureIv);
            Glide.with(context)
                    .load(day.getPicture())
                    .circleCrop()
                    .into(imageView);
            Timber.d("-------------------------------------------");
            Timber.d(String.valueOf(day.getDay()));
            Timber.d("-------------------------------------------");
            ((TextView) itemView.findViewById(R.id.manufacturerTv)).setText(String.valueOf(day.getDay()));
        }

    }

}