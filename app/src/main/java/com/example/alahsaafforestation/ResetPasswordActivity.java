package com.example.alahsaafforestation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class ResetPasswordActivity extends AppCompatActivity {


    EditText mEmailET;
    EditText mPasswordET;
    EditText mPasswordRetypeET;

    Button mResetPassBtn;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmailET = findViewById(R.id.email_edit_text);
        mPasswordET = findViewById(R.id.password_edit_text);
        mPasswordRetypeET = findViewById(R.id.password_retype_edit_text);

        mResetPassBtn = findViewById(R.id.reset_pass_btn);

        // Get a RequestQueue
        queue = RequestQueueSingeleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        mResetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateUserData()){

                    mResetPassBtn.setEnabled(false);
                    updatePassword();

                }else{
                    //didn't satisfy the input conditions
                    return;
                }
            }
        });
    }


    private boolean validateUserData() {

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String pass = mPasswordET.getText().toString();
        final String pass2 = mPasswordRetypeET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email address!", Toast.LENGTH_SHORT).show();
            mResetPassBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "please enter your password!", Toast.LENGTH_SHORT).show();
            mResetPassBtn.setEnabled(true);
            return false;
        }

        if(!TextUtils.equals(pass, pass2)){
            Toast.makeText(this, "passwords are not matching! please try again.", Toast.LENGTH_SHORT).show();
            mResetPassBtn.setEnabled(true);
            return false;
        }

        return true;
    }


    private void updatePassword() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String password = mPasswordET.getText().toString();


        String url = Constants.RESET_PASSWORD + "&email="+email + "&new_password="+password;

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

                        mResetPassBtn.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        mResetPassBtn.setEnabled(true);
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
                        mResetPassBtn.setEnabled(true);
                    }
                });

        queue.add(jsonObjectRequest);
        queue.start();

    }


}