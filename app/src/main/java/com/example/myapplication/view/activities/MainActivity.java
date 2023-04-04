package com.example.myapplication.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.viewmodels.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    // View components
    private Button loginBtn;
    private EditText email;
    private EditText username;
    private EditText password;

    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
//            Boolean value = splashViewModel.isLoading().getValue();
//            if (value == null) return false;
//            return value;
        });
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initView();
        initListeners();
    }


    private void initView() {
        loginBtn = findViewById(R.id.login_button);
        email = findViewById(R.id.email_edit_text);
        username = findViewById(R.id.username_edit_text);
        password = findViewById(R.id.password_edit_text);

    }

    private void initListeners() {
//        styledBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(this, StyledActivity.class);
//            startActivity(intent);
//        });
//
//        rotationBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(this, RotationActivity.class);
//            startActivity(intent);
//        });
//
//        classicRecyclerBtn.setOnClickListener(v -> {
//            Intent intent = new Intent(this, RecyclerActivity.class);
//            startActivity(intent);
//        });

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

            String credentials[] = readPasswordFromRawResource(getApplicationContext(), R.raw.password);
//            Timber.d("Username is %s", credentials[0]);
//            Timber.d("Password is %s", credentials[1]);
            if (!username.getText().toString().equals(credentials[0])) {
                Snackbar.make(findViewById(android.R.id.content), "Incorrect username.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if(!password.getText().toString().equals(credentials[1])){
//                Toast.makeText(MainActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content), "Incorrect password.", Snackbar.LENGTH_SHORT).show();
                return;
            }


            // Successful login
            Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, CalendarActivity.class);
//            startActivity(intent);

        });
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$";
        return password.matches(regex);
    }

    public String[] readPasswordFromRawResource(Context context, int resourceId) {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String[] credentials = null;
        try {
            String line = bufferedReader.readLine();
            credentials = line.split("=");
            bufferedReader.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return credentials;
    }


}