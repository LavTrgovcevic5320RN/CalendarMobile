package com.example.myapplication.view.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.view.recycler.adapter.CalendarAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.example.myapplication.R;
import com.example.myapplication.viewmodels.RecyclerViewModel;

public class RecyclerActivity extends AppCompatActivity {

    // View components
    private RecyclerView recyclerView;
    private EditText editText;
    private Button addBtn;
    private ConstraintLayout mainLayout;

    private RecyclerViewModel recyclerViewModel;
    private CalendarAdapter calendarAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init();
    }

    private void init() {
        initView();
        initObservers();
    }

    private void initView() {
        mainLayout = findViewById(R.id.recyclerMainLayout);
        recyclerView = findViewById(R.id.listRv);
        editText = findViewById(R.id.inputEt);
        addBtn = findViewById(R.id.doMagicBtn);
    }

    private void showAddSnackBar(int id) {
        Snackbar
                .make(mainLayout, "Item added", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> recyclerViewModel.removeDay(id))
                .show();
    }

    private void initObservers() {
        recyclerViewModel.getDays().observe(this, days -> {
            calendarAdapter.submitList(days);
        });
    }

//    private void initRecycler() {
//        calendarAdapter = new CarAdapter(new CarDiffItemCallback(), car -> {
//            Toast.makeText(this, car.getId() + "", Toast.LENGTH_SHORT).show();
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(calendarAdapter);
//    }

}