package com.example.gameon.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gameon.Interfaces.CallBackOrderClick;
import com.example.gameon.Models.Order;
import com.example.gameon.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class OrderInUserAdapter extends RecyclerView.Adapter<OrderInUserAdapter.OrderInUserViewHolder> {

    private Context context;
    private ArrayList<Order> orders;
    private CallBackOrderClick callBackOrderClick;

    public OrderInUserAdapter(Context context, ArrayList<Order> orders, CallBackOrderClick callBackOrderClick){
        this.context = context;
        this.orders = orders;
        this.callBackOrderClick = callBackOrderClick;
    }

    @NonNull
    @Override
    public OrderInUserAdapter.OrderInUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_in_my_orders_activity_item, parent, false);
        return new OrderInUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderInUserAdapter.OrderInUserViewHolder holder, int position) {
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

    public class OrderInUserViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView order_item_in_my_orders_LBL_date;
        private MaterialTextView order_item_in_my_orders_LBL_time;
        private ImageButton order_item_in_my_orders_BTN_info;

        public OrderInUserViewHolder(@NonNull View itemView) {
            super(itemView);

            //connect between order object data to views in player_record_item.xml
            order_item_in_my_orders_LBL_date = itemView.findViewById(R.id.order_item_in_my_orders_LBL_date);
            order_item_in_my_orders_LBL_time = itemView.findViewById(R.id.order_item_in_my_orders_LBL_time);
            order_item_in_my_orders_BTN_info  = itemView.findViewById(R.id.order_item_in_my_orders_BTN_info);

            order_item_in_my_orders_BTN_info.setOnClickListener(view ->
            {
                if (callBackOrderClick != null) {
                    Order order = getItem(getAdapterPosition());
                    callBackOrderClick.getOrderInfo(order);
                }
            });
        }

        void  setOrderDetails(Order order){
            //create date string
            String dateOrderStr ="Date:\n" +
                    order.getDateTimeOrder().getDate() + "/" +
                    (order.getDateTimeOrder().getMonth() + 1) + "/" +
                    order.getDateTimeOrder().getYear(); //we add 1 to the month because the counting stats from 0

            order_item_in_my_orders_LBL_date.setText(dateOrderStr);

            //create time string
            String timeOrderStr ="Time:\n" +
                    order.getDateTimeOrder().getHours() + ":00" + " - " +
                    (order.getDateTimeOrder().getHours() + 1) + ":00";
            order_item_in_my_orders_LBL_time.setText(timeOrderStr);
        }
    }
}
