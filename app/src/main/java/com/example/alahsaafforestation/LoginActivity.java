package com.example.alahsaafforestation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.api.RequestQueueSingeleton;
import com.example.alahsaafforestation.model.User;
import com.example.alahsaafforestation.sellerfragments.SellerMainFragment;
import com.example.alahsaafforestation.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailET;
    EditText mPasswordET;

    Button mSignInBtn;
    Button mRegisterBtn;
    Button mResetPasswordBtn;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            switch (SharedPrefManager.getInstance(this).getUserType()){
                case Constants.NORMAL_USER:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case Constants.SELLER:
                    startActivity(new Intent(this, SellerMainActivity.class));
                    break;
                case Constants.VOLUNTEER:
                    startActivity(new Intent(this, VolunteerMainActivity.class));
                    break;
            }
            finish();
        }

        // Get a RequestQueue
        queue = RequestQueueSingeleton.getInstance(this.getApplicationContext()).
                getRequestQueue();


        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInBtn.setEnabled(false);
                //first check if input is valid for sending
                if(validateUserData()){
                    //valid input!...now we need to know if this user exists
                    login();

                }else{
                    //didn't satisfy the input conditions
                    return;
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });

        mResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToResetPassword();
            }
        });

    }





    private void bindViews() {
        mEmailET = findViewById(R.id.email_edit_text);
        mPasswordET = findViewById(R.id.password_edit_text);
        mSignInBtn = findViewById(R.id.sign_in_btn);
        mRegisterBtn = findViewById(R.id.register_btn);
        mResetPasswordBtn = findViewById(R.id.reset_pass_btn);
    }



    //todo first check witch register type user want to make
    private void goToRegisterActivity() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.account_type_chooser_dialog, null);
        final AlertDialog accountChooserDialog = new AlertDialog.Builder(this).create();
        accountChooserDialog.setView(view);
        accountChooserDialog.setCancelable(false);
        accountChooserDialog.setCanceledOnTouchOutside(false);

        RadioGroup rg = view.findViewById(R.id.rg_chooser);
        RadioButton rbCustomer = view.findViewById(R.id.customer);
        RadioButton rbSeller = view.findViewById(R.id.seller);
        RadioButton rbVolunteer = view.findViewById(R.id.volunteer);
        Button next = view.findViewById(R.id.done);


        final int customer = rbCustomer.getId();
        final int seller = rbSeller.getId();
        final int volunteer = rbVolunteer.getId();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checked = rg.getCheckedRadioButtonId();
                if(checked == customer){
                    goToCustomerRegisterActivity();
                    accountChooserDialog.dismiss();
                }else if(checked == seller){
                    goToSellerRegisterActivity();
                    accountChooserDialog.dismiss();
                }else if(checked == volunteer){
                    goToVolunteerRegisterActivity();
                    accountChooserDialog.dismiss();
                }else{
                    Toast.makeText(LoginActivity.this, "please choose type of account you want to create", Toast.LENGTH_SHORT).show();
                }

            }
        });
        accountChooserDialog.show();

    }

    private void goToCustomerRegisterActivity() {
        Intent i = new Intent(this, CustomerRegisterActivity.class);
        startActivity(i);
    }

    private void goToVolunteerRegisterActivity() {
        Intent i = new Intent(this, VolunteerRegisterActivity.class);
        startActivity(i);
    }

    private void goToSellerRegisterActivity() {
        Intent i = new Intent(this, SellerRegisterActivity.class);
        startActivity(i);
    }

    private void goToResetPassword() {
        Intent i = new Intent(this, ResetPasswordActivity.class);
        startActivity(i);
    }


    private boolean validateUserData() {

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String pass = mPasswordET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email address!", Toast.LENGTH_SHORT).show();
            mSignInBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "please enter your password!", Toast.LENGTH_SHORT).show();
            mSignInBtn.setEnabled(true);
            return false;
        }

        return true;

    }


    public void login(){
        //first getting the values
        final String email = mEmailET.getText().toString();
        final String password = mPasswordET.getText().toString();

        String url = Constants.LOGIN_URL + "&email="+email + "&password="+password;

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //if everything is fine
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

                pDialog.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = response;

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("data");
                        User user;
                        int userType = userJson.getInt("type");
                        SharedPrefManager.getInstance(getApplicationContext()).setUserType(userType);

                        switch (userType){
                            case Constants.NORMAL_USER:
                                //creating a new customer object
                                user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("email"),
                                        userJson.getString("address"),
                                        userJson.getString("phone")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).customerLogin(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                break;
                            case Constants.SELLER:
                                //creating a new seller object
                                user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("email"),
                                        userJson.getString("address"),
                                        userJson.getString("phone"),
                                        userJson.getString("types_of_products")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).customerLogin(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), SellerMainActivity.class));
                                break;
                            case Constants.VOLUNTEER:
                                //creating a new volunteer object
                                user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("name"),
                                        userJson.getString("email"),
                                        userJson.getString("address"),
                                        userJson.getString("phone"),
                                        userJson.getInt("age"),
                                        userJson.getString("career_skils"),
                                        userJson.getString("previous_jobs")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).customerLogin(user);
                                finish();
                                startActivity(new Intent(getApplicationContext(), VolunteerMainActivity.class));
                                break;
                        }

                        mSignInBtn.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        mSignInBtn.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    mSignInBtn.setEnabled(true);
                }
        });

        queue.add(jsonObjectRequest);
        queue.start();
    }



}