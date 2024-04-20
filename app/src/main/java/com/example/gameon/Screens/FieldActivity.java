package com.example.gameon.Screens;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.Fragments.OrderListFieldFragment;
import com.example.gameon.Models.Field;
import com.example.gameon.Models.Order;
import com.example.gameon.R;
import com.example.gameon.Utilities.FirebaseAdministrator;
import com.example.gameon.Utilities.FullHourTimePickerDialog;
import com.example.gameon.Utilities.MyVibrator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FieldActivity extends AppCompatActivity {


    //text views:
    private MaterialTextView field_LBL_field_name;
    private MaterialTextView field_LBL_field_type;
    private MaterialTextView field_LBL_address;
    private MaterialTextView field_LBL_start_date;
    private MaterialTextView field_LBL_start_time;
    private MaterialTextView field_LBL_end_time;

    //buttons:
    private MaterialButton field_BTN_start_time;
    private MaterialButton field_BTN_save_order;

    //frames:
    private FrameLayout field_FRAME_current_orders;

    FirebaseAdministrator firebaseAdministrator = new FirebaseAdministrator();
    private final DatabaseReference usersDatabaseReference = FirebaseAdministrator.getUsersDatabaseReference();
    private final DatabaseReference fieldsDatabaseReference = FirebaseAdministrator.getFieldsDatabaseReference();

    private OrderListFieldFragment orderListFieldFragment;
    private String userName;
    private String fieldId;
    private Field field;

    private MyVibrator vibrator = new MyVibrator(this);

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private String currentDateStrFromDatePicker = "";
    private String currentStartTimeStrFromTimePicker = "";
    private String currentEndTimeStrFromTimePicker = "";

    private String userOrderIdStr = "";
    private String fieldOrderIdStr = "";

    //order details:
    int selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute;

    //flags:
    boolean isDateSelected = false;
    boolean isTimeSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_field);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        setValuesFromPreviousScreen();
        setField();
        initializeFragments();

        field_BTN_start_time.setOnClickListener(view -> getStartTime());
        field_BTN_save_order.setOnClickListener(view -> addOrderProcess());
    }

    private void addOrderProcess() {

        if(!isDateSelected){ //date was not selected
            userNotSelectedDateEvent();
            return;
        }

        if(!isTimeSelected){ //date was not selected
            userNotSelectedTimeEvent();
            return;
        }

        if(!isOrderHoursValid()){
            orderHoursIsNotValidEvent();
            return;
        }

        Order newOrder = createOrder(); //create the order

        //get order id strings for user and field
        userOrderIdStr = getUserOrderIdStr(newOrder);
        fieldOrderIdStr = getFieldOrderIdStr(newOrder);

        Map<String, Object> orderMap = convertOrderToMap(newOrder);

        saveOrderInDatabaseIfNotExist(orderMap);

    }


    private void orderWasCreatedEvent() {
        Toast.makeText(this, "Your order saved!", Toast.LENGTH_LONG).show();
    }

    private void sameOrderAlreadyExistInUserOrdersEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "You have already order in the same time!", Toast.LENGTH_LONG).show();
    }

    private void sameOrderAlreadyExistInFieldOrdersEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "The field is already taken in that time!", Toast.LENGTH_LONG).show();
    }

    private void userNotSelectedTimeEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "You must select start time!", Toast.LENGTH_LONG).show();
    }

    private void userNotSelectedDateEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "You must select date!", Toast.LENGTH_LONG).show();
    }

    private Order createOrder() {
        return new Order()
                .setField(field)
                .setDateTimeOrder(new Date(selectedYear, selectedMonth - 1, selectedDay, selectedHour, selectedMinute))
                .setUserName(userName);
    }

    private void showTimePickerDialog() {
        initTimePicker();
        timePickerDialog.show();
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {

                //set selected time;
                selectedHour = hour;
                selectedMinute = minute;

                setCurrentStartAndEndTime(hour, minute);

                isTimeSelected = true;

            }
        };

        int timePickerStyle = AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialog = new FullHourTimePickerDialog(this,timePickerStyle, onTimeSetListener , selectedHour, selectedMinute);

    }

    private void setCurrentStartAndEndTime(int hour, int minute) {
        currentStartTimeStrFromTimePicker = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        currentEndTimeStrFromTimePicker =  String.format(Locale.getDefault(), "%02d:%02d", (hour + 1) % 24 , minute);

        // Update the start date text view
        field_LBL_start_time.setText("Start time: " + currentStartTimeStrFromTimePicker);
        field_LBL_start_time.setVisibility(View.VISIBLE); //set visible

        // Update the end date text view
        field_LBL_end_time.setText("End time: " + currentEndTimeStrFromTimePicker);
        field_LBL_end_time.setVisibility(View.VISIBLE); //set visible
    }

    private void getStartTime() {
        showDatePickerDialog();
    }

    private void showDatePickerDialog(){
        initDatePicker();
        datePickerDialog.show();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1; //because the counting of the months begin from 0
                currentDateStrFromDatePicker = dayOfMonth+ "/" + month + "/" + year;

                //set selected date;
                selectedYear = year;
                selectedMonth = month;
                selectedDay = dayOfMonth;

                // Update the start date text view
                field_LBL_start_date.setText("Date: " + currentDateStrFromDatePicker);
                field_LBL_start_date.setVisibility(View.VISIBLE); //set visible

                isDateSelected = true;

                // Show time picker dialog after selecting the date
                showTimePickerDialog();

            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int datePickerStyle = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, datePickerStyle, dateSetListener, year, month,day);
        //enable to choose only date in the future
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
    }

    private void setField() {
        DatabaseReference fieldReference = fieldsDatabaseReference.child(fieldId);
        fieldReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    field = snapshot.getValue(Field.class); //set the Field variable
                    Log.d("the chosen field:", field.toString());
                    initializeViews(); // Call initializeViews only after field data is retrieved
                }
                else {
                    Log.e("FieldActivity", "Field is null");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FieldActivity", "Failed to read field value.", error.toException());
            }
        });
    }

    private void initializeViews() {
        //initial field details:
        field_LBL_field_name.setText(field.getFieldName());
        field_LBL_field_type.setText(field.getFieldType()+ "");
        field_LBL_address.setText(field.getAddress() +"");

        //set start and end date time as invisible
        field_LBL_start_date.setVisibility(View.INVISIBLE);
        field_LBL_start_time.setVisibility(View.INVISIBLE);
        field_LBL_end_time.setVisibility(View.INVISIBLE);
    }

    private void setValuesFromPreviousScreen() {
        Intent previousScreen = getIntent();
        userName = previousScreen.getStringExtra(SignInActivity.USER_NAME);
        fieldId = previousScreen.getStringExtra(SelectFieldActivity.FIELD_ID);
    }

    private void findViews() {

        //texts views:
        field_LBL_field_name = findViewById(R.id.field_LBL_field_name);
        field_LBL_field_type = findViewById(R.id.field_LBL_field_type);
        field_LBL_address = findViewById(R.id.field_LBL_address);
        field_LBL_start_date = findViewById(R.id.field_LBL_start_date);
        field_LBL_start_time = findViewById(R.id.field_LBL_start_time);
        field_LBL_end_time = findViewById(R.id.field_LBL_end_time);

        //buttons:
        field_BTN_start_time = findViewById(R.id.field_BTN_start_time);
        field_BTN_save_order = findViewById(R.id.field_BTN_save_order);

        //frames:
        field_FRAME_current_orders = findViewById(R.id.field_FRAME_current_orders);
    }

    private String getUserOrderIdStr(Order newOrder) {
        return  newOrder.getUserName() + " " +
                newOrder.getDateTimeOrder().getYear() + " " +
                newOrder.getDateTimeOrder().getMonth() + " " +
                newOrder.getDateTimeOrder().getDate() + " " +
                newOrder.getDateTimeOrder().getHours() + " " +
                newOrder.getDateTimeOrder().getMinutes();
    }

    private String getFieldOrderIdStr(Order newOrder) {
        return  newOrder.getField().getFieldId() + " " +
                newOrder.getDateTimeOrder().getYear() + " " +
                newOrder.getDateTimeOrder().getMonth() + " " +
                newOrder.getDateTimeOrder().getDate() + " " +
                newOrder.getDateTimeOrder().getHours() + " " +
                newOrder.getDateTimeOrder().getMinutes();
    }

    private Map<String, Object> convertOrderToMap(Order newOrder) {
        Map<String, Object> orderMap = new HashMap<>();

        if(newOrder != null){
            orderMap.put("userName", newOrder.getUserName());
            orderMap.put("dateTimeOrder", newOrder.getDateTimeOrder());
            orderMap.put("field", newOrder.getField());
        }

        return orderMap;
    }

    private void saveOrderInDatabase(Map<String, Object> orderMap) {
        //save in field database:
        DatabaseReference fieldReference = fieldsDatabaseReference.child(fieldId);
        DatabaseReference newFieldOrderReference = fieldReference.child("Orders").child(fieldOrderIdStr);
        newFieldOrderReference.setValue(orderMap);

        //save in user database:
        DatabaseReference userReference = usersDatabaseReference.child(userName);
        DatabaseReference newUserOrderReference = userReference.child("Orders").child(userOrderIdStr);
        newUserOrderReference.setValue(orderMap);

        orderWasCreatedEvent();
        goBackToSelectFieldActivity();

    }

    public void saveOrderInDatabaseIfNotExist(Map<String, Object> orderMap){

        DatabaseReference userOrderReference = usersDatabaseReference.child(userName).child("Orders").child(userOrderIdStr);
        DatabaseReference fieldOrderReference = fieldsDatabaseReference.child(fieldId).child("Orders").child(fieldOrderIdStr);

        // for user:
        userOrderReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                if (userSnapshot.exists()) {
                    sameOrderAlreadyExistInUserOrdersEvent();
                    return;
                }

                fieldOrderReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot fieldSnapshot) {
                        if (fieldSnapshot.exists()) {
                            sameOrderAlreadyExistInFieldOrdersEvent();
                            return;
                        }

                        saveOrderInDatabase(orderMap);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FieldActivity", "Failed to read field order value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FieldActivity", "Failed to read user order value.", error.toException());
            }
        });
    }

    private void initializeFragments() {
        orderListFieldFragment = new OrderListFieldFragment();
        Log.d("field id", fieldId);
        orderListFieldFragment.setFieldId(fieldId);
        getSupportFragmentManager().beginTransaction().add(R.id.field_FRAME_current_orders, orderListFieldFragment).commit();
    }

    public void goBackToSelectFieldActivity(){
        // Set result to return to SelectFieldActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SignInActivity.USER_NAME, userName);
        setResult(RESULT_OK, resultIntent);

        // Finish the activity
        finish();
    }

    private boolean isOrderHoursValid() { //invalid order hours: 22:00 - 06:00
        if(selectedHour >= 22 || selectedHour <= 6)
            return false;
        return  true;
    }

    private void orderHoursIsNotValidEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "Your order can't be at that time 22:00 - 06:00!", Toast.LENGTH_LONG).show();
    }
}