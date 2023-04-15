package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;
import com.example.myapplication.view.recycler.adapter.TaskAdapter;
import com.example.myapplication.view.recycler.differ.TaskDiffItemCallBack;
import com.example.myapplication.viewmodels.RecyclerTaskViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayScheduleFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerTaskViewModel recyclerTaskViewModel;
    private TaskAdapter taskAdapter;
    private View view;
    private FloatingActionButton addBtn;
    private boolean showPastTasks = true;
    private int selectedSortOption = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.day_schedule_fragment, container, false);
        recyclerTaskViewModel = new ViewModelProvider(this).get(RecyclerTaskViewModel.class);
        init();

        return view;
    }

    private void init() {
        initView();
        initObservers();
        initRecycler();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.taskRecycleView);
        addBtn = view.findViewById(R.id.buttonAddTask);
    }

    private void initObservers() {
        recyclerTaskViewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> {
            taskAdapter.submitList(tasks);
        });

//        addBtn.setOnClickListener(v -> {
//            recyclerTaskViewModel.addTask("Cas iz mate", new Date(2023,4,14,17,0),
//                    "HIGH", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9vMHQzf3GMYiI2WnYG9TUKnGAQFevruSgJF35VLAJe_odBMVd&usqp=CAU",
//                            "Cas iz mate", "Danas imam cas iz mate u 17h");
//
//        });
    }

    private void initRecycler() {
        taskAdapter = new TaskAdapter(new TaskDiffItemCallBack(), task -> {}, recyclerTaskViewModel.getTaskList(), addBtn, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdapter);
    }

    public void addTask() {
        recyclerTaskViewModel.addTask("Cas iz mate", new Date(2023,4,14,17,0),
                "HIGH", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9vMHQzf3GMYiI2WnYG9TUKnGAQFevruSgJF35VLAJe_odBMVd&usqp=CAU",
                "Cas iz mate", "Danas imam cas iz mate u 17h");
    }
}
