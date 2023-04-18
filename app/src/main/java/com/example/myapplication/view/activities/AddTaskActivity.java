package com.example.myapplication.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private TextView textViewTime;
    private EditText editTextDescription;
    private Button btnAddTask;
    private Button btnCancel;
    private ArrayList<Task> tasks = new ArrayList<>();;
    private Calendar calendar;
    private SimpleDateFormat timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        tasks = (ArrayList<Task>) args.getSerializable("ARRAYLIST");

        initView();
        initListeners();
    }

    private void initView(){
        editTextTitle = findViewById(R.id.editTextTitle);
        textViewTime = findViewById(R.id.textViewTime);
        editTextDescription = findViewById(R.id.editTextDescription);
        btnAddTask = findViewById(R.id.buttonDodajTask);
        btnCancel = findViewById(R.id.buttonCancel);

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    }

    private void initListeners(){
        btnAddTask.setOnClickListener(v -> {
            String naziv = editTextTitle.getText().toString();
            String vreme = textViewTime.getText().toString();
            String opis = editTextDescription.getText().toString();

            if (naziv.isEmpty() || vreme.isEmpty() || opis.isEmpty()) {
                showAlertDialog("Greška", "Morate uneti sve podatke.");
            } else {
                Task task = new Task(naziv, vreme, "MID", opis);
                if (proveriPodudaranjeTermina(task)) {
                    showAlertDialog("Greška", "Obaveza se podudara sa već postojećom obavezom.");
                } else {
                    tasks.add(task);
                    Intent intent = new Intent();
                    intent.putExtra("newTask", task.toString());
                    setResult(RESULT_OK, intent);
                    showAlertDialog("Uspeh", "Obaveza uspešno kreirana.");
                    finish();
                }
            }
        });

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> {
            finish();
        });

        textViewTime.setOnClickListener(v -> showTimePicker());
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calendar.set(Calendar.MINUTE, minute);
                    String dateTime = timeFormat.format(calendar.getTime());
                    textViewTime.setText(dateTime);
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);

        timePickerDialog.show();
    }

    private boolean proveriPodudaranjeTermina(Task noviTask) {
        for (Task task : tasks) {
            if (task.getTime().equals(noviTask.getTime())) {
                return true;
            }
        }
        return false;
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}