package com.example.myapplication.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;
import com.example.myapplication.view.activities.AddTaskActivity;
import com.example.myapplication.view.activities.DetailedTaskActivity;
import com.example.myapplication.view.activities.EditTaskActivity;
import com.example.myapplication.view.recycler.adapter.TaskAdapter;
import com.example.myapplication.view.recycler.differ.TaskDiffItemCallBack;
import com.example.myapplication.viewmodels.RecyclerTaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import timber.log.Timber;

public class DayScheduleFragment extends Fragment implements View.OnCreateContextMenuListener {
    private TextView lowPriority;
    private TextView midPriority;
    private TextView highPriority;

    private RecyclerView recyclerView;
    private RecyclerTaskViewModel recyclerTaskViewModel;
    private TaskAdapter taskAdapter;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton addBtn;
    private View view;
    private String day = "";
    private boolean showPastTasks = true;
    private int selectedSortOption = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.day_schedule_fragment, container, false);
        recyclerTaskViewModel = new ViewModelProvider(requireActivity()).get(RecyclerTaskViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        recyclerTaskViewModel.prepare(day);
        init();
        return view;
    }

    private void init() {
        initView();
        initObservers();
        initRecycler();
    }

    private void initView() {
        lowPriority = view.findViewById(R.id.lowPriorityDayTask);
        midPriority = view.findViewById(R.id.midPriorityDayTask);
        highPriority = view.findViewById(R.id.highPriorityDayTask);
        recyclerView = view.findViewById(R.id.taskRecycleView);
        addBtn = view.findViewById(R.id.buttonAddTask);
    }

    private void initObservers() {
        lowPriority.setOnClickListener(view -> onTextViewClick(view, lowPriority.getText().toString()));

        midPriority.setOnClickListener(view -> onTextViewClick(view, midPriority.getText().toString()));

        highPriority.setOnClickListener(view -> onTextViewClick(view, highPriority.getText().toString()));

        recyclerTaskViewModel.getMap().get(day).observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.submitList(tasks);
        });
    }

    private void initRecycler() {
        taskAdapter = new TaskAdapter(new TaskDiffItemCallBack(), task -> {}, recyclerTaskViewModel.getTaskList(), addBtn, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerTaskViewModel = new ViewModelProvider(requireActivity()).get(RecyclerTaskViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Timber.d("8888888888888888888888888888888888888888888888888");
        day = sharedPreferences.getString("title", "");
//        Timber.d("Kljuc je je %s", day);
    }

    public void showDetailedTask(int position) {
        Intent intent = new Intent(this.getContext(), DetailedTaskActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", recyclerTaskViewModel.getTaskList());
        args.putSerializable("Pozicija taska u listi", position);
        args.putSerializable("Title", requireActivity().getTitle().toString());
        intent.putExtra("BUNDLE", args);

        startActivityForResult(intent, 3);
    }

    public void editTask(int position) {
        Intent intent = new Intent(this.getContext(), EditTaskActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", recyclerTaskViewModel.getTaskList());
        args.putSerializable("Pozicija taska u listi", position);
        intent.putExtra("BUNDLE", args);

        startActivityForResult(intent, 2);
    }

    public void addTask() {
        Intent intent = new Intent(this.getContext(), AddTaskActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", recyclerTaskViewModel.getTaskList());
        intent.putExtra("BUNDLE", args);

        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String returnString = data.getStringExtra("newTask");
            String[] components = returnString.split(",");

            String title = components[0];
            String time = components[1];
            String priority = components[2];
            String description = components[3];

            Task task = new Task(title, time, priority, description);
            task.setPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9vMHQzf3GMYiI2WnYG9TUKnGAQFevruSgJF35VLAJe_odBMVd&usqp=CAU");
            recyclerTaskViewModel.addTask(task);

        }else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            int position = data.getIntExtra("position", 0);
            String returnString = data.getStringExtra("newTask");
            String[] components = returnString.split(",");

            String title = components[0];
            String time = components[1];
            String priority = components[2];
            String description = components[3];

            Task task = new Task(title, time, priority, description);
            task.setPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9vMHQzf3GMYiI2WnYG9TUKnGAQFevruSgJF35VLAJe_odBMVd&usqp=CAU");
            recyclerTaskViewModel.addTaskWithPosition(task, position);

        }else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            boolean zaBrisanjeIliEditovanje = data.getBooleanExtra("posao", false);
            int position = data.getIntExtra("position", 0);
            if(zaBrisanjeIliEditovanje){
                recyclerTaskViewModel.deleteTask(position);
            }else{
                Timber.d("USAO");

            }
        }
    }

    public void deleteTask(int position) {
        recyclerTaskViewModel.deleteTask(position);
    }

    public void onTextViewClick(View view, String priority) {
        view.setAlpha(1.0f);

        switch (view.getId()) {
            case R.id.lowPriorityEditTask:
                midPriority.setAlpha(0.5f);
                highPriority.setAlpha(0.5f);
                break;
            case R.id.midPriorityEditTask:
                lowPriority.setAlpha(0.5f);
                highPriority.setAlpha(0.5f);
                break;
            case R.id.highPriorityEditTask:
                lowPriority.setAlpha(0.5f);
                midPriority.setAlpha(0.5f);
                break;
        }
        recyclerTaskViewModel.filterTask(priority);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
