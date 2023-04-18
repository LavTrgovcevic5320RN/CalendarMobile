package com.example.myapplication.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class RecyclerTaskViewModel extends ViewModel {

    private HashMap<String, MutableLiveData<List<Task>>> map = new HashMap<>();
    private String key = "";
    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();
    private ArrayList<Task> taskList = new ArrayList<>();

    public RecyclerTaskViewModel() {
        Timber.d("INICIJALIZACIJA");
        Timber.d("Kljuc u inicijaliziji je: %s", key);
        Task task = new Task("Cas iz mate u 17h", "17:30",
                "HIGH", "Danas imam cas iz mate u 17h");
        taskList.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        tasks.setValue(listToSubmit);
        map.put(key, tasks);
    }

    public void prepare(String key) {
        this.key = key;
        if (map.containsKey(key)) {
                Timber.d("Postoji mapa");

                taskList = (ArrayList<Task>) map.get(key).getValue();
                tasks.setValue(taskList);
        }else{
            Timber.d("USAO U ELSE");
            Timber.d("Kljuc u else-u je: %s", key);
            for (int i = 0; i < 2; i++) {
                Task task = new Task("Cas iz mate u 20h", "17:30",
                    "HIGH", "Danas imam cas iz mate u 20h");
                taskList.add(task);
            }
            ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
            tasks.setValue(listToSubmit);
            map.put(key, tasks);
        }
    }

    public void addTask(Task task) {
//        Timber.d("napravio task");
        Timber.d("Kljuc u kreiranju je %s", key);
        taskList.add(task);
        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        tasks.setValue(listToSubmit);
        map.put(key, tasks);
    }

    public void addTaskWithPosition(Task task, int position){
        taskList.remove(position);

        taskList.add(position, task);
        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        tasks.setValue(listToSubmit);
        map.put(key, tasks);
    }

    public void deleteTask(int position) {
//        Timber.d("Kljuc u brisanju je %s", key);
        taskList.remove(position);
        ArrayList<Task> listToSubmit = new ArrayList<>(taskList);
        tasks.setValue(listToSubmit);
        map.put(key, tasks);
    }

    public HashMap<String, MutableLiveData<List<Task>>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, MutableLiveData<List<Task>>> map) {
        this.map = map;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
