package com.example.gameon.Screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.R;
import com.google.android.material.button.MaterialButton;

public class WelcomePageActivity extends AppCompatActivity {

    private MaterialButton welcome_page_BTN_sign_in;
    private MaterialButton welcome_page_BTN_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();

        welcome_page_BTN_sign_in.setOnClickListener(view -> moveSignInPage());

        welcome_page_BTN_login.setOnClickListener(view -> moveLoginPage());
    }

    private void moveSignInPage() {
        Intent signInIntent = new Intent(this, SignInActivity.class);
        startActivity(signInIntent);
    }

    private void moveLoginPage() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    private void findViews() {

        //buttons:
        welcome_page_BTN_sign_in = findViewById(R.id.welcome_page_BTN_sign_in);
        welcome_page_BTN_login = findViewById(R.id.welcome_page_BTN_login);

    }
}