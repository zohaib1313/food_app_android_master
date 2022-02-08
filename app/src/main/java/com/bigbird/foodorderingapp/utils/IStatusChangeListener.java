package com.bigbird.foodorderingapp.utils;

public interface IStatusChangeListener {
    void onItemClick(int position,boolean status,String userType,String userMail);
}
