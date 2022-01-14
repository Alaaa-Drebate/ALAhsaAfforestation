package com.example.alahsaafforestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alahsaafforestation.model.User;
import com.example.alahsaafforestation.utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    ImageView mHomeBtn;
    Button mEditProfileBtn;

    TextView mUserNameTV;
    TextView mPhoneTV;
    TextView mEmailTV;

    String name, phone, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mHomeBtn = findViewById(R.id.home_btn);
        mEditProfileBtn = findViewById(R.id.edit_profile_btn);

        mUserNameTV = findViewById(R.id.user_name_text_view);
        mPhoneTV = findViewById(R.id.phone_text_view);
        mEmailTV = findViewById(R.id.email_text_view);

        User user = SharedPrefManager.getInstance(this).getCustomerData();

        mUserNameTV.setText(user.getName());
        mPhoneTV.setText(user.getPhone());
        mEmailTV.setText(user.getEmail());

        //go back to home activity
        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //go to edit profile activity
        mEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditProfile();
            }
        });
    }

    public void goToEditProfile(){
        Intent i = new Intent(this, EditProfile.class);
        startActivity(i);
    };
}