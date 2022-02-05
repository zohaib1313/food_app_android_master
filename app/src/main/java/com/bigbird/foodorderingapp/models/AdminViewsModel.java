package com.bigbird.foodorderingapp.models;

public class AdminViewsModel {

    String title;
    String value;
    Actions actions;
    int textColor;
    int boxColor;

    public AdminViewsModel() {
    }

    public AdminViewsModel(String title, String value, Actions actions, int textColor, int boxColor) {
        this.title = title;
        this.value = value;
        this.actions = actions;
        this.textColor = textColor;
        this.boxColor = boxColor;
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

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBoxColor() {
        return boxColor;
    }

    public void setBoxColor(int boxColor) {
        this.boxColor = boxColor;
    }
}

