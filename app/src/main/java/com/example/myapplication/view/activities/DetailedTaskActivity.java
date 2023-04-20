package com.example.myapplication.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import timber.log.Timber;

public class DetailedTaskActivity extends AppCompatActivity {
    private ImageView imageViewClock;
    private TextView detailedTime;
    private TextView detailedTitle;
    private TextView detailedDescription;
    private Button buttonEdituj;
    private Button btnDelete;
    private ArrayList<Task> tasks = new ArrayList<>();;
    private Task task;
    private int pozicijaTaskaUListi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_task);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        tasks = (ArrayList<Task>) args.getSerializable("ARRAYLIST");
        pozicijaTaskaUListi = (int) args.getSerializable("Pozicija taska u listi");
        task = tasks.get(pozicijaTaskaUListi);

        String title  = (String) args.getSerializable("Title");
        setTitle(title);

        Timber.d("------------------------");
        Timber.d(task.getPriority());
        Timber.d("------------------------");

        initView();
        initListeners();
    }

    private void initView(){
        imageViewClock = findViewById(R.id.slikaSata);
        detailedTime = findViewById(R.id.detailedTime);
        detailedTitle = findViewById(R.id.detailedTitle);
        detailedDescription = findViewById(R.id.detailedDescription);
        buttonEdituj = findViewById(R.id.buttonEdituj);
        btnDelete = findViewById(R.id.buttonObrisi);

        detailedTime.setText(task.getTime());
        detailedTitle.setText(task.getTitle());
        detailedDescription.setText(task.getDescription());
    }

    private void initListeners(){
        buttonEdituj.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("posao", false);
            intent.putExtra("position", pozicijaTaskaUListi);
            setResult(RESULT_OK, intent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("posao", true);
            intent.putExtra("position", pozicijaTaskaUListi);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}