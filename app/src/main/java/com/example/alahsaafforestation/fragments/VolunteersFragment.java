package com.example.alahsaafforestation.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.adapters.ProductsAdapter;
import com.example.alahsaafforestation.adapters.VolunteersAdapter;
import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.api.RequestQueueSingeleton;
import com.example.alahsaafforestation.model.Product;
import com.example.alahsaafforestation.model.Volunteer;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class VolunteersFragment extends Fragment {


    public static final String TAG = "customerVolunteers";

    TextInputEditText mSearchET;

    RecyclerView mVolunteersListView;
    List<Volunteer> volunteers;
    VolunteersAdapter volunteersAdapter;

    RequestQueue queue;


    public VolunteersFragment() {
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
        return inflater.inflate(R.layout.fragment_volunteers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchET = view.findViewById(R.id.search_edit_text);
        mVolunteersListView = view.findViewById(R.id.volunteers_list);

        // Get a RequestQueue instance
        queue = RequestQueueSingeleton.getInstance(this.getContext()).
        getRequestQueue();

        getVolunteers();
    }


    public void getVolunteers() {
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        String url = Constants.GET_ALL_VOLUNTEERS;
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
                        JSONArray volunteersArray = obj.getJSONArray("data");
                        volunteers = new ArrayList<>();
                        Volunteer volunteer;
                        for (int i = 0; i < volunteersArray.length(); i++){
                            JSONObject volunteerJson = volunteersArray.getJSONObject(i);
                            volunteer = new Volunteer(
                                    Integer.parseInt(volunteerJson.getString("id")),
                                    volunteerJson.getString("name"),
                                    volunteerJson.getString("description"),
                                    Integer.parseInt(volunteerJson.getString("availability")),
                                    volunteerJson.getString("image"),
                                    volunteerJson.getString("phone"),
                                    volunteerJson.getString("address")
                            );
                            volunteers.add(volunteer);
                        }

                        volunteersAdapter = new VolunteersAdapter(getContext(), volunteers);
                        mVolunteersListView.setAdapter(volunteersAdapter);

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
    public List<Volunteer> getVolunteersByName() {
        return volunteers;
    }
}