package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.Volunteer;

import java.util.List;

public class VolunteersAdapter extends RecyclerView.Adapter<VolunteersAdapter.ViewHolder>{

    Context context;
    private List<Volunteer> volunteers;
    VolunteerssOnClickListener clickListener;

    // RecyclerView recyclerView;
    public VolunteersAdapter(Context context, List<Volunteer> volunteers) {
        this.context = context;
        this.volunteers = volunteers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //the usual shit
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.volunteer_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Volunteer volunteer = volunteers.get(position);

        if(volunteer.getImageUrl() == null){
            Glide.with(context)
                    .load(context.getResources().getDrawable(R.drawable.ic_man))
                    .into(holder.image);
        }else{
            Glide.with(context)
                    .load(volunteer.getImageUrl())
                    .into(holder.image);
        }


        holder.personName.setText(volunteer.getPersonName());

        //call on click to notify main activity to switch to volunteer details fragment
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.volunteerOnItemClick(volunteer.getId(), volunteer.getPersonName(), volunteer.getDescription(), volunteer.getAvailability(), volunteer.getPhoneNumber(), volunteer.getAddress(), volunteer.getImageUrl());
            }
        });



    }

    @Override
    public int getItemCount() {
        return volunteers.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView personName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.volunteer_profile_image);
            this.personName = itemView.findViewById(R.id.volunteer_person_name);
        }
    }
    public interface VolunteerssOnClickListener{
        void volunteerOnItemClick(int id, String name, String description, int availability, String phone, String address, String imageUrl);
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (context instanceof VolunteersAdapter.VolunteerssOnClickListener) {
            clickListener = (VolunteersAdapter.VolunteerssOnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnClickListener");
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        clickListener =null;
    }




}
