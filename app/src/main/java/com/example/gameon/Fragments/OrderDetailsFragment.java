package com.example.gameon.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gameon.Models.Order;
import com.example.gameon.R;
import com.google.android.material.textview.MaterialTextView;


public class OrderDetailsFragment extends Fragment {

    private MaterialTextView order_details_fragment_LBL_details;

    private MaterialTextView order_details_fragment_LBL_field_name_title;
    private MaterialTextView order_details_fragment_LBL_field_type_title;
    private MaterialTextView order_details_fragment_LBL_address_title;

    private MaterialTextView order_details_fragment_LBL_field_name;
    private MaterialTextView order_details_fragment_LBL_field_type;
    private MaterialTextView order_details_fragment_LBL_address;

    //Default constructor
    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);

        findViews(view);


        return view;
    }

    private void findViews(View view) {
        order_details_fragment_LBL_details = view.findViewById(R.id.order_details_fragment_LBL_details);

        order_details_fragment_LBL_field_name_title = view.findViewById(R.id.order_details_fragment_LBL_field_name_title);
        order_details_fragment_LBL_field_type_title = view.findViewById(R.id.order_details_fragment_LBL_field_type_title);
        order_details_fragment_LBL_address_title = view.findViewById(R.id.order_details_fragment_LBL_address_title);

        order_details_fragment_LBL_field_name = view.findViewById(R.id.order_details_fragment_LBL_field_name);
        order_details_fragment_LBL_field_type = view.findViewById(R.id.order_details_fragment_LBL_field_type);
        order_details_fragment_LBL_address = view.findViewById(R.id.order_details_fragment_LBL_address);
    }

   public void showOrderDetails(Order order){


        //set texts:
       order_details_fragment_LBL_field_name.setText(order.getField().getFieldName());
       order_details_fragment_LBL_address.setText(order.getField().getAddress().toString());
       order_details_fragment_LBL_field_type.setText(order.getField().getFieldType() + "");

       //show texts:
       order_details_fragment_LBL_details.setVisibility(View.VISIBLE);

       order_details_fragment_LBL_field_name_title.setVisibility(View.VISIBLE);
       order_details_fragment_LBL_field_type_title.setVisibility(View.VISIBLE);
       order_details_fragment_LBL_address_title.setVisibility(View.VISIBLE);

       order_details_fragment_LBL_field_name.setVisibility(View.VISIBLE);
       order_details_fragment_LBL_address.setVisibility(View.VISIBLE);
       order_details_fragment_LBL_field_type.setVisibility(View.VISIBLE);
   }
}