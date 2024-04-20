package com.example.gameon.Utilities;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseAdministrator {
    public static final String USERS_DATABASE_REFERENCE_STRING = "Users database";
    public static final String FIELDS_DATABASE = "Fields database";


    public static DatabaseReference getUsersDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference(USERS_DATABASE_REFERENCE_STRING);
    }

    public static DatabaseReference getFieldsDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference(FIELDS_DATABASE);
    }
}
