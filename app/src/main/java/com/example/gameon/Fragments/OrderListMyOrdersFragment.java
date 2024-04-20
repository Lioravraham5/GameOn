package com.example.gameon.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gameon.Adapters.OrderInUserAdapter;
import com.example.gameon.Interfaces.CallBackOrderClick;
import com.example.gameon.Models.Address;
import com.example.gameon.Models.CompareOrdersByDateTimeFromNewestToOldest;
import com.example.gameon.Models.CompareOrdersByDateTimeFromOldestToNewest;
import com.example.gameon.Models.Field;
import com.example.gameon.Models.Order;
import com.example.gameon.R;
import com.example.gameon.Utilities.FirebaseAdministrator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class OrderListMyOrdersFragment extends Fragment {

    private final DatabaseReference usersDatabaseReference = FirebaseAdministrator.getUsersDatabaseReference();

    private RecyclerView order_list_RCW_my_orders_Fragment;
    private CallBackOrderClick callBackOrderClick;
    private String userName;
    private ArrayList<Order> userOrders = new ArrayList<>();
    private CompareOrdersByDateTimeFromNewestToOldest compareOrdersByDateTimeFromNewestToOldest = new CompareOrdersByDateTimeFromNewestToOldest();

    // Default constructor
    public OrderListMyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list_my_orders, container, false);
        findViews(view);
        initializeViews(getContext());

        return view;
    }

    public String getUserName() {
        return userName;
    }

    public OrderListMyOrdersFragment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    private void initializeViews(Context context) {
        readOrdersFromDatabase(context);
    }

    private void findViews(View view) {
        order_list_RCW_my_orders_Fragment = view.findViewById(R.id.order_list_RCW_my_orders_Fragment);
    }

    public void setCallBackOrderClick(CallBackOrderClick callBackOrderClick) {
        if(callBackOrderClick != null)
            this.callBackOrderClick = callBackOrderClick;
    }

    private void readOrdersFromDatabase(Context context) {

        DatabaseReference fieldOrdersReference = usersDatabaseReference.child(userName).child("Orders");
        fieldOrdersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot orderSnapshot: snapshot.getChildren()){
                        Map<String, Object> orderMap = (Map<String, Object>) orderSnapshot.getValue();
                        Order order = convertMapToOrder(orderMap);
                        userOrders.add(order);
                    }

                    // Once data is loaded, sort and set the adapter
                    sortArrayByDate();
                    setAdapter(context);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private Order convertMapToOrder(Map<String, Object> orderMap) {
        Order order = new Order();

        //convert field from HashMap
        Map<String, Object> fieldMap = (Map<String, Object>) orderMap.get("field");
        Field field = convertMapToField(fieldMap);
        order.setField((field));

        // Convert dateTimeOrder from Map to Date
        Map<String, Object> dateMap = (Map<String, Object>) orderMap.get("dateTimeOrder");
        Date dateTimeOrder = convertMapToDate(dateMap);
        order.setDateTimeOrder(dateTimeOrder);

        order.setUserName((String) orderMap.get("userName"));
        return order;
    }

    private Date convertMapToDate(Map<String, Object> dateMap) {
        Date date = new Date();
        date.setDate(((Long) dateMap.get("date")).intValue());
        date.setHours(((Long) dateMap.get("hours")).intValue());
        date.setMinutes(((Long) dateMap.get("minutes")).intValue());
        date.setMonth(((Long) dateMap.get("month")).intValue());
        date.setSeconds(((Long) dateMap.get("seconds")).intValue());
        date.setYear(((Long) dateMap.get("year")).intValue());
        return date;
    }

    private Field convertMapToField(Map<String, Object> fieldMap) {
        Field newfield = new Field();
        newfield.setFieldId((String) fieldMap.get("fieldId"));
        newfield.setFieldName((String) fieldMap.get("fieldName"));

        // Convert FieldType from String to enum
        String fieldTypeString = (String) fieldMap.get("fieldType");
        Field.FieldType fieldType = Field.FieldType.valueOf(fieldTypeString);
        newfield.setFieldType((fieldType));

        // Convert Address from HashMap to Address object
        Map<String, Object> addressMap = (Map<String, Object>) fieldMap.get("address");
        Address address = convertMapToAddress(addressMap);
        newfield.setAddress(address);

        return newfield;
    }

    private Address convertMapToAddress(Map<String, Object> addressMap) {
        Address address = new Address();
        address.setCountry((String) addressMap.get("country"));
        address.setCity((String) addressMap.get("city"));
        address.setStreet((String) addressMap.get("street"));
        address.setLatitude((double) addressMap.get("latitude"));
        address.setLongitude((double) addressMap.get("longitude"));

        return address;
    }

    private void sortArrayByDate() {
        userOrders.sort(compareOrdersByDateTimeFromNewestToOldest);
    }

    private void setAdapter(Context context) {
        OrderInUserAdapter orderInUserAdapter = new OrderInUserAdapter(context, userOrders, callBackOrderClick);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        order_list_RCW_my_orders_Fragment.setLayoutManager(linearLayoutManager);
        order_list_RCW_my_orders_Fragment.setAdapter(orderInUserAdapter);
    }

}