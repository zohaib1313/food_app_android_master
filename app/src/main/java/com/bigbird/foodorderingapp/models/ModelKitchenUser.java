package com.bigbird.foodorderingapp.models;

public class ModelKitchenUser {
    String name,contact,email,restaurantName,restaurantLocation,cninFron,cninBack,password,type;

    public ModelKitchenUser(String name, String contact, String email, String restaurantName, String restaurantLocation, String cninFron, String cninBack, String password, String type) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.restaurantName = restaurantName;
        this.restaurantLocation = restaurantLocation;
        this.cninFron = cninFron;
        this.cninBack = cninBack;
        this.password = password;
        this.type = type;
    }

    public ModelKitchenUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getCninFron() {
        return cninFron;
    }

    public void setCninFron(String cninFron) {
        this.cninFron = cninFron;
    }

    public String getCninBack() {
        return cninBack;
    }

    public void setCninBack(String cninBack) {
        this.cninBack = cninBack;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ModelKitchenUser{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantLocation='" + restaurantLocation + '\'' +
                ", cninFron='" + cninFron + '\'' +
                ", cninBack='" + cninBack + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
