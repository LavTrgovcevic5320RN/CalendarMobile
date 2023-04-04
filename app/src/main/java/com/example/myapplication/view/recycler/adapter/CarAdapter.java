package com.example.myapplication.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.function.Consumer;
import com.example.myapplication.R;
import com.example.myapplication.models.Car;

public class CarAdapter extends ListAdapter<Car, CarAdapter.ViewHolder> {

    private final Consumer<Car> onCarClicked;

    public CarAdapter(@NonNull DiffUtil.ItemCallback<Car> diffCallback, Consumer<Car> onCarClicked) {
        super(diffCallback);
        this.onCarClicked = onCarClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Car car = getItem(position);
            onCarClicked.accept(car);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = getItem(position);
        holder.bind(car);
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

        public void bind(Car car) {
            ImageView imageView = itemView.findViewById(R.id.carPictureIv);
            Glide
                .with(context)
                .load(car.getPicture())
                .circleCrop()
                .into(imageView);
            ((TextView) itemView.findViewById(R.id.manufacturerTv)).setText(car.getManufacturer());
            ((TextView) itemView.findViewById(R.id.modelTv)).setText(car.getModel());
        }

    }
}
