package com.example.alahsaafforestation.volunteerfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.adapters.VolunteerRequestsAdapter;
import com.example.alahsaafforestation.model.CustomerServices;
import com.example.alahsaafforestation.model.VoluntaryService;

import java.util.ArrayList;


public class VolunteerMainFragment extends Fragment {

    public static final String TAG = "volunteerMain";

    ArrayList<CustomerServices> services;
    RecyclerView mCustomerRequestsList;
    VolunteerRequestsAdapter mCustomerRequestsAdapter;


    public VolunteerMainFragment() {
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
        return inflater.inflate(R.layout.fragment_volunteer_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCustomerRequestsList = view.findViewById(R.id.customer_service_request_list);

        services = new ArrayList<CustomerServices>()
        {{
            add(new CustomerServices(0, "abdullah", new VoluntaryService(1, "I can help you plant some tomato", 2), "20/02/2021"));
            add(new CustomerServices(0, "abdullah", new VoluntaryService(1, "I can help you plant some tomato", 2), "20/02/2021"));
            add(new CustomerServices(0, "abdullah", new VoluntaryService(1, "I can help you plant some tomato", 2), "20/02/2021"));
            add(new CustomerServices(0, "abdullah", new VoluntaryService(1, "I can help you plant some tomato", 2), "20/02/2021"));
            add(new CustomerServices(0, "abdullah", new VoluntaryService(1, "I can help you plant some tomato", 2), "20/02/2021"));
            add(new CustomerServices(0, "abdullah", new VoluntaryService(1, "I can help you plant some tomato", 2), "20/02/2021"));

        }};


        mCustomerRequestsAdapter = new VolunteerRequestsAdapter(getContext(), services);

        mCustomerRequestsList.setAdapter(mCustomerRequestsAdapter);

    }


}