package com.example.alahsaafforestation.sellerfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.adapters.SellerCustomerOrdersAdapter;
import com.example.alahsaafforestation.adapters.SellerMyProductsAdapter;
import com.example.alahsaafforestation.model.Product;
import com.example.alahsaafforestation.model.SellerOrder;

import java.util.ArrayList;


public class SellerMainFragment extends Fragment {

    public static final String TAG = "sellerMain";

    RecyclerView mCustomersOrdersList;
    ArrayList<SellerOrder> currentOrders;
    SellerCustomerOrdersAdapter mCurrentOrdersAdapter;

    Button mAcceptAllOrders;
    Button mRejectAllOrders;


    public SellerMainFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCustomersOrdersList = view.findViewById(R.id.customers_orders_list);
        mAcceptAllOrders = view.findViewById(R.id.accept_all_orders_btn);
        mRejectAllOrders = view.findViewById(R.id.reject_all_orders_btn);

        currentOrders = new ArrayList<SellerOrder>()
        {{
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
            add(new SellerOrder(0, "tomato", "abdullah", 10.0, 50));
        }};

        mCurrentOrdersAdapter = new SellerCustomerOrdersAdapter(getContext(), currentOrders);
        mCustomersOrdersList.setAdapter(mCurrentOrdersAdapter);


        mAcceptAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptAllOrders();
            }
        });

        mRejectAllOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectAllOrders();
            }
        });

    }

    private void rejectAllOrders() {

    }

    private void acceptAllOrders() {
    }
}