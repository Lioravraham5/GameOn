package com.example.gameon.Screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.Models.User;
import com.example.gameon.R;
import com.example.gameon.Screens.FieldActivity;
import com.example.gameon.Screens.LoginActivity;
import com.example.gameon.Screens.MyOrdersActivity;
import com.example.gameon.Screens.SelectFieldActivity;
import com.example.gameon.Screens.SignInActivity;
import com.example.gameon.Utilities.FirebaseAdministrator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends AppCompatActivity {

    MaterialTextView homepage_LBL_hello_username;

    private MaterialButton homepage_BTN_my_orders;
    private MaterialButton homepage_BTN_create_field_order;

    private FirebaseAdministrator firebaseAdministrator = new FirebaseAdministrator();

    private Intent previousScreen;
    private String userName;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();

        //get user name from the previous activity
        previousScreen = getIntent();
        userName = previousScreen.getStringExtra(SignInActivity.USER_NAME);
        initializationViews();

        homepage_BTN_create_field_order.setOnClickListener(view -> moveSelectFieldActivity());
        homepage_BTN_my_orders.setOnClickListener(view -> moveMyOrdersActivity());


    }

    private void moveMyOrdersActivity() {
        Intent myOrdersdActivityIntent = new Intent(this, MyOrdersActivity.class);
        myOrdersdActivityIntent.putExtra(SignInActivity.USER_NAME ,userName);

        startActivity(myOrdersdActivityIntent);
    }

    private void moveSelectFieldActivity() {
        Intent selectfieldActivityIntent = new Intent(this, SelectFieldActivity.class);
        selectfieldActivityIntent.putExtra(SignInActivity.USER_NAME ,userName);

        startActivity(selectfieldActivityIntent);
    }

    private void initializationViews() {
        initialUserDetails();
    }

    private void initialUserDetails() {
        DatabaseReference userReference = FirebaseAdministrator.getUsersDatabaseReference().child(userName);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                homepage_LBL_hello_username.setText(user.getFullName() + "!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void findViews() {

        //hello username label:
        homepage_LBL_hello_username = findViewById(R.id.homepage_LBL_hello_username);

        //buttons:
        homepage_BTN_my_orders = findViewById(R.id.homepage_BTN_my_orders);
        homepage_BTN_create_field_order = findViewById(R.id.homepage_BTN_create_field_order);
    }
}