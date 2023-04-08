package com.example.myapplication.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.example.myapplication.view.viewpager.PagerAdapter;
import com.example.myapplication.viewmodels.SplashViewModel;

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

    private void init() {
        initLogIn();

        initViewPager();
        initNavigation();
    }

    private void initLogIn() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
//        finish();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    @SuppressLint("NonConstantResourceId")
    private void initNavigation() {
//        ((BottomNavigationView)findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
//                case R.id.menu_calendar: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
//                case R.id.menu_day_schedule: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
//                case R.id.menu_profile: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
//            }
//            return true;
//        });
    }




}