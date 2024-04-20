package com.example.gameon.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.Models.User;
import com.example.gameon.R;
import com.example.gameon.Utilities.FirebaseAdministrator;
import com.example.gameon.Utilities.MyVibrator;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    public static final String USER_NAME = "user name";

    private EditText sign_in_page_user_name_edit_text;
    private EditText sign_in_page_full_name_edit_text;
    private EditText sign_in_page_password_edit_text;

    private User user;

    private FirebaseAdministrator firebaseAdministrator = new FirebaseAdministrator();

    private MaterialButton sign_in_page_BTN_register;

    private MyVibrator vibrator = new MyVibrator(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        sign_in_page_BTN_register.setOnClickListener(view -> registerUser());
    }

    private void saveUserInDatabase() {
        DatabaseReference newUserReference = FirebaseAdministrator.getUsersDatabaseReference().child(user.getUserName());
        newUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) { // Username already exists in the database
                    userAlreadyExistInDatabaseEvent();
                }
                else{
                    Map<String, Object> userMap = convertUserToMap();
                    newUserReference.setValue(userMap);
                    userAddedSuccessfullyToDatabaseEvent();
                    moveHomepage();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void registerUser() {
        if(isUserInputForRegisterIsValid()){ //if the user enter valid details
            createUser();
            saveUserInDatabase();
        }
    }

    private void userAddedSuccessfullyToDatabaseEvent() {
        Toast.makeText(this, "You have successfully registered", Toast.LENGTH_LONG).show();
    }


    private void userAlreadyExistInDatabaseEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "This user name already taken!", Toast.LENGTH_LONG).show();
    }


    private void createUser() {

        //get input from edits text:
        String userName = sign_in_page_user_name_edit_text.getText().toString();
        String fullName = sign_in_page_full_name_edit_text.getText().toString();
        String password = sign_in_page_password_edit_text.getText().toString();

        //create the user:
        user = new User();
        user.setUserName(userName).setFullName(fullName).setPassword(password);


    }

    private boolean isUserInputForRegisterIsValid() {

        if(TextUtils.isEmpty(sign_in_page_user_name_edit_text.getText())){
            userNameMissingEvent();
            return false;
        }

        if(TextUtils.isEmpty(sign_in_page_full_name_edit_text.getText())){
            fullNameMissingEvent();
            return false;

        }

        if(TextUtils.isEmpty(sign_in_page_password_edit_text.getText())){
            passwordMissingEvent();
            return false;
        }
        return true;
    }

    private void passwordMissingEvent() {
        sign_in_page_password_edit_text.setError("You must enter a password!");
    }

    private void fullNameMissingEvent() {
        sign_in_page_full_name_edit_text.setError("You must enter your full name!");
    }

    private void userNameMissingEvent() {
        sign_in_page_user_name_edit_text.setError("You must enter a user name!");
    }

    private void findViews() {

        //edits text:
        sign_in_page_user_name_edit_text = findViewById(R.id.sign_in_page_user_name_edit_text);
        sign_in_page_full_name_edit_text = findViewById(R.id.sign_in_page_full_name_edit_text);
        sign_in_page_password_edit_text = findViewById(R.id.sign_in_page_password_edit_text);

        //register button:
        sign_in_page_BTN_register = findViewById(R.id.sign_in_page_BTN_register);
    }

    private void moveHomepage() {
        Intent hompageIntent = new Intent(this, HomePageActivity.class);
        hompageIntent.putExtra(USER_NAME, user.getUserName());
        startActivity(hompageIntent);
        finish();
    }

    private Map<String, Object> convertUserToMap(){
        Map<String, Object> userMap = new HashMap<>();

        if(user != null){
            userMap.put("userName", user.getUserName());
            userMap.put("fullName", user.getFullName());
            userMap.put("password", user.getPassword());
        }

        return  userMap;
    }

}