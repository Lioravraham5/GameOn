package com.example.gameon.Models;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Field field;
    private Date dateTimeOrder;
    private String userName;

    public Order(){

    }

    public Field getField() {
        return field;
    }

    public Order setField(Field field) {
        this.field = field;
        return this;
    }

    public Date getDateTimeOrder() {
        return dateTimeOrder;
    }

    public Order setDateTimeOrder(Date dateTimeOrder) {
        this.dateTimeOrder = dateTimeOrder;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Order setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "field=" + field.getFieldName() +
                ", dateTimeOrder=" + dateTimeOrder +
                ", user=" + userName +
                '}';
    }
}
