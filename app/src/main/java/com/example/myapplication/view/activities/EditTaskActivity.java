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

public class EditTaskActivity extends AppCompatActivity {
    private TextView lowPriority;
    private TextView midPriority;
    private TextView highPriority;
    private EditText editTextTitle;
    private TextView textViewTime;
    private EditText editTextDescription;
    private ImageView clockImage;
    private Button btnSave;
    private Button btnCancel;
    private ArrayList<Task> tasks = new ArrayList<>();;
    private Task task;
    private int pozicijaTaskaUListi;
    private Calendar startCal, endCal;
    private String prioritet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        tasks = (ArrayList<Task>) args.getSerializable("ARRAYLIST");
        pozicijaTaskaUListi = (int) args.getSerializable("Pozicija taska u listi");
        task = tasks.get(pozicijaTaskaUListi);
        prioritet = task.getPriority();

        initView();
        initListeners();
    }

    private void initView(){
        lowPriority = findViewById(R.id.lowPriorityEditTask);
        midPriority = findViewById(R.id.midPriorityEditTask);
        highPriority = findViewById(R.id.highPriorityEditTask);
        editTextTitle = findViewById(R.id.editTitle);
        textViewTime = findViewById(R.id.textTime);
        editTextDescription = findViewById(R.id.editDescription);
        btnSave = findViewById(R.id.buttonSaveTask);
        btnCancel = findViewById(R.id.buttonOtkazi);
        clockImage = findViewById(R.id.clockImageView);
        editTextTitle.setText(task.getTitle());
        textViewTime.setText(task.getTime());
        editTextDescription.setText(task.getDescription());

        if(prioritet.equalsIgnoreCase("low")){
            lowPriority.setAlpha(1.0f);
            midPriority.setAlpha(0.5f);
            highPriority.setAlpha(0.5f);
        }
        else if(prioritet.equalsIgnoreCase("Mid")){
            lowPriority.setAlpha(0.5f);
            midPriority.setAlpha(1.0f);
            highPriority.setAlpha(0.5f);
        }
        else if(prioritet.equalsIgnoreCase("High")){
            lowPriority.setAlpha(0.5f);
            midPriority.setAlpha(0.5f);
            highPriority.setAlpha(1.0f);
        }
    }

    private void initListeners(){
        lowPriority.setOnClickListener(view -> onTextViewClick(view, lowPriority.getText().toString()));

        midPriority.setOnClickListener(view -> onTextViewClick(view, midPriority.getText().toString()));

        highPriority.setOnClickListener(view -> onTextViewClick(view, highPriority.getText().toString()));

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
        prioritet = text;
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