package com.example.myapplication.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.view.viewpager.PagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import timber.log.Timber;

public class BottomNavigationActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initViewPager();
        initNavigation();
    }

    private void initViewPager() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
    }

    @SuppressLint("NonConstantResourceId")
    private void initNavigation() {
//        Timber.d("Usao 8");
        ((BottomNavigationView)findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.menu_calendar: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_1, false); break;
                case R.id.menu_day_schedule: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_2, false); break;
                case R.id.menu_profile: viewPager.setCurrentItem(PagerAdapter.FRAGMENT_3, false); break;
            }
            Timber.d("Usao 9");
            return true;
        });
        Timber.d("Usao 10");
    }
}