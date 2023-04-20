package com.example.myapplication.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import timber.log.Timber;

public class AddTaskActivity extends AppCompatActivity {
    private TextView lowPriority;
    private TextView midPriority;
    private TextView highPriority;
    private EditText editTextTitle;
    private TextView textViewTime;
    private ImageView clockImage;
    private EditText editTextDescription;
    private Button btnAddTask;
    private Button btnCancel;
    private ArrayList<Task> tasks = new ArrayList<>();;
    private Calendar startCal, endCal;
    private String prioritet = "";

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
        lowPriority = findViewById(R.id.lowPriorityAddTask);
        midPriority = findViewById(R.id.midPriorityAddTask);
        highPriority = findViewById(R.id.highPriorityAddTask);
        editTextTitle = findViewById(R.id.editTextTitle);
        textViewTime = findViewById(R.id.textViewTime);
        clockImage = findViewById(R.id.clockImageView);
        editTextDescription = findViewById(R.id.editTextDescription);
        btnAddTask = findViewById(R.id.buttonDodajTask);
        btnCancel = findViewById(R.id.buttonCancel);
    }

    private void initListeners(){
        lowPriority.setOnClickListener(view -> onTextViewClick(view, lowPriority.getText().toString()));

        midPriority.setOnClickListener(view -> onTextViewClick(view, midPriority.getText().toString()));

        highPriority.setOnClickListener(view -> onTextViewClick(view, highPriority.getText().toString()));

        btnAddTask.setOnClickListener(v -> {
            String naziv = editTextTitle.getText().toString();
            String vreme = textViewTime.getText().toString();
            String opis = editTextDescription.getText().toString();

            if (naziv.isEmpty() || vreme.isEmpty() || opis.isEmpty() || prioritet.isEmpty()) {
//                Timber.d(prioritet);
                showAlertDialog("Greška", "Morate uneti sve podatke.");
            } else {
//                Timber.d(prioritet);
                Task task = new Task(naziv, vreme, prioritet, opis);
                if (proveriPodudaranjeTermina(task)) {
                    showAlertDialog("Greška", "Obaveza se podudara sa već postojećom obavezom.");
                } else {
                    tasks.add(task);
                    Intent intent = new Intent();
                    intent.putExtra("newTask", task.toString());
                    setResult(RESULT_OK, intent);
//                    showAlertDialog("Uspeh", "Obaveza uspešno kreirana.");
                    finish();
                }
            }
        });

        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(v -> {
            finish();
        });

        clockImage.setOnClickListener(view -> {
            if (startCal != null && endCal != null) {
                updateIntervalTextView();
            } else {
                showTimePickerDialog(true);
            }
        });
    }

    public void onTextViewClick(View view, String text) {
        view.setAlpha(1.0f);

        switch (view.getId()) {
            case R.id.lowPriorityAddTask:
                midPriority.setAlpha(0.5f);
                highPriority.setAlpha(0.5f);
                break;
            case R.id.midPriorityAddTask:
                lowPriority.setAlpha(0.5f);
                highPriority.setAlpha(0.5f);
                break;
            case R.id.highPriorityAddTask:
                lowPriority.setAlpha(0.5f);
                midPriority.setAlpha(0.5f);
                break;
        }
        prioritet = text;
    }

    private boolean proveriPodudaranjeTermina(Task noviTask) {
        for (Task task : tasks) {
            if (task.getTime().equals(noviTask.getTime())) {
                return true;
            }
        }
        return false;
    }

    private void showTimePickerDialog(final boolean isStartTime) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute1) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute1);

                    if (isStartTime) {
                        startCal = cal;
                        showTimePickerDialog(false);
                    } else {
                        endCal = cal;
                        updateIntervalTextView();
                    }
                },
                hour, minute, false);
        timePickerDialog.show();
    }

    private void updateIntervalTextView() {
        if (startCal != null && endCal != null) {
            if (endCal.before(startCal)) {
                showErrorDialog("ERROR", "End of Task can't be before the start of the Task.");
                startCal = null;
                endCal = null;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String startTime = sdf.format(startCal.getTime());
                String endTime = sdf.format(endCal.getTime());
                String interval = startTime + " - " + endTime;
                textViewTime.setText(interval);
                startCal = null;
                endCal = null;
            }
        }
    }

    private void showErrorDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }


}