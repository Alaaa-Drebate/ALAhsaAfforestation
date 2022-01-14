package com.example.alahsaafforestation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.alahsaafforestation.api.Constants;
import com.example.alahsaafforestation.api.RequestQueueSingeleton;
import com.example.alahsaafforestation.model.User;
import com.example.alahsaafforestation.utils.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfile extends AppCompatActivity {

    EditText mEmailET;
    EditText mNameET;
    EditText mPasswordET;
    EditText mRetypePasswordET;
    EditText mPhoneET;

    Button mSaveBtn;
    Button mCancelBtn;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mEmailET = findViewById(R.id.email_edit_text);
        mNameET = findViewById(R.id.name_edit_text);
        mPasswordET = findViewById(R.id.password_edit_text);
        mRetypePasswordET = findViewById(R.id.password_retype_edit_text);
        mPhoneET = findViewById(R.id.phone_edit_text);

        mSaveBtn = findViewById(R.id.save_new_info_btn);
        mCancelBtn = findViewById(R.id.cancel_editing_btn);

        // Get a RequestQueue
        queue = RequestQueueSingeleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUserData()){
                    updateUserData();
                }
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private boolean validateUserData() {

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String pass = mPasswordET.getText().toString();
        final String pass2 = mRetypePasswordET.getText().toString();
        final String name =mNameET.getText().toString();
        final String phone = mPhoneET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email address!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "please enter your password!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        //checking if username is empty
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "please enter your full name !", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "please enter your phone number!", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }


        if(!TextUtils.equals(pass, pass2)){
            Toast.makeText(this, "passwords are not matching! please try again.", Toast.LENGTH_SHORT).show();
            mSaveBtn.setEnabled(true);
            return false;
        }


        return true;

    }


    public void updateUserData(){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String password = mPasswordET.getText().toString();
        final String name =mNameET.getText().toString();
        final String phone = mPhoneET.getText().toString();

        String url = Constants.UPDATE_USER_DATA + "&user_id="+ SharedPrefManager.getInstance(this).getUserId() + "&email="+email + "&password="+password + "&name="+name + "&phone="+phone;
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

                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("data");
                        User user;
                        SharedPrefManager.getInstance(getApplicationContext()).setUserType(Constants.NORMAL_USER);
                        user = new User(
                                userJson.getInt("id"),
                                userJson.getString("name"),
                                userJson.getString("email"),
                                userJson.getString("phone")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).updateUser(user);
                        finish();
                        onBackPressed();

                        mSaveBtn.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        mSaveBtn.setEnabled(true);
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
                        mSaveBtn.setEnabled(true);
                    }
                });

        queue.add(jsonObjectRequest);
        queue.start();
    }
}