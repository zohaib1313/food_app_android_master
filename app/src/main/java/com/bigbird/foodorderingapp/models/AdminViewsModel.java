package com.bigbird.foodorderingapp.models;

public class AdminViewsModel {

    String title;
    String value;

    public AdminViewsModel(String title, String value) {
        this.title = title;
        this.value = value;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
