package com.example.gameon.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gameon.Adapters.OrderInFieldAdapter;
import com.example.gameon.Models.Address;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class OrderListFieldFragment extends Fragment {

    private final DatabaseReference fieldsDatabaseReference = FirebaseAdministrator.getFieldsDatabaseReference();

    private RecyclerView order_list_RCW_Field_Fragment;
    private String fieldId;
    private ArrayList<Order> fieldOrders = new ArrayList<>();
    private CompareOrdersByDateTimeFromOldestToNewest compareOrdersByDateTimeFromOldestToNewest = new CompareOrdersByDateTimeFromOldestToNewest();

    // Default constructor
    public OrderListFieldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_list_field, container, false);
        findViews(view);

        // Check if fieldId is set
        if (fieldId != null && !fieldId.isEmpty()) {
            initializeViews(getContext());
        } else {
            Log.d("OrderListFieldFragment", "FieldId is null or empty");
        }

        return view;
    }

    public void setFieldId(String fieldId){
        this.fieldId = fieldId;
    }

    private void findViews(View view) {
        order_list_RCW_Field_Fragment = view.findViewById(R.id.order_list_RCW_Field_Fragment);
    }

    private void initializeViews(Context context) {
        readOrdersFromDatabase(context);
    }

    private void readOrdersFromDatabase(Context context){
        DatabaseReference fieldOrdersReference = fieldsDatabaseReference.child(fieldId).child("Orders");
        fieldOrdersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot orderSnapshot: snapshot.getChildren()){
                        Map<String, Object> orderMap = (Map<String, Object>) orderSnapshot.getValue();
                        Order order = convertMapToOrder(orderMap);

                        if(isOrderDateAfterToday(order)){ //check if the order that is after today
                            Log.d("Order is added", "added");
                            fieldOrders.add(order);
                        }
                        else{
                            Log.d("Order is not added", "not added");
                        }
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

    public Order convertMapToOrder(Map<String, Object> orderMap){
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

    private void sortArrayByDate() {
        if(fieldOrders != null){
            fieldOrders.sort(compareOrdersByDateTimeFromOldestToNewest);
        }
    }

    private void setAdapter(Context context) {
        OrderInFieldAdapter orderInFieldAdapter = new OrderInFieldAdapter(context, fieldOrders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        order_list_RCW_Field_Fragment.setLayoutManager(linearLayoutManager);
        order_list_RCW_Field_Fragment.setAdapter(orderInFieldAdapter);
    }
    private boolean isOrderDateAfterToday(Order order) {
        Date orderDate = order.getDateTimeOrder();
        Date today = Calendar.getInstance().getTime();
        int todayYear = today.getYear() + 1900; //The getYear() method returns the year represented by this Date object, minus 1900
        if(orderDate.getYear() > todayYear)
            return true;
        if(orderDate.getYear() == todayYear && orderDate.getMonth() > today.getMonth())
            return true;
        if(orderDate.getYear() == todayYear && orderDate.getMonth() == today.getMonth() && orderDate.getDate() > today.getDate())
            return true;
        if(orderDate.getYear() == todayYear && orderDate.getMonth() == today.getMonth() && orderDate.getDate() == today.getDate()) // to show orders in the sane day
            return true;
        return false;
    }
}