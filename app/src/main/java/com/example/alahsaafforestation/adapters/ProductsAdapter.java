package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.Product;
import com.example.alahsaafforestation.offlinedata.Myappdatabas;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>{

    Context context;
    private List<Product> products;
    ProductsOnClickListener clickListener;

    // RecyclerView recyclerView;
    public ProductsAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.product_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = products.get(position);

        if(product.getImageUrl() == null){
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.ic_cart))
                    .into(holder.image);
        }else{
            Glide.with(context)
                    .load(product.getImageUrl())
                    .into(holder.image);
        }

        holder.name.setText(product.getName());


        holder.sellerName.setText(product.getSellerName());

        //call on click to notify main activity to switch to product details fragment
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.productsOnItemClick(product.getId(), product.getName(), product.getPrice(),   product.getSellerName(), product.getImageUrl(),String.valueOf(product.getAvailableQuantity()), product.getDescription(), product.getPlantingDate(), String.valueOf(product.getSellerId()));
            }
        });



    }

    @Override
    public int getItemCount() {
        return products.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView name;
        public TextView sellerName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.product_Image);
            this.name = itemView.findViewById(R.id.product_name);
            this.sellerName = itemView.findViewById(R.id.seller_name);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (context instanceof ProductsAdapter.ProductsOnClickListener) {
            clickListener = (ProductsAdapter.ProductsOnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSendTextListener");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        clickListener =null;
    }



    public interface ProductsOnClickListener{
        void productsOnItemClick(int product_id, String productName, double price, String sellerName, String image, String available, String description, String plantingDate, String sellerId);
    }
}

