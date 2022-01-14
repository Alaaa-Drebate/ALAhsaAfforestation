package com.example.alahsaafforestation.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.api.Constants;


public class VolunteerDetailsFragment extends Fragment {

    public static final String TAG = "customerVolunteerDetails";


    TextView nameTv;
    TextView descriptionTv;
    TextView publishDateTv;
    TextView availabilityTv;
    TextView phoneTv;
    TextView addressTv;
    ImageView image;


    private int id;
    private String name;
    private String description;
    private String publishDate;
    private int availability;
    private String phone;
    private String address;
    private String imageUrl;

    ChatWithVolunteerListener listener;



    Button messageVolunteerBtn;

    public VolunteerDetailsFragment() {
        // Required empty public constructor
    }

    public static VolunteerDetailsFragment newInstance(int itemId, String name, String description, int availability, String phone, String address, String imageUrl) {
        VolunteerDetailsFragment fragment = new VolunteerDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("item_id", itemId);
        args.putString("v_name", name);
        args.putString("v_description", description);
        args.putInt("v_available", availability);
        args.putString("v_phone", phone);
        args.putString("v_address", address);
        args.putString("v_image", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("item_id");
            name = getArguments().getString("v_name");
            description = getArguments().getString("v_description");
            availability = getArguments().getInt("v_available");
            phone = getArguments().getString("v_phone");
            address = getArguments().getString("v_address");
            imageUrl = getArguments().getString("v_image");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volunteer_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        image = view.findViewById(R.id.volunteer_profile_image);
        nameTv = view.findViewById(R.id.volunteer_person_name);
        descriptionTv = view.findViewById(R.id.description_text_view);
        publishDateTv = view.findViewById(R.id.publish_date_text_view);
        availabilityTv = view.findViewById(R.id.availability_text_view);
        phoneTv = view.findViewById(R.id.volunteer_phone_text_view);
        addressTv = view.findViewById(R.id.volunteer_address_text_view);

        messageVolunteerBtn = view.findViewById(R.id.message_volunteer_btn);

        Context context = getContext();
        if(imageUrl == null||imageUrl.isEmpty()){
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.ic_man))
                    .into(image);
        }else{
            Glide.with(context)
                    .load(imageUrl)
                    .into(image);
        }

        nameTv.setText(name);
        descriptionTv.setText(description);
        publishDateTv.setText("03-10-2021");
        if(availability == Constants.VOLUNTEER_AVAILABLE){
            availabilityTv.setText("yes");
        }else{
            availabilityTv.setText("no");
        }
        phoneTv.setText(phone);
        addressTv.setText(address);

        messageVolunteerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.chatWithVolunteer(String.valueOf(id), name);
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof VolunteerDetailsFragment.ChatWithVolunteerListener) {
            listener = (VolunteerDetailsFragment.ChatWithVolunteerListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement chatWithSellerListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ChatWithVolunteerListener {
        void chatWithVolunteer(String volunteerId, String volunteerName);
    }
}