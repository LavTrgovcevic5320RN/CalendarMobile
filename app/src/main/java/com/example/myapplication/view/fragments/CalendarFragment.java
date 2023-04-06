package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
//import com.example.myapplication.view.recycler.adapter.CalendarAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    private TextView mMonthText;
    private RecyclerView mCalendarView;
    private GridLayoutManager mCalendarLayout;

//    private CalendarAdapter mAdapter;
    private List<CalendarDay> mDays = new ArrayList<>();

    private int mCurrentMonth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

//        mMonthText = view.findViewById(R.id.text_month);
//        mCalendarView = view.findViewById(R.id.recycler_calendar);
//        mCalendarLayout = new GridLayoutManager(getActivity(), 7);
//
//        // set up the calendar grid view
//        mAdapter = new CalendarAdapter(mDays, new CalendarAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(CalendarDay day) {
//                // open a dialog to show all events on the selected day
//                // TODO: implement this
//            }
//        });
//        mCalendarView.setLayoutManager(mCalendarLayout);
//        mCalendarView.setAdapter(mAdapter);
//
//        // populate the calendar grid view with the current month
//        Calendar calendar = Calendar.getInstance();
//        mCurrentMonth = calendar.get(Calendar.MONTH);
//        updateCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

        return view;
    }

    private void updateCalendar(int year, int month) {
//        mDays.clear();
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//
//        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
//        int lastDayOfWeek = (firstDayOfWeek + daysInMonth - 1) % 7;
//
//        // add empty cells for the days of the week before the first day of the month
//        for (int i = 0; i < firstDayOfWeek; i++) {
//            mDays.add(new CalendarDay("", false));
//        }
//
//        // add cells for the days of the month
//        for (int i = 1; i <= daysInMonth; i++) {
//            boolean isToday = false;
//            if (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
//                    calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
//                    i == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//                isToday = true;
//            }
//            mDays.add(new CalendarDay(String.valueOf(i), isToday));
//        }
//
//        // add empty cells for the days of the week after the last day of the month
//        for (int i = lastDayOfWeek; i < 6; i++) {
//            mDays.add(new CalendarDay("", false));
//        }
//
//        mAdapter.notifyDataSetChanged();
//
//        // update the month text at the top of the screen
//        mMonthText.setText(getMonthName(month));
    }

    private String getMonthName(int month) {
        switch (month) {
            case Calendar.JANUARY:
                return getString(R.string.month_january);
            case Calendar.FEBRUARY:
                return getString(R.string.month_february);
            case Calendar.MARCH:
                return getString(R.string.month_march);
            case Calendar.APRIL:
                return getString(R.string.month_april);
            case Calendar.MAY:
                return getString(R.string.month_may);
            case Calendar.JUNE:
                return getString(R.string.month_june);
            case Calendar.JULY:
                return getString(R.string.month_july);
            case Calendar.AUGUST:
                return getString(R.string.month_august);
            case Calendar.SEPTEMBER:
                return getString(R.string.month_september);
            case Calendar.OCTOBER:
                return getString(R.string.month_october);
            case Calendar.NOVEMBER:
                return getString(R.string.month_november);
            case Calendar.DECEMBER:
                return getString(R.string.month_december);
            default:
                return "";
        }
    }
}