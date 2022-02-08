package com.bigbird.foodorderingapp.models;

public class ModelUserTypeUser {
    String name, contact, email, location, password, type;
    String img;
    boolean isApproved;

    public ModelUserTypeUser(String name, String contact, String email, String location, String password, String type, String img, boolean isApproved) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.location = location;
        this.password = password;
        this.type = type;
        this.img = img;
        this.isApproved = isApproved;
    }

    public ModelUserTypeUser() {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
