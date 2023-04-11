package com.example.myapplication.view.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.view.viewpager.PagerAdapter;
import com.example.myapplication.viewmodels.SplashViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return false;
        });
        setContentView(R.layout.activity_main);
        init();
    }

//    ActivityResultLauncher<Intent> sharedPreferencesActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//                String message = sharedPreferences.getString(PreferenceActivity.PREF_MESSAGE_KEY, "");
//                if (message == null)
//                    message = "Error";
//                Toast.makeText(this, "We have written to pref: " + message, Toast.LENGTH_LONG).show();
//            });

    private void init() {
        initLogIn();

        initViewPager();
        initNavigation();
    }

    private void initLogIn() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!preferences.getBoolean("is_logged_in", false)) {
//            Timber.d("ULOGOVAN");
            Intent intent = new Intent(this, LogInActivity.class);
            startActivity(intent);
        }
//        sharedPreferencesActivityResultLauncher.launch(intent);
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    @SuppressLint("NonConstantResourceId")
    private void initNavigation() {
        ((BottomNavigationView)findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.menu_calendar: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.menu_day_schedule: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.menu_profile: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
            }
            return true;
        });
    }




}