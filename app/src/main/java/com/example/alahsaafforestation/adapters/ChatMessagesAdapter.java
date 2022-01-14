package com.example.alahsaafforestation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alahsaafforestation.R;
import com.example.alahsaafforestation.model.BaseMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter {

    public static final int MESSAGE_FROM_OTHER = 1;
    public static final int MESSAGE_FROM_ME = 0;

    ArrayList<BaseMessage> mMessageList;
    Context context;


    public ChatMessagesAdapter(ArrayList<BaseMessage> mMessageList, Context context) {
        this.mMessageList = mMessageList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        BaseMessage message =  mMessageList.get(position);

        if(message.isFromMe()){
            return MESSAGE_FROM_ME;
        }else {
            return MESSAGE_FROM_OTHER;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        if (viewType == MESSAGE_FROM_ME) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_list_item_me, parent, false);
            return new ViewHolderFromMe(view);
        } else if (viewType == MESSAGE_FROM_OTHER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_list_item_other, parent, false);
            return new ViewHolderFromOther(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BaseMessage message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_FROM_ME:
                ((ViewHolderFromMe) holder).bind(message);
                break;
            case MESSAGE_FROM_OTHER:
                ((ViewHolderFromOther) holder).bind(message);
        }

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class ViewHolderFromMe extends RecyclerView.ViewHolder {

        public TextView message;

        public ViewHolderFromMe(View itemView) {
            super(itemView);
            this.message = itemView.findViewById(R.id.text_chat_message_me);
        }

        void bind(BaseMessage message) {
            this.message.setText(message.getMessage());
        }
    }

    public class ViewHolderFromOther extends RecyclerView.ViewHolder {

        public TextView message;

        public ViewHolderFromOther(View itemView) {
            super(itemView);
            this.message = itemView.findViewById(R.id.text_chat_message_other);
        }
        void bind(BaseMessage message) {
            this.message.setText(message.getMessage());

        }


    }


}
