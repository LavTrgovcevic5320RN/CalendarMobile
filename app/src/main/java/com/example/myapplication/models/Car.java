package com.example.myapplication.models;

public class Car {

    private int id;
    private String picture;
    private String manufacturer;
    private String model;

    public Car(int id, String picture, String manufacturer, String model) {
        this.id = id;
        this.picture = picture;
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

}
