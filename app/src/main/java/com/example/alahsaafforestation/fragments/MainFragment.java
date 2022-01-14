package com.example.alahsaafforestation.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.adapters.PharmaciesAdapter;
import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.api.RequestQueueSingeleton;
import com.example.alahsaafforestation.model.Pharmacy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {


    public static final String TAG = "customerMain";

    EditText mSearchEditText;

    List<Pharmacy> pharmacies;
    PharmaciesAdapter listAdapter;
    RecyclerView mPharmaciesList;

    RequestQueue queue;

    public MainFragment() {
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSearchEditText = view.findViewById(R.id.search_edit_text);
        mPharmaciesList = view.findViewById(R.id.pharmacies_list);


        mPharmaciesList.setHasFixedSize(true);
        mPharmaciesList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // Get a RequestQueue instance
        queue = RequestQueueSingeleton.getInstance(this.getContext()).
                getRequestQueue();
        getAllPharmacies();

    }

    public void getAllPharmacies(){

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        String url = Constants.PHARMACIES_ALL_URL;
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
                        JSONArray pharmacyJsonArray = obj.getJSONArray("data");
                        pharmacies = new ArrayList<>();
                        Pharmacy pharmacy;
                        for (int i = 0; i < pharmacyJsonArray.length(); i++){
                            JSONObject pharmacyJson = pharmacyJsonArray.getJSONObject(i);
                            Log.e(TAG, "onResponse: "+pharmacyJson.getString("id"));
                            pharmacy = new Pharmacy(
                                    pharmacyJson.getString("id"),
                                    pharmacyJson.getString("name"),
                                    pharmacyJson.getString("pharmacist_name"),
                                    pharmacyJson.getString("image"),
                                    pharmacyJson.getString("location"),
                                    pharmacyJson.getString("phone"),
                                    pharmacyJson.getString("opensat"),
                                    pharmacyJson.getString("closesat"),
                                    "02/22"
                            );
                            pharmacies.add(pharmacy);
                        }

                        listAdapter = new PharmaciesAdapter(getContext(), pharmacies);
                        mPharmaciesList.setAdapter(listAdapter);

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

    public List<Pharmacy> getPharmaciesByOpeningTime(String openingTime) {
        return pharmacies;
    }
    public List<Pharmacy> getPharmaciesByClosingTime(String closingTime) {
        return pharmacies;
    }
    public List<Pharmacy> getPharmaciesByPharmacistName(String pharmacistName) {
        return pharmacies;
    }
    public List<Pharmacy> getPharmaciesByAddress(String address) {
        return pharmacies;
    }
    public List<Pharmacy> getPharmaciesByName(String name) {
        return pharmacies;
    }
}