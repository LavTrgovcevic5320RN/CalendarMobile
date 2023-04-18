package com.example.myapplication.view.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Task;
import com.example.myapplication.view.fragments.DayScheduleFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import timber.log.Timber;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.ViewHolder> {

    private final Consumer<Task> onTaskClicked;
    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
    private ArrayList<Task> taskList;
    private Button deleteButton;
    private Button editButton;
    private FloatingActionButton addButton;
    private DayScheduleFragment dayScheduleFragment;

    public TaskAdapter(@NonNull DiffUtil.ItemCallback<Task> diffCallback, Consumer<Task> onTaskClicked, ArrayList<Task> taskList, FloatingActionButton addButtonTask, DayScheduleFragment dayScheduleFragment) {
        super(diffCallback);
        this.taskList = taskList;
        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        this.tasks.setValue(listToSubmit);
        this.addButton = addButtonTask;
        this.dayScheduleFragment = dayScheduleFragment;

        this.onTaskClicked = onTaskClicked;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_task_item, parent, false);
        editButton = view.findViewById(R.id.buttonEditTask);
        deleteButton = view.findViewById(R.id.buttonDeleteTask);
        return new TaskAdapter.ViewHolder(view, view.getContext(), position -> {
            Task task = getItem(position);
            onTaskClicked.accept(task);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        Task task = getItem(position);
        holder.bind(task);

        /*holder.itemView.setOnClickListener(view -> {
            dayScheduleFragment.editTask(task);
        });*/

        editButton.setOnClickListener(view -> {
            dayScheduleFragment.editTask(position);
        });

        deleteButton.setOnClickListener(view -> {
            Timber.d("Brisanje taskova");
//            dayScheduleFragment.initTitle();
            dayScheduleFragment.deleteTask(position);
        });

        addButton.setOnClickListener(view -> {
            Timber.d("Dodavanje taskova");
//            dayScheduleFragment.initTitle();
            dayScheduleFragment.addTask();
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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

        public void bind(Task task) {
            ImageView imageView = itemView.findViewById(R.id.taskPicture);
            Glide
                    .with(context)
                    .load(task.getPicture())
                    .circleCrop()
                    .into(imageView);
            ((TextView) itemView.findViewById(R.id.timeTaskTextView)).setText(task.getTime().toString());
            ((TextView) itemView.findViewById(R.id.titleTaskTextView)).setText(task.getTitle());
        }





    }

}