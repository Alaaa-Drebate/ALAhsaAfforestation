package com.example.alahsaafforestation.volunteerfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.fragments.ProductDetailsFragment;


public class VolunteerAddNewServiceFragment extends Fragment {

    public static final String TAG = "volunteerAddService";

    EditText mServiceDescriptionET;
    TextView mSaveBtn;
    TextView mCancelBtn;



    public VolunteerAddNewServiceFragment() {
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
        return inflater.inflate(R.layout.fragment_volunteer_add_new_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mServiceDescriptionET = view.findViewById(R.id.service_description_edit_text);
        mSaveBtn = view.findViewById(R.id.save);
        mCancelBtn = view.findViewById(R.id.cancel);


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(validateInput()){
                    //todo : get volunteer id from shared prefs or where you stored it before ....
                    int volunteerId = 0;
                    String description = mServiceDescriptionET.getText().toString();
                    addNewService(volunteerId, description);
                    getActivity().onBackPressed();

                }
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }


    private void addNewService(int volunteerId, String description) {

    }


    //todo validate input from user in service info edit text
    private boolean validateInput() {
        return false;
    }
}