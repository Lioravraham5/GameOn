package com.example.gameon.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.R;
import com.example.gameon.Utilities.FirebaseAdministrator;
import com.example.gameon.Utilities.MyVibrator;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    public static final String USER_NAME_FROM_LOGIN = "USER_NAME";

    private EditText login_page_user_name_edit_text;
    private EditText login_page_password_edit_text;

    private MaterialButton login_page_BTN_login;

    private FirebaseAdministrator firebaseAdministrator = new FirebaseAdministrator();

    private String userName;
    private String password;
    private MyVibrator vibrator = new MyVibrator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        findViews();
        login_page_BTN_login.setOnClickListener(view -> login());

    }

    private void findViews() {

        //edits text:
        login_page_user_name_edit_text = findViewById(R.id.login_page_user_name_edit_text);
        login_page_password_edit_text = findViewById(R.id.login_page_password_edit_text);

        //login button:
        login_page_BTN_login = findViewById(R.id.login_page_BTN_login);
    }

    private void login() {

        if(isUserInputForLoginIsValid()){
            setUserDetails();
            checkIfUserRegistered();
        }

    }

    private void setUserDetails() {
        userName = login_page_user_name_edit_text.getText().toString();
        password = login_page_password_edit_text.getText().toString();
    }

    private void checkIfUserRegistered() {
        DatabaseReference userReference = FirebaseAdministrator.getUsersDatabaseReference().child(userName);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) { //There is a user with the same user name in the database

                    String userPasswordInDataBase = snapshot.child("password").getValue(String.class);
                    Log.d("The user password in database: ", userPasswordInDataBase);
                    if(userPasswordInDataBase.equals(password)){ //check if the password is correct
                        //password is correct
                        moveHomepage();
                    }
                    else{ //password is not correct
                        passwordIsIncorrectEvent();
                    }
                }

                else { //There is no user with the same user name in the database
                    userIsIncorrectEvent();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void userIsIncorrectEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "User name is incorrect!", Toast.LENGTH_LONG).show();
    }

    private void moveHomepage() {
        Intent hompageIntent = new Intent(this, HomePageActivity.class);
        hompageIntent.putExtra(SignInActivity.USER_NAME, userName);
        startActivity(hompageIntent);
        finish();
    }

    private void passwordIsIncorrectEvent(){
        vibrator.vibrate();
        Toast.makeText(this, "Password is incorrect!", Toast.LENGTH_LONG).show();
    }

    private boolean isUserInputForLoginIsValid() {

        if(TextUtils.isEmpty(login_page_user_name_edit_text.getText())){
            userNameMissingEvent();
            return false;
        }

        if(TextUtils.isEmpty(login_page_password_edit_text.getText())){
            passwordMissingEvent();
            return false;
        }
        return true;
    }

    private void passwordMissingEvent() {
        login_page_password_edit_text.setError("You didn't enter your password!");
    }

    private void userNameMissingEvent() {
        login_page_user_name_edit_text.setError("You didn't enter your user name!");
    }


}