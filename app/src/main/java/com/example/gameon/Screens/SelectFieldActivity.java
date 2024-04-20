package com.example.gameon.Screens;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gameon.Databases.FieldsDatabase;
import com.example.gameon.Models.Field;
import com.example.gameon.R;
import com.example.gameon.Utilities.FirebaseAdministrator;
import com.example.gameon.Utilities.MyVibrator;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectFieldActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_FIELD_ACTIVITY = 1;
    public static String FIELD_ID = "ID";

    private GoogleMap myGoogleMap; //google map object variable
    private SearchView map_search_view;
    private final MyVibrator vibrator = new MyVibrator(this);
    private final FieldsDatabase fieldsDatabaseInstance = FieldsDatabase.getFieldsDatabase();
    private Intent previousScreen;
    private String userName;
    private String selectedFieldId;
    private FirebaseAdministrator firebaseAdministrator = new FirebaseAdministrator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select_field);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();

        //get user name from the previous activity
        previousScreen = getIntent();
        userName = previousScreen.getStringExtra(SignInActivity.USER_NAME);

        //get the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.goolge_map);
        mapFragment.getMapAsync(this);

        getLocationThatRequestedByUser();
        //writeFieldsDatabase();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_FIELD_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                // Retrieve the userName from the result data
                if (intent != null) {
                    userName = intent.getStringExtra(SignInActivity.USER_NAME);
                }
            }
        }
    }

    private void writeFieldsDatabase() {

        for (Field field: fieldsDatabaseInstance.getAllFields()) {
            Map<String, Object> fieldMap = convertFieldToMap(field);
            DatabaseReference newFieldReference = firebaseAdministrator.getFieldsDatabaseReference().child(field.getFieldId());
            newFieldReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) { // field already exists in the database

                    }
                    else{
                        newFieldReference.setValue(fieldMap);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private Map<String, Object> convertFieldToMap(Field field){
        Map<String, Object> fieldMap = new HashMap<>();

        if(field != null){
            fieldMap.put("fieldId",field.getFieldId());
            fieldMap.put("fieldName",field.getFieldName());
            fieldMap.put("fieldType",field.getFieldType());
            fieldMap.put("address",field.getAddress());
        }

        return  fieldMap;
    }

    private void getLocationThatRequestedByUser() {
        map_search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String userInput = map_search_view.getQuery().toString(); //Returns the string that currently in the text field of the search view.
                List<Address> addressList = null;

                //if the user enter a string
                if(userInput != null){
                    Geocoder geocoder = new Geocoder(SelectFieldActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(userInput, 1);
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                    // if the user enter a real location
                    if(addressList != null && !addressList.isEmpty()){
                        Address userRequestAddress = addressList.get(0); // the address that user request
                        LatLng latLng = new LatLng(userRequestAddress.getLatitude(), userRequestAddress.getLongitude());
                        myGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                    }
                    else {
                        invalidAddressEnteredByUserEvent();
                    }

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void invalidAddressEnteredByUserEvent() {
        vibrator.vibrate();
        Toast.makeText(this, "Invalid location Entered!", Toast.LENGTH_LONG).show();
    }


    private void findViews() {
        map_search_view = findViewById(R.id.map_search_view);
    }


    //necessary function for implements, it's make sure we are loading the map only when it is ready
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myGoogleMap = googleMap;
        zoomOnIsrael(myGoogleMap);
        addAllFieldsLocationToTheMap();

        // Set the OnMarkerClickListener to handle marker clicks
        myGoogleMap.setOnMarkerClickListener(this);
    }

    private void addAllFieldsLocationToTheMap() {

        ArrayList<Field> allFields = fieldsDatabaseInstance.getAllFields();

        for (Field field: allFields) {
            MarkerOptions markerToAdd = new MarkerOptions()
                    .position(new LatLng(field.getAddress().getLatitude(), field.getAddress().getLongitude())) // Set the position of the marker
                    .title(field.getFieldName()) // Set the title of the marker (optional)
                    .snippet(field.getFieldType().toString()) // Set the type of the field in the marker
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)); // Set the marker color to green

            Marker addedMarker = myGoogleMap.addMarker(markerToAdd);
            addedMarker.setTag(field.getFieldId());

        }


    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        selectedFieldId = (String)marker.getTag();
        showFieldInfoDialog(marker);

        return true;
    }

    private void showFieldInfoDialog(Marker marker) {
        Dialog fieldInfoDialog = new Dialog(SelectFieldActivity.this);
        
        fieldInfoDialog.setCancelable(true); // the user will be able to cancel the dialog by clicking anywhere outside the dialog
        fieldInfoDialog.setContentView(R.layout.field_info_dialog); //set my custom dialog layout
        
        //find dialog views:
        MaterialTextView field_info_dialog_LBL_field_name = fieldInfoDialog.findViewById(R.id.field_info_dialog_LBL_field_name);
        MaterialTextView field_info_dialog_LBL_field_type = fieldInfoDialog.findViewById(R.id.field_info_dialog_LBL_field_type);
        MaterialButton field_info_dialog_BTN_order = fieldInfoDialog.findViewById(R.id.field_info_dialog_BTN_order);

        //set field name text on the window:
        String fieldName = marker.getTitle();
        field_info_dialog_LBL_field_name.setText(fieldName);

        //set field name text on the window:
        String fieldType = marker.getSnippet();
        field_info_dialog_LBL_field_type.setText(fieldType);

        field_info_dialog_BTN_order.setOnClickListener(v -> moveFieldActivity());
        fieldInfoDialog.show();

    }

    private void moveFieldActivity() {
        Intent fieldActivityIntent = new Intent(this, FieldActivity.class);
        fieldActivityIntent.putExtra(SignInActivity.USER_NAME, userName);
        fieldActivityIntent.putExtra(FIELD_ID, selectedFieldId);

        startActivityForResult(fieldActivityIntent, REQUEST_FIELD_ACTIVITY);
    }

    private void zoomOnIsrael(GoogleMap myGoogleMap) {
        LatLng israel = new LatLng(31.51037554347202, 34.9339241032252);
        myGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(israel, 8));
    }
}