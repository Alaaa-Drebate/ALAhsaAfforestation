package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.CustomerServices;
import com.example.alahsaafforestation.model.VoluntaryService;

import java.util.ArrayList;

public class VolunteerRequestsAdapter extends RecyclerView.Adapter<VolunteerRequestsAdapter.ViewHolder> {

    Context context;
    ArrayList<CustomerServices> services;

    public VolunteerRequestsAdapter(Context context, ArrayList<CustomerServices> services) {
        this.context = context;
        this.services = services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.voluntary_service_request_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CustomerServices service = services.get(position);
        VoluntaryService serviceInfo = service.getService();

        holder.customerName.setText(service.getCustomerName());

        holder.serviceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View infoDialogView = factory.inflate(R.layout.service_info_dialog, null);
                final AlertDialog infoDialog = new AlertDialog.Builder(context).create();
                infoDialog.setView(infoDialogView);

                //to look rounded
                infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView details = infoDialogView.findViewById(R.id.service_details);
                details.append(" : " + serviceInfo.getDescription());

                infoDialogView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //your business logic
                        infoDialog.dismiss();
                    }
                });
                infoDialog.show();
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrderFromCustomer(service.getId());
                removeOrder(position);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectOrderFromCustomer(service.getId());
                removeOrder(position);
            }
        });
    }

    private void rejectOrderFromCustomer(int id) {

    }

    private void acceptOrderFromCustomer(int id) {
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView customerName;
        public ImageView serviceInfo;
        public ImageView accept;
        public ImageView reject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.customerName = itemView.findViewById(R.id.customer_name);
            this.serviceInfo = itemView.findViewById(R.id.service_info);
            this.accept = itemView.findViewById(R.id.accept_btn);
            this.reject =itemView.findViewById(R.id.reject_btn);
        }
    }

    private void removeOrder(int index) {
        services.remove(index);
        notifyItemRemoved(index);
    }

}
