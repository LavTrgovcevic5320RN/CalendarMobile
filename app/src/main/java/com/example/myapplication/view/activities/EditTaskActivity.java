package com.example.myapplication.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

public class EditTaskActivity extends AppCompatActivity {
    private EditText editTextTitle;
    private TextView textViewTime;
    private EditText editTextDescription;
    private Button btnSave;
    private Button btnCancel;
    private ArrayList<Task> tasks = new ArrayList<>();;
    private Task task;
    private int pozicijaTaskaUListi;
    private Calendar calendar;
    private SimpleDateFormat timeFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        tasks = (ArrayList<Task>) args.getSerializable("ARRAYLIST");
        pozicijaTaskaUListi = (int) args.getSerializable("Pozicija taska u listi");
        task = tasks.get(pozicijaTaskaUListi);

        initView();
        initListeners();
    }

    private void initView(){
        editTextTitle = findViewById(R.id.editTitle);
        textViewTime = findViewById(R.id.textTime);
        editTextDescription = findViewById(R.id.editDescription);
        btnSave = findViewById(R.id.buttonSaveTask);
        btnCancel = findViewById(R.id.buttonOtkazi);

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        editTextTitle.setText(task.getTitle());
        textViewTime.setText(task.getTime());
        Timber.d(task.getDescription());
        editTextDescription.setText(task.getDescription());
    }

    private void initListeners(){
        btnSave.setOnClickListener(v -> {
            String naziv = editTextTitle.getText().toString();
            String vreme = textViewTime.getText().toString();
            String opis = editTextDescription.getText().toString();

            if (naziv.isEmpty() || vreme.isEmpty() || opis.isEmpty()) {
                showAlertDialog("Greška", "Morate uneti sve podatke.");
            } else {
                Task task = new Task(naziv, vreme, "MID", opis);
                tasks.remove(pozicijaTaskaUListi);
                if (proveriPodudaranjeTermina(task)) {
                    showAlertDialog("Greška", "Obaveza se podudara sa već postojećom obavezom.");
                } else {
                    tasks.add(task);
                    tasks.add(pozicijaTaskaUListi, task);
                    Intent intent = new Intent();
                    intent.putExtra("newTask", task.toString());
                    intent.putExtra("position", pozicijaTaskaUListi);
                    setResult(RESULT_OK, intent);
                    showAlertDialog("Uspeh", "Obaveza uspešno kreirana.");
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(v -> {
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