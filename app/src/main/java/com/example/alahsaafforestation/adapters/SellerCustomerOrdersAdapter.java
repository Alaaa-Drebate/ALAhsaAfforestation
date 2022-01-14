package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.SellerOrder;

import java.util.ArrayList;

public class SellerCustomerOrdersAdapter extends RecyclerView.Adapter<SellerCustomerOrdersAdapter.ViewHolder> {

    ArrayList<SellerOrder> orders;
    Context context;

    public SellerCustomerOrdersAdapter(Context context, ArrayList<SellerOrder> orders) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.product_request_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SellerOrder order = orders.get(position);

        holder.productName.setText(order.getProductName());

        holder.customerName.setText(String.valueOf(order.getCustomerName()));

        holder.quantity.setText(String.valueOf(order.getQuantity()));

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrderFromCustomer(order.getId());
                removeOrder(position);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectOrderFromCustomer(order.getId());
                removeOrder(position);
            }
        });

    }

    private void rejectOrderFromCustomer(int orderId) {
    }

    private void acceptOrderFromCustomer(int orderId) {
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView productName;
        public TextView customerName;
        public TextView quantity;
        public ImageView accept;
        public ImageView reject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productName = itemView.findViewById(R.id.product_name);
            this.customerName = itemView.findViewById(R.id.customer_name);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.accept = itemView.findViewById(R.id.accept_btn);
            this.reject =itemView.findViewById(R.id.reject_btn);
        }
    }

    private void removeOrder(int index) {
        orders.remove(index);
        notifyItemRemoved(index);
    }


}
