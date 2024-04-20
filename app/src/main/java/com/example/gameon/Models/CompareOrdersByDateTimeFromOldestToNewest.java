package com.example.gameon.Models;

import java.util.Comparator;

public class CompareOrdersByDateTimeFromOldestToNewest implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getDateTimeOrder().compareTo(o2.getDateTimeOrder()) ;
    }
}
