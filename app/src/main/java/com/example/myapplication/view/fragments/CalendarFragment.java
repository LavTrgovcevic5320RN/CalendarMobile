package com.example.myapplication.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Day;
import com.example.myapplication.view.recycler.adapter.CalendarAdapter;
import com.example.myapplication.view.recycler.differ.DayDiffItemCallBack;
import com.example.myapplication.viewmodels.RecyclerViewModel;

import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class CalendarFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewModel recyclerViewModel;
    private CalendarAdapter calendarAdapter;
    private TextView month;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        recyclerViewModel = new ViewModelProvider(this).get(RecyclerViewModel.class);
        init();

        return view;
    }

    private void init(){
        initView();
        initRecycler();
        initObservers();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
//        month = view.findViewById(R.id.monthTitleTextView);
    }

    private void initObservers() {
        recyclerViewModel.getDays().observe(getViewLifecycleOwner(), days -> {
            calendarAdapter.submitList(days);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//              Promena imena meseca kada skrolujemo na dole
                if(dy > 0){
                    Day day = recyclerViewModel.getDayList().get(((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastVisibleItemPosition());
//                    Timber.d("------------------------------------------------");
//                    Timber.d("DAN DOLE %s", String.valueOf(day.getLocalDate().getDayOfMonth()));
//                    Timber.d("------------------------------------------------");
                    if(day.getLocalDate().getDayOfMonth() >= 25){
                        getActivity().setTitle(day.getLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + day.getLocalDate().getYear() + ".");
                    }
                }
//              Promena imena meseca kada skrolujemo na gore
                if(dy < 0){
                    Day day = recyclerViewModel.getDayList().get(((GridLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastVisibleItemPosition());
//                    Timber.d("------------------------------------------------");
//                    Timber.d("DAN GORE %s", String.valueOf(day.getLocalDate().getDayOfMonth()));
//                    Timber.d("------------------------------------------------");
                    if(day.getLocalDate().getDayOfMonth() <= 15){
                        getActivity().setTitle(day.getLocalDate().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + day.getLocalDate().getYear() + ".");
                    }
                }

//              Dodavanje nedelju dana kada skrolujemo na dole
                if(recyclerView.findFocus() != null){
                    if(!recyclerView.findFocus().canScrollVertically(1)){
                        recyclerViewModel.addMonth();
                    }
                }

//               Dodavanje nedelju dana kada skrolujemo na gore
                if(recyclerView.findFocus() != null){
                    if(!recyclerView.findFocus().canScrollVertically(-1)){
                        recyclerViewModel.addMonthToBeginning();
                    }
                }
            }
        });
    }

    private void initRecycler() {
        calendarAdapter = new CalendarAdapter(new DayDiffItemCallBack(), date -> {});
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerView.setAdapter(calendarAdapter);
    }
}
