package com.example.alahsaafforestation.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.adapters.ProductsInCartAdapter;
import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.api.RequestQueueSingeleton;
import com.example.alahsaafforestation.model.Product;
import com.example.alahsaafforestation.offlinedata.CartItemsDB;
import com.example.alahsaafforestation.offlinedata.Myappdatabas;
import com.example.alahsaafforestation.utils.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyCartFragment extends Fragment implements ProductsInCartAdapter.myCartListener{

    public static final String TAG = "customerMyCart";


    RecyclerView mOrderItemsList;
    ArrayList<CartItemsDB> orders;
    ProductsInCartAdapter productsInCartAdapter;

    Myappdatabas myappdatabas;

    TextView mTotalPriceText;
    Button mConfirmOrderBtn;

    RequestQueue queue;


    public MyCartFragment() {
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
        return inflater.inflate(R.layout.fragment_my_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOrderItemsList = view.findViewById(R.id.products_in_cart_list);
        mTotalPriceText = view.findViewById(R.id.total_price_text_view);
        mConfirmOrderBtn = view.findViewById(R.id.confirm_order_btn);

        myappdatabas = Myappdatabas.getDatabase(getContext());
        orders = new ArrayList<>(myappdatabas.myDao().getItems());

        productsInCartAdapter = new ProductsInCartAdapter(orders, getContext(), this);
        mOrderItemsList.setAdapter(productsInCartAdapter);

        //calculate and set total price
        updateTotalPrice();

        // Get a RequestQueue
        queue = RequestQueueSingeleton.getInstance(getContext()).
                getRequestQueue();

        mConfirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productsInCartAdapter.getOrders().isEmpty()){
                    Toast.makeText(getContext(), "no orders to confirm", Toast.LENGTH_SHORT).show();
                }else
                    confirmOrder();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //just making assume that dialogs can get fragments into onStop() state
        updateTotalPrice();
    }

    void updateTotalPrice(){
        mTotalPriceText.setText(getContext().getResources().getString(R.string.total) + productsInCartAdapter.getTotalPrice());
    }


    @Override
    public void onChange() {
        updateTotalPrice();
    }

    private JSONArray ordersToSend(){

        JSONArray jsonArray = new JSONArray();
        ArrayList<CartItemsDB> orders = productsInCartAdapter.getOrders();

        for (CartItemsDB item : orders){
            JSONObject order = new JSONObject();
            try {

                order.put("product_id", String.valueOf(item.getId()));
                order.put("quantity", String.valueOf(item.getQuantity()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(order);
        }

        return jsonArray;
    }

    private void confirmOrder() {

        String URL = Constants.BASE_URL;

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        pDialog.hide();
                        Log.e("Result", response);
                        Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                        myappdatabas.myDao().deleteAll(productsInCartAdapter.getOrders());
                        productsInCartAdapter = new ProductsInCartAdapter(new ArrayList<CartItemsDB>(), getContext(), MyCartFragment.this);
                        mOrderItemsList.setAdapter(productsInCartAdapter);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
                pDialog.hide();

            }
        })

        {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("type", "save_order");
                params.put("expired_date", "2021-05-06");
                params.put("balance", mTotalPriceText.getText().toString());
                params.put("customer_id", String.valueOf(SharedPrefManager.getInstance(getContext()).getUserId()));
                params.put("status", "1");
                params.put("cart_items", ordersToSend().toString());


                return params;
            }


        };

        //Adding request to request queue

        queue.add(stringRequest);
    }
}