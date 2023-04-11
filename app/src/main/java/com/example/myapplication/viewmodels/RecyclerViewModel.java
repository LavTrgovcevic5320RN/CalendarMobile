package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.example.myapplication.model.Day;

public class RecyclerViewModel extends ViewModel {

    public static int counter = 101;

    private final MutableLiveData<List<Day>> days = new MutableLiveData<>();
    private ArrayList<Day> dayList = new ArrayList<>();

    public RecyclerViewModel() {
        Calendar calendar = Calendar.getInstance();
        int j = 1;
        for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            Day day = new Day(j, i, calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));
            day.setPicture("https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg");
            dayList.add(day);
            j++;
        }
        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
        ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
        days.setValue(listToSubmit);
    }

    public LiveData<List<Day>> getDays() {
        return days;
    }

    public void filterDay(String filter) {
//        List<Day> filteredList = dayList.stream().filter(car -> car.getYear());
//        days.setValue(filteredList);
    }

    public int addDay(Integer dan, Integer mesec, Integer godina) {
        int id = counter++;
        Day day = new Day(id, dan, mesec, godina);
        dayList.add(day);
        ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
        days.setValue(listToSubmit);
        return id;
    }

    public void removeDay(int id) {
        Optional<Day> carObject = dayList.stream().filter(car -> car.getId() == id).findFirst();
        if (carObject.isPresent()) {
            dayList.remove(carObject.get());
            ArrayList<Day> listToSubmit = new ArrayList<>(dayList);
            days.setValue(listToSubmit);
        }
    }

}
