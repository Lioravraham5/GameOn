package com.example.gameon.Models;

import java.util.Comparator;

public class CompareOrdersByDateTimeFromNewestToOldest implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        // Compare o2 to o1 to sort from newest to oldest
        return o2.getDateTimeOrder().compareTo(o1.getDateTimeOrder()) ;
    }
}
