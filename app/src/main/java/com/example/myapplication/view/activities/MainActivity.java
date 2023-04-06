package com.example.myapplication.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.model.User;
import com.example.myapplication.viewmodels.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
//                Toast.makeText(MainActivity.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(android.R.id.content), "Incorrect password.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.apply();

//            String userInfo = email.getText().toString() + " " + password.getText().toString();
//            sharedPreferences
//                    .edit()
//                    .putString("userInfo", userInfo)
//                    .apply();
            Toast.makeText(this, "Message written to preferences", Toast.LENGTH_SHORT).show();

            // Successful login
            Toast.makeText(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BottomNavigationActivity.class);
            startActivity(intent);

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
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        Map<String,String> map = new HashMap<>();

        String[] credentials = null;
        try {
            String line = bufferedReader.readLine();
            while(line != null){
                credentials = line.split("=");
                map.put(credentials[0], credentials[1]);
//                Timber.d("email je: %s", credentials[0]);
//                Timber.d("sifra je: %s", credentials[1]);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            reader.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }


}