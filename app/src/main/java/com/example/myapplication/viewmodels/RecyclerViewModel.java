package com.example.myapplication.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RecyclerViewModel extends ViewModel {

//    public static int counter = 101;
//
//    private final MutableLiveData<List<Car>> cars = new MutableLiveData<>();
//    private ArrayList<Car> carList = new ArrayList<>();
//
//    public RecyclerViewModel() {
//        for (int i = 0; i <= 100; i++) {
//            Car car = new Car(i, "https://electric-fun.com/wp-content/uploads/2020/01/sony-car-796x418-1.jpg", "Manufacturer" + i, "Model" + i);
//            carList.add(car);
//        }
//        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
//        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
//        ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//        cars.setValue(listToSubmit);
//    }
//
//    public LiveData<List<Car>> getCars() {
//        return cars;
//    }
//
//    public void filterCars(String filter) {
//        List<Car> filteredList = carList.stream().filter(car -> car.getManufacturer().toLowerCase().startsWith(filter.toLowerCase())).collect(Collectors.toList());
//        cars.setValue(filteredList);
//    }
//
//    public int addCar(String pictureUrl, String manufacturer, String model) {
//        int id = counter++;
//        Car car = new Car(id, pictureUrl, manufacturer, model);
//        carList.add(car);
//        ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//        cars.setValue(listToSubmit);
//        return id;
//    }
//
//    public void removeCar(int id) {
//        Optional<Car> carObject = carList.stream().filter(car -> car.getId() == id).findFirst();
//        if (carObject.isPresent()) {
//            carList.remove(carObject.get());
//            ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//            cars.setValue(listToSubmit);
//        }
//    }

}
