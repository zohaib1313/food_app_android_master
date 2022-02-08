package com.bigbird.foodorderingapp.activities.admin;

public class ChangeUserStatusModel {

    String userName, contact, cnicFront, cnicBack, employCard;
    String userType;
    boolean status;

    public ChangeUserStatusModel() {
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ChangeUserStatusModel(String userName, String contact, String cnicFront, String cnicBack, String employCard, boolean status) {
        this.userName = userName;
        this.contact = contact;
        this.cnicFront = cnicFront;
        this.cnicBack = cnicBack;
        this.employCard = employCard;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChangeUserStatusModel{" +
                "userName='" + userName + '\'' +
                ", contact='" + contact + '\'' +
                ", cnicFront='" + cnicFront + '\'' +
                ", cnicBack='" + cnicBack + '\'' +
                ", employCard='" + employCard + '\'' +
                ", status=" + status +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCnicFront() {
        return cnicFront;
    }

    public void setCnicFront(String cnicFront) {
        this.cnicFront = cnicFront;
    }

    public String getCnicBack() {
        return cnicBack;
    }

    public void setCnicBack(String cnicBack) {
        this.cnicBack = cnicBack;
    }

    public String getEmployCard() {
        return employCard;
    }

    public void setEmployCard(String employCard) {
        this.employCard = employCard;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
