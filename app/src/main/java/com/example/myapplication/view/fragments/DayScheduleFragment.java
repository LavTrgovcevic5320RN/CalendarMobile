package com.example.myapplication.view.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Timber.d("11111111111111111111111111111111111111111111111111");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.d("22222222222222222222222222222222222222222222222222");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Timber.d("33333333333333333333333333333333333333333333333333");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Timber.d("4444444444444444444444444444444444444444444444444");
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Timber.d("5555555555555555555555555555555555555555555555555");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("6666666666666666666666666666666666666666666666666");
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("7777777777777777777777777777777777777777777777777");
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerTaskViewModel = new ViewModelProvider(requireActivity()).get(RecyclerTaskViewModel.class);
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Timber.d("8888888888888888888888888888888888888888888888888");
        day = sharedPreferences.getString("title", "");
        Timber.d("Kljuc je je %s", day);


    }

    @Override
    public void onStart() {
        super.onStart();
        Timber.d("9999999999999999999999999999999999999999999999999");
    }

    @Override
    public void onStop() {
        super.onStop();
        Timber.d("10101010101010110101010110101101101011010100110101");
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.taskRecycleView);
        addBtn = view.findViewById(R.id.buttonAddTask);
    }

    private void initObservers() {
        recyclerTaskViewModel.getMap().get(day).observe(getViewLifecycleOwner(), tasks -> {
//            Timber.d("Kljucevi je %s", recyclerTaskViewModel.getMap().keySet());
//            for(String kljuc : recyclerTaskViewModel.getMap().keySet()){
//                Timber.d("Kljuc: %s", kljuc);
//                MutableLiveData<List<Task>> taskovi = recyclerTaskViewModel.getMap().get(kljuc);
//                for(Task t : taskovi.getValue()){
//                    Timber.d("Task: %s", t.getTitle());
//                }
//                Timber.d("------------------------------------------------");
//            }
//            day = sharedPreferences.getString("title", "");
//            recyclerTaskViewModel.setKey(day);
            taskAdapter.submitList(tasks);
//            Timber.d("Dan je %s", day);
        });
    }

    private void initRecycler() {
        taskAdapter = new TaskAdapter(new TaskDiffItemCallBack(), task -> {}, recyclerTaskViewModel.getTaskList(), addBtn, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);
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
        }
    }

    public void deleteTask(int position) {
        recyclerTaskViewModel.deleteTask(position);
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
