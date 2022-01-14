package com.example.alahsaafforestation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.Product;
import com.example.alahsaafforestation.offlinedata.CartItemsDB;
import com.example.alahsaafforestation.offlinedata.Myappdatabas;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProductDetailsFragment extends Fragment {

    public static final String TAG = "customerProductDetails";


    CircleImageView mImage;
    TextView mName;
    TextView mSellerName;
    TextView mDescription;
    TextView mPlantingDate;
    TextView mSellerName2;
    TextView mPrice;
    TextView mAvailable;


    private int productId;
    private String productName;
    private double productPrice;
    private String sellerName;
    private String imageUrl;
    private String availableQuantity;
    private String description;
    private String plantingDate;
    private String sellerId;


    Button mAddProductToCartBtn;
    Button mMessageSellerBtn;

    Myappdatabas myappdatabas;

    ChatWithSellerListener mChatWithSellerListener;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    public static ProductDetailsFragment newInstance(int itemId, String name, double price, String sellerName, String imageUrl, String availableQuantity, String description, String plantingDate, String sellerId) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("item_id", itemId);
        args.putString("item_name", name);
        args.putDouble("item_price", price);
        args.putString("seller_name", sellerName);
        args.putString("image_url", imageUrl);
        args.putString("avai_quantity", availableQuantity);
        args.putString("description", description);
        args.putString("planting_date", plantingDate);
        args.putString("sellerId", sellerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productId = getArguments().getInt("item_id");
            productName = getArguments().getString("item_name");
            productPrice = getArguments().getDouble("item_price");
            sellerName = getArguments().getString("seller_name");
            imageUrl = getArguments().getString("image_url");
            availableQuantity = getArguments().getString("avai_quantity");
            description = getArguments().getString("description");
            plantingDate = getArguments().getString("planting_date");
            sellerId = getArguments().getString("sellerId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mImage = view.findViewById(R.id.product_Image);
        mName = view.findViewById(R.id.product_name);
        mSellerName = view.findViewById(R.id.seller_name);
        mDescription = view.findViewById(R.id.description_text_view);
        mPlantingDate = view.findViewById(R.id.planting_date_text_view);
        mSellerName2 = view.findViewById(R.id.seller_name_text_view);
        mPrice = view.findViewById(R.id.price_text_view);
        mAvailable = view.findViewById(R.id.available_text_view);

        if(imageUrl != null)
            Glide.with(this).load(imageUrl).into(mImage);
        mName.setText(productName);
        mSellerName.setText(sellerName);
        mSellerName2.setText(sellerName);
        mDescription.setText(description);
        mPlantingDate.setText(plantingDate);
        mPrice.setText(String.valueOf(productPrice));
        mAvailable.setText(availableQuantity);

        mAddProductToCartBtn = view.findViewById(R.id.add_product_to_cart_btn);
        mMessageSellerBtn = view.findViewById(R.id.message_seller_btn);

        myappdatabas = Myappdatabas.getDatabase(getContext());


        mAddProductToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(getContext());
                final View view = factory.inflate(R.layout.add_product_dialog, null);
                final AlertDialog addProductDialog = new AlertDialog.Builder(getContext()).create();
                addProductDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                addProductDialog.setView(view);

                EditText quantityET = view.findViewById(R.id.quantity_edit_text);
                EditText cartNumberET = view.findViewById(R.id.cart_number_edit_text);
                TextView ok = view.findViewById(R.id.ok);
                TextView cancel = view.findViewById(R.id.cancel);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String quantityText = quantityET.getText().toString();
                        String cartText = cartNumberET.getText().toString();
                        //checking if password is empty
                        if (TextUtils.isEmpty(quantityText)) {
                            Toast.makeText(getContext(), "please enter quantity!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //checking if username is empty
                        if (TextUtils.isEmpty(cartText)) {
                            Toast.makeText(getContext(), "please enter cart number!", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        int quantity = Integer.parseInt(quantityText);
                        int cartNumber = Integer.parseInt(cartText);

                        CartItemsDB item = new CartItemsDB();
                        item.setId(productId);
                        item.setPrice(productPrice);
                        item.setQuantity(quantity);
                        item.setName(productName);
                        myappdatabas.myDao().addItem(item);

//                        addOrderToCart(quantity, cartNumber);
                        //when done dismiss;
                        addProductDialog.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addProductDialog.dismiss();
                    }
                });
                addProductDialog.show();

            }
        });

        mMessageSellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatInAction(sellerId, sellerName);
            }
        });




    }




    private void goToChatInAction(String otherUserId, String sellerName){
        mChatWithSellerListener.chatWithSeller(otherUserId, sellerName);
    }





    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ChatWithSellerListener) {
            mChatWithSellerListener = (ChatWithSellerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement chatWithSellerListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mChatWithSellerListener = null;
    }


    public interface ChatWithSellerListener {
        void chatWithSeller(String sellerId, String sellerName);
    }


}