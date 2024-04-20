package com.example.gameon.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.Adapters.OrderInUserAdapter;
import com.example.gameon.Fragments.OrderDetailsFragment;
import com.example.gameon.Fragments.OrderListFieldFragment;
import com.example.gameon.Fragments.OrderListMyOrdersFragment;
import com.example.gameon.Interfaces.CallBackOrderClick;
import com.example.gameon.Models.Order;
import com.example.gameon.R;

public class MyOrdersActivity extends AppCompatActivity {

    private OrderListMyOrdersFragment orderListMyOrdersFragment;
    private  OrderDetailsFragment orderDetailsFragment;

    private Intent previousScreen;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get user name from the previous activity
        previousScreen = getIntent();
        userName = previousScreen.getStringExtra(SignInActivity.USER_NAME);

        initializeFragments();

    }

    private void initializeFragments() {

        //initialize OrderListMyOrdersFragment:
        orderListMyOrdersFragment = new OrderListMyOrdersFragment();
        orderListMyOrdersFragment.setUserName(userName);
        //initialize OrderDetailsFragment:
        orderDetailsFragment = new OrderDetailsFragment();

        //set callback:
        orderListMyOrdersFragment.setCallBackOrderClick(new CallBackOrderClick() {
            @Override
            public void getOrderInfo(Order order) {
                orderDetailsFragment.showOrderDetails(order);
            }
        });

        //begin transaction:
        getSupportFragmentManager().beginTransaction().add(R.id.my_orders_FRAME_orders, orderListMyOrdersFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.my_orders_FRAME_action, orderDetailsFragment).commit();
    }
}