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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.adapters.PharmaciesAdapter;
import com.example.alahsaafforestation.adapters.ProductsAdapter;
import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.api.RequestQueueSingeleton;
import com.example.alahsaafforestation.model.Pharmacy;
import com.example.alahsaafforestation.model.Product;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductsFragment extends Fragment {

    public static final String TAG = "customerProducts";


    TextInputEditText mSearchET;
    ImageView mUploadBtn;

    RecyclerView mProductsListView;
    List<Product> products;
    ProductsAdapter productsAdapter;

    RequestQueue queue;


    public ProductsFragment() {
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
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchET = view.findViewById(R.id.search_edit_text);
        mUploadBtn = view.findViewById(R.id.upload_btn);
        mProductsListView = view.findViewById(R.id.products_list);


        // Get a RequestQueue instance
        queue = RequestQueueSingeleton.getInstance(this.getContext()).
                getRequestQueue();
        getAllProducts();

    }


    //todo implement those api calls
    public List<Product> getProducts() {
        return products;
    }
    public List<Product> getProductsByName(String name) {
        return products;
    }
    public List<Product> getProductsByLocation(String location) {
        return products;
    }
    public List<Product> getProductsByImage(String imageUrl) {
        return products;
    }


    public void getAllProducts(){

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        String url = Constants.PRODUCTS_ALL_URL;
        //if everything is fine
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                pDialog.dismiss();
                try {
                    //converting response to json object
                    JSONObject obj = response;

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        //getting the user from the response
                        JSONArray productsArray = obj.getJSONArray("data");
                        products = new ArrayList<>();
                        Product product;
                        for (int i = 0; i < productsArray.length(); i++){
                            JSONObject productJson = productsArray.getJSONObject(i);
                            product = new Product(
                                    productJson.getInt("id"),
                                    productJson.getInt("seller_id"),
                                    productJson.getString("seller_name"),
                                    productJson.getString("image"),
                                    productJson.getString("name"),
                                    productJson.getString("description"),
                                    productJson.getString("planting_date"),
                                    productJson.getString("planting_address"),
                                    Double.parseDouble(productJson.getString("price").trim()),
                                    productJson.getInt("quantity")
                            );
                            products.add(product);
                        }

                        productsAdapter = new ProductsAdapter(getContext(), products);
                        mProductsListView.setAdapter(productsAdapter);

                    } else {
                        Toast.makeText(getContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
        queue.start();
    }

}