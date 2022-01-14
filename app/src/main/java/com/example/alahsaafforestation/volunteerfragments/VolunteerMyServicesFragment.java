package com.example.alahsaafforestation.volunteerfragments;

import android.content.Context;
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
import com.example.alahsaafforestation.adapters.VolunteerMyServicesAdapter;
import com.example.alahsaafforestation.model.VoluntaryService;
import com.example.alahsaafforestation.sellerfragments.SellerMyProductsFragment;

import java.util.ArrayList;


public class VolunteerMyServicesFragment extends Fragment {

    public static final String TAG = "volunteerMyServices";


    ArrayList<VoluntaryService> services;
    RecyclerView mMyServicesList;
    VolunteerMyServicesAdapter mMyServicesAdapter;

    Button mAddServiceBtn;


    OnAddServiceClicked mListener;



    public VolunteerMyServicesFragment() {
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
        return inflater.inflate(R.layout.fragment_volunteer_my_services, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMyServicesList = view.findViewById(R.id.volunteer_my_services_list);
        mAddServiceBtn = view.findViewById(R.id.add_service_btn);

        services = new ArrayList<VoluntaryService>()
        {{
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));
            add(new VoluntaryService(1, "description", 3));

        }};

        mMyServicesAdapter = new VolunteerMyServicesAdapter(getContext(), services);

        mMyServicesList.setAdapter(mMyServicesAdapter);


        mAddServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddServiceClicked();
            }
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof VolunteerMyServicesFragment.OnAddServiceClicked) {
            mListener = (VolunteerMyServicesFragment.OnAddServiceClicked) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAddServiceClicked{
        void onAddServiceClicked();
    }

}