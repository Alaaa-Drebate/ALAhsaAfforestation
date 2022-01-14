package com.example.alahsaafforestation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.utils.SharedPrefManager;

public class VerificationActivity extends AppCompatActivity {


    EditText mVerificationCodeET;
    Button mVerifyBtn;

    //get it from extras
    int userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        bindViews();

        SharedPrefManager sp = SharedPrefManager.getInstance(this);
        userType = sp.getUserType();

        mVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVerifyBtn.setEnabled(false);
                if(validateInput()){
                    if(checkCode()){
                        switch (userType){
                            case Constants.NORMAL_USER:
                                goToMainActivity();
                                break;
                            case Constants.SELLER:
                                goToSellerNameActivity();
                                break;
                            case Constants.VOLUNTEER:
                                goToVolunteersMainActivity();
                                break;
                        }
                    }
                }
            }
        });
    }

    private boolean validateInput(){
        final String code = mVerificationCodeET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "please enter your verification code sent yo your email !", Toast.LENGTH_SHORT).show();
            mVerifyBtn.setEnabled(true);
            return false;
        }

        return true;
    }

    private boolean checkCode(){
        return (mVerificationCodeET.getText().toString().equals("000000"));
    }

    private void bindViews() {
        mVerificationCodeET = findViewById(R.id.verification_code_edit_text);
        mVerifyBtn = findViewById(R.id.verify_btn);
    }

    private void goToVolunteersMainActivity() {
        Intent i = new Intent(this, VolunteerMainActivity.class);
        startActivity(i);
        finish();
    }

    private void goToSellerNameActivity() {
        Intent i = new Intent(this, SellerMainActivity.class);
        startActivity(i);
        finish();
    }

    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}