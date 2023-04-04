package com.example.myapplication.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.RotationViewModel;
import timber.log.Timber;

public class RotationActivity extends AppCompatActivity {

    // View components
    private EditText editText;
    private CheckBox checkBox;
    private Button setTextBtn;
    private TextView rotationTv;

    // codes
    public static String MESSAGE_KEY = "message";

    private String text = "";
    private RotationViewModel rotationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation);
        Timber.e("onCreate");
//        rotationViewModel = new ViewModelProvider(this).get(RotationViewModel.class);
        // Getting the information when screen rotating
//        if(savedInstanceState != null) {
//            text = savedInstanceState.getString(MESSAGE_KEY);
//        }
        init();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.e("onSaveInstanceState");
        outState.putString(MESSAGE_KEY, text);
    }


    private void init() {
        initView();
        initListeners();
//        initObservers();
    }

    private void initView() {
        editText = findViewById(R.id.inputEt);
        checkBox = findViewById(R.id.checkbox);
        setTextBtn = findViewById(R.id.setTextBtn);
        rotationTv = findViewById(R.id.rotationTv);
        // We are using this setter when working with onSaveInstanceSate
//        rotationTv.setText(text);
    }

    private void initListeners() {
        setTextBtn.setOnClickListener(v -> {
            text = editText.getText().toString();
            // We are using this setter when working with onSaveInstanceSate
            rotationTv.setText(text);
//            rotationViewModel.setMessage(text);
        });
    }

    private void initObservers() {
        rotationViewModel.getMessage().observe(this, message -> {
            rotationTv.setText(message);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.e("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.e("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Timber.e("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.e("onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.e("onDestroy");
    }

}