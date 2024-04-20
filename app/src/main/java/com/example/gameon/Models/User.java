package com.example.gameon.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {
    public static final int MAX_RECENTLY_ORDERS_COUNTER = 10;
    private String userName;
    private String fullName;
    private String password;
//    private ArrayList<Order> orders = new ArrayList<>();
    private CompareOrdersByDateTimeFromOldestToNewest compareOrdersByDateTime = new CompareOrdersByDateTimeFromOldestToNewest();
            

    public User(){

    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
