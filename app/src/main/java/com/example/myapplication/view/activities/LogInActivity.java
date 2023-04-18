package com.example.myapplication.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class LogInActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText email;
    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();
    }

    private void init() {
//        if(!checkIfLoggedIn()){
            initView();
            initListeners();
//        }
    }

    private void initView() {
        loginBtn = findViewById(R.id.login_button);
        email = findViewById(R.id.email_edit_text);
        username = findViewById(R.id.username_edit_text);
        password = findViewById(R.id.password_edit_text);
    }

    private boolean checkIfLoggedIn() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("is_logged_in", false)) {
            Timber.d("ULOGOVAN");
            finish();
            return true;
        }
        return false;
    }

    private void initListeners() {
        loginBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(email.getText())) {
                email.setError("Email is required.");
                return;
            }

            if (!isValidEmail(email.getText())) {
                email.setError("Invalid email format.");
                return;
            }

            if (TextUtils.isEmpty(username.getText())) {
                username.setError("Username is required.");
                return;
            }

            if (TextUtils.isEmpty(password.getText().toString())) {
                password.setError("Password is required.");
                return;
            }

            if (!isValidPassword(password.getText().toString())) {
                password.setError("Invalid password format.");
                return;
            }

            Map<String,String> map = readPasswordFromRawResource(getApplicationContext(), R.raw.password);
            if(!map.containsKey(email.getText().toString())){
                Snackbar.make(findViewById(android.R.id.content), "Incorrect email.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if(!map.containsValue(password.getText().toString())){
                Snackbar.make(findViewById(android.R.id.content), "Incorrect password.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.putBoolean("is_logged_in", true);
            editor.apply();

            Toast.makeText(this, "Message written to preferences", Toast.LENGTH_SHORT).show();

            Toast.makeText(LogInActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, BottomNavigationActivity.class);
//            startActivity(intent);
            finish();

        });
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$";
        return password.matches(regex);
    }

    public Map<String,String> readPasswordFromRawResource(Context context, int resourceId) {
        Map<String,String> map = new HashMap<>();

        String fileString = "";
        String[] credentials;
        try {
            File dir = new File(getApplicationContext().getFilesDir(), "mydir");
            if(!dir.exists())
                dir.mkdir();

            File gpxfile = new File(dir, "password.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(gpxfile));

            String line;
            boolean containsNonWhitespace = false;
            while ((line = bufferedReader.readLine()) != null) {
                Timber.d("-----------------------");
                Timber.d(line);
                Timber.d("-----------------------");
                if (line.matches("[a-zA-Z\\d].*")) {
                    containsNonWhitespace = true;
                    break;
                }
            }

            bufferedReader.close();

            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamreader = new InputStreamReader(inputStream);

            if(containsNonWhitespace){
                bufferedReader = new BufferedReader(new FileReader(gpxfile));
            }else{
                bufferedReader = new BufferedReader(inputStreamreader);
            }

            String line1;
            while((line1 = bufferedReader.readLine()) != null){
                credentials = line1.split("=");
                map.put(credentials[0], credentials[1]);
                fileString += line1 + "\n";
            }
            if(!containsNonWhitespace) {
                PrintWriter printWriter = new PrintWriter(new FileWriter(gpxfile));
                printWriter.print(fileString);
                printWriter.flush();
                printWriter.close();

            }

            bufferedReader.close();
            inputStreamreader.close();
            inputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return map;
    }
}