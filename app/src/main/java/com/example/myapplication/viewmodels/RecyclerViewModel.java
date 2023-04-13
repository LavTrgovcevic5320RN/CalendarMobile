package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.model.Day;

public class RecyclerViewModel extends ViewModel {

    private final MutableLiveData<List<Day>> days = new MutableLiveData<>();
    private ArrayList<Day> dayList = new ArrayList<>();

    public RecyclerViewModel() {
        Day day = new Day(LocalDate.now());

        day = new Day(day.getLocalDate().minusDays(day.getLocalDate().getDayOfMonth()));

        int daysToAdd = day.getLocalDate().lengthOfMonth();

        while(!day.getLocalDate().getDayOfWeek().equals(DayOfWeek.MONDAY)){
            day = new Day(day.getLocalDate().minusDays(1));
            daysToAdd += 1;
        }

        for(int i = 0; i < daysToAdd; i++){
            dayList.add(new Day(day.getLocalDate().plusDays(i)));
        }

        ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
        days.setValue(listToSubmit);
    }

    public void addMonth() {
        int daysToAdd = dayList.get(dayList.size()-1).getLocalDate().lengthOfMonth();

        for(int i = 0; i < daysToAdd; i++){
            Day day = new Day(dayList.get(dayList.size()-1).getLocalDate().plusDays(1));
            dayList.add(day);
        }

        ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
        days.setValue(listToSubmit);
    }

    public void addMonthToBeginning() {
        int daysToAdd = 0;

        if(dayList.get(0).getLocalDate().minusDays(1).getMonth().equals(dayList.get(0).getLocalDate().getMonth())){
            daysToAdd = dayList.get(0).getLocalDate().getDayOfMonth()-1;
        }else{
            daysToAdd = dayList.get(0).getLocalDate().minusDays(1).lengthOfMonth();
        }

        for(int i = 0; i < daysToAdd; i++){
            Day day = new Day(dayList.get(0).getLocalDate().minusDays(1));
            dayList.add(0, day);
        }

        Day day = new Day(dayList.get(0).getLocalDate());

        while(!day.getLocalDate().getDayOfWeek().equals(DayOfWeek.MONDAY)){
            day = new Day(dayList.get(0).getLocalDate().minusDays(1));
            dayList.add(0 ,day);
        }

        ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
        days.setValue(listToSubmit);
    }

    public LiveData<List<Day>> getDays() {
        return days;
    }
}
