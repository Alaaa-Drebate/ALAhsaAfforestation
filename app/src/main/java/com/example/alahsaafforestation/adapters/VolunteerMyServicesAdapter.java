package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.Order;
import com.example.alahsaafforestation.model.VoluntaryService;

import java.util.ArrayList;

public class VolunteerMyServicesAdapter extends RecyclerView.Adapter<VolunteerMyServicesAdapter.ViewHolder> {


    ArrayList<VoluntaryService> services;
    Context context;

    public VolunteerMyServicesAdapter(Context context, ArrayList<VoluntaryService> services) {
        this.services = services;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.service_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VoluntaryService service = services.get(position);

        holder.name.setText(service.getDescription());

        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(context);
                final View infoDialogView = factory.inflate(R.layout.service_info_dialog, null);
                final AlertDialog infoDialog = new AlertDialog.Builder(context).create();
                infoDialog.setView(infoDialogView);

                //to look rounded
                infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView details = infoDialogView.findViewById(R.id.service_details);
                details.append(" : " + service.getDescription());

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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.edit_service_dialog, null);
                final AlertDialog editProductDialog = new AlertDialog.Builder(context).create();
                editProductDialog.setView(view);

                EditText descriptionET = view.findViewById(R.id.service_description_edit_text);
                TextView save = view.findViewById(R.id.save);
                TextView cancel = view.findViewById(R.id.cancel);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String info = descriptionET.getText().toString();
                        service.setDescription(info);
                        //to the database
                        sendNewQuantityToDatabase(service.getId(), info);
                        //to see changes immediately
                        updateService(position, service);
                        //when done dismiss;
                        editProductDialog.dismiss();

                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editProductDialog.dismiss();
                    }
                });
                editProductDialog.show();

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.delete_confirmation_dialog, null);
                final AlertDialog deleteProductDialog = new AlertDialog.Builder(context).create();
                deleteProductDialog.setView(view);

                TextView yes = view.findViewById(R.id.yes_btn);
                TextView no = view.findViewById(R.id.no_btn);


                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //to the database
                        deleteProductFromCart(service.getId());
                        //to see changes immediately
                        removeService(position);
                        //when done dismiss;
                        deleteProductDialog.dismiss();

                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteProductDialog.dismiss();
                    }
                });
                deleteProductDialog.show();

            }
        });
    }

    //todo remember to return status
    private void deleteProductFromCart(int id) {
    }

    //todo update this method to return a status
    private void sendNewQuantityToDatabase(int id, String newDescription) {

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView name;
        public ImageView info;
        public ImageView edit;
        public ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.service_details);
            this.info = itemView.findViewById(R.id.service_info);
            this.edit = itemView.findViewById(R.id.edit_btn);
            this.delete = itemView.findViewById(R.id.delete_btn);
        }
    }

    private void removeService(int index) {
        if(index >= services.size())
            services.remove(services.size()-1);
        else
            services.remove(index);

        notifyItemRemoved(index);
    }


    private void updateService(int index, VoluntaryService service) {
        if(index >= services.size())
            services.set(services.size()-1, service);
        else
            services.set(index, service);

        notifyItemChanged(index);
    }


}
