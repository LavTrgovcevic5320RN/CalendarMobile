package com.example.myapplication.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.view.fragments.CalendarFragment;
import com.example.myapplication.view.fragments.DayScheduleFragment;
import com.example.myapplication.view.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CalendarActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    private CalendarFragment mCalendarFragment;
    private DayScheduleFragment mDayScheduleFragment;
    private ProfileFragment mProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        mCalendarFragment = new CalendarFragment();
        mDayScheduleFragment = new DayScheduleFragment();
        mProfileFragment = new ProfileFragment();

        setFragment(mCalendarFragment);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_calendar:
                        setFragment(mCalendarFragment);
                        return true;
                    case R.id.menu_day_schedule:
                        setFragment(mDayScheduleFragment);
                        return true;
                    case R.id.menu_profile:
                        setFragment(mProfileFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
    }
}
