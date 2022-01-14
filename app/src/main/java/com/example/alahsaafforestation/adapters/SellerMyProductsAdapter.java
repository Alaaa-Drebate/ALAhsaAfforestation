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
import com.example.alahsaafforestation.model.Order;
import com.example.alahsaafforestation.model.Product;

import java.util.ArrayList;

public class SellerMyProductsAdapter extends RecyclerView.Adapter<SellerMyProductsAdapter.ViewHolder>{

    ArrayList<Product> myProducts;
    Context context;

    public SellerMyProductsAdapter(ArrayList<Product> myProducts, Context context) {
        this.myProducts = myProducts;
        this.context = context;
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

        Product product = myProducts.get(position);

        holder.name.setText(product.getName());

        holder.price.setText(String.valueOf(product.getPrice()));

        holder.quantity.setText(String.valueOf(product.getAvailableQuantity()));

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
                        product.setAvailableQuantity(quantity);
                        //to the database
                        sendNewQuantityToDatabase(product.getId(), quantity);
                        //to see changes immediately
                        updateProduct(position, product);
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
                //todo open confirmation dialog
                // then delete the order item from cart
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.delete_confirmation_dialog, null);
                final AlertDialog deleteProductDialog = new AlertDialog.Builder(context).create();
                deleteProductDialog.setView(view);

                TextView yes = view.findViewById(R.id.yes_btn);
                TextView no = view.findViewById(R.id.no_btn);


                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //to the database
                        deleteProductFromCart(product.getId());
                        //to see changes immediately
                        removeProduct(position);
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

    //todo remember to return status
    private void deleteProductFromCart(int id) {
    }

    //todo update this method to return a status
    private void sendNewQuantityToDatabase(int id, int quantity) {

    }

    @Override
    public int getItemCount() {
        return myProducts.size();
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

    private void removeProduct(int index) {
        myProducts.remove(index);
        notifyItemRemoved(index);
    }


    private void updateProduct(int index, Product product) {
        myProducts.set(index, product);
        notifyItemChanged(index);
    }



}
