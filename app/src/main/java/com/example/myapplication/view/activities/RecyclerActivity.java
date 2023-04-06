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
//    private CarAdapter carAdapter;/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init();
    }

    private void init() {
        initView();
        initListeners();
        initObservers();
//        initRecycler();
    }

    private void initView() {
        mainLayout = findViewById(R.id.recyclerMainLayout);
        recyclerView = findViewById(R.id.listRv);
        editText = findViewById(R.id.inputEt);
        addBtn = findViewById(R.id.doMagicBtn);
    }

    private void initListeners() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
//                recyclerViewModel.filterCars(s.toString());
            }
        });

        addBtn.setOnClickListener(v -> {
//            showAddSnackBar(
//                    recyclerViewModel.addCar("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9vMHQzf3GMYiI2WnYG9TUKnGAQFevruSgJF35VLAJe_odBMVd&usqp=CAU",
//                            "Ikea",
//                            "LILLABO")
//            );
        });
    }

    private void showAddSnackBar(int id) {
        Snackbar
                .make(mainLayout, "Item added", Snackbar.LENGTH_SHORT)
//                .setAction("Undo", view -> recyclerViewModel.removeCar(id))
                .show();
    }

    private void initObservers() {
//        recyclerViewModel.getCars().observe(this, cars -> {
//            carAdapter.submitList(cars);
//        });
    }

//    private void initRecycler() {
//        carAdapter = new CarAdapter(new CarDiffItemCallback(), car -> {
//            Toast.makeText(this, car.getId() + "", Toast.LENGTH_SHORT).show();
//        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(carAdapter);
//    }

}