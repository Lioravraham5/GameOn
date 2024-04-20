package com.example.gameon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameon.Models.Order;
import com.example.gameon.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderInFieldAdapter extends RecyclerView.Adapter<OrderInFieldAdapter.OrderInFieldViewHolder> {

    private Context context;
    private ArrayList<Order> orders;

    public OrderInFieldAdapter(Context context, ArrayList<Order> orders){
        this.context = context;
        this.orders = orders;
    }


    @NonNull
    @Override
    public OrderInFieldAdapter.OrderInFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_in_field_activity_item, parent, false);
        return new OrderInFieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderInFieldAdapter.OrderInFieldViewHolder holder, int position) {
        Order order = getItem(position);
        holder.setOrderDetails(order);

    }

    @Override
    public int getItemCount() {
        if(orders == null)
            return 0;
        else {
            return orders.size();
        }
    }

    private Order getItem(int position){
        return orders.get(position);
    }

    public class OrderInFieldViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView order_item_in_field_LBL_date;
        private MaterialTextView order_item_in_field_LBL_time;
        private MaterialTextView order_item_in_field_LBL_create_by;


        public OrderInFieldViewHolder(@NonNull View itemView) {
            super(itemView);

            //connect between order object data to views inorder_in_field_activity_item.xml
            order_item_in_field_LBL_date = itemView.findViewById(R.id.order_item_in_field_LBL_date);
            order_item_in_field_LBL_time = itemView.findViewById(R.id.order_item_in_field_LBL_time);
            order_item_in_field_LBL_create_by  = itemView.findViewById(R.id.order_item_in_field_LBL_create_by);
        }

        void  setOrderDetails(Order order){
            //create date string
            StringBuffer dateOrderStr = new StringBuffer();
            if(order.getDateTimeOrder().getDate() < 10)
                dateOrderStr.append("0");
            dateOrderStr.append(order.getDateTimeOrder().getDate() + "/");

            if(order.getDateTimeOrder().getMonth() + 1 < 10)
                dateOrderStr.append("0");
            dateOrderStr.append((order.getDateTimeOrder().getMonth() + 1) + "/");  //we add 1 to the month because the counting stats from 0
            dateOrderStr.append(order.getDateTimeOrder().getYear());
            order_item_in_field_LBL_date.setText(dateOrderStr);

            //create time string
            StringBuffer timeOrderStr = new StringBuffer();
            if(order.getDateTimeOrder().getHours() < 10)
                timeOrderStr.append("0");
            timeOrderStr.append(order.getDateTimeOrder().getHours() + ":00 - ");
            
            if(order.getDateTimeOrder().getHours() + 1 < 10)
                timeOrderStr.append("0");
            timeOrderStr.append(order.getDateTimeOrder().getHours() + 1 + ":00");
            order_item_in_field_LBL_time.setText(timeOrderStr);

            //create created by string
            String createdByStr = order.getUserName();
            order_item_in_field_LBL_create_by.setText(createdByStr);
        }
    }
}
