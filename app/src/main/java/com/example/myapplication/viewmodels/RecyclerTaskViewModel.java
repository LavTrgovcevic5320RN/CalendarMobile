package com.example.myapplication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

public class RecyclerTaskViewModel extends ViewModel {

    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
    private ArrayList<Task> taskList = new ArrayList<>();

    public RecyclerTaskViewModel() {
        for (int i = 0; i <= 2; i++) {
            Task task = new Task("Cas iz mate", new Date(2023,4,14,17,0),
                    "HIGH", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR9vMHQzf3GMYiI2WnYG9TUKnGAQFevruSgJF35VLAJe_odBMVd&usqp=CAU",
                    "Cas iz mate", "Danas imam cas iz mate u 17h");
            taskList.add(task);
        }

        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        tasks.setValue(listToSubmit);
    }

    public void addTask(String name, Date time, String priority, String picture, String title, String opis) {
        Task task = new Task(name, time, priority, picture, title, opis);
        Timber.d("napravio task");
        taskList.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        tasks.setValue(listToSubmit);
    }

/*    public void removeCar(int id) {
        Optional<Task> carObject = taskList.stream().filter(task -> task.getTime() == id).findFirst();
        if (carObject.isPresent()) {
            taskList.remove(carObject.get());
            ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
            tasks.setValue(listToSubmit);
        }
    }*/

    public MutableLiveData<List<Task>> getTasks() {
        return tasks;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
}
