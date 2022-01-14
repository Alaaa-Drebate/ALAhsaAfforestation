package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.offlinedata.CartItemsDB;
import com.example.alahsaafforestation.offlinedata.Myappdatabas;

import java.util.ArrayList;

public class ProductsInCartAdapter extends RecyclerView.Adapter<ProductsInCartAdapter.ViewHolder> {


    ArrayList<CartItemsDB> orders;
    Context context;

    myCartListener itemsListener;

    Myappdatabas myappdatabas;

    public ProductsInCartAdapter(ArrayList<CartItemsDB> orders, Context context, myCartListener listener) {
        this.orders = orders;
        this.context = context;
        itemsListener = listener;
        myappdatabas = Myappdatabas.getDatabase(context);
    }

    public double getTotalPrice(){
        double totalPrice = 0.0;
        for (CartItemsDB o:orders){
            totalPrice += o.getPrice() * o.getQuantity();
        }
        return totalPrice;
    }

    public ArrayList<CartItemsDB> getOrders() {
        return orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.product_in_cart_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItemsDB order = orders.get(position);

        holder.name.setText(order.getName());

        holder.price.setText(String.valueOf(order.getPrice()));

        holder.quantity.setText(String.valueOf(order.getQuantity()));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.edit_quantity_dialog, null);
                final AlertDialog editProductDialog = new AlertDialog.Builder(context).create();
                editProductDialog.setView(view);

                EditText quantityET = view.findViewById(R.id.quantity_edit_text);
                TextView save = view.findViewById(R.id.save);
                TextView cancel = view.findViewById(R.id.cancel);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int quantity = Integer.parseInt(quantityET.getText().toString());
                        order.setQuantity(quantity);
                        //to see changes immediately
                        updateOrder(position, order);
                        //to update total price in my cart screen
                        itemsListener.onChange();
                        //when done dismiss;
                        editProductDialog.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editProductDialog.dismiss();
                    }
                });
                editProductDialog.show();

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.delete_confirmation_dialog, null);
                final AlertDialog deleteProductDialog = new AlertDialog.Builder(context).create();
                deleteProductDialog.setView(view);

                TextView yes = view.findViewById(R.id.yes_btn);
                TextView no = view.findViewById(R.id.no_btn);


                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //to see changes immediately
                        removeOrder(position);
                        //to update total price in my cart screen
                        itemsListener.onChange();
                        //when done dismiss;
                        deleteProductDialog.dismiss();

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteProductDialog.dismiss();
                    }
                });
                deleteProductDialog.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView name;
        public TextView price;
        public TextView quantity;
        public ImageView edit;
        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.price = itemView.findViewById(R.id.price);
            this.quantity = itemView.findViewById(R.id.quantity);
            this.edit = itemView.findViewById(R.id.edit_btn);
            this.delete =itemView.findViewById(R.id.delete_btn);
        }
    }

    private void removeOrder(int index) {
        myappdatabas.myDao().deleteItem(orders.get(index));
        orders.remove(index);
        notifyItemRemoved(index);
    }


    private void updateOrder(int index, CartItemsDB order) {
        myappdatabas.myDao().updateItem(order);
        orders.set(index, order);
        notifyItemChanged(index);
    }



    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        itemsListener =null;
    }

    public interface myCartListener{
        void onChange();
    }

}
