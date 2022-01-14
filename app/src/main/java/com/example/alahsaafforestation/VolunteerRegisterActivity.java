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
import com.skydoves.powerspinner.PowerSpinnerView;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerRegisterActivity extends AppCompatActivity {

    EditText mEmailET;
    EditText mNameET;
    EditText mPasswordET;
    EditText mPasswordRetypeET;
    EditText mPhoneET;
    EditText mAddressET;
    PowerSpinnerView mAgeChooser;
    Button mNextCreateAccountBtn;
    EditText mCareerSkillsET;
    EditText mPreviousJobsET;


    String selectedAge = "";

    //next || create account , btn titles
    String next;
    String createAccount;

    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_register);

        bindViews();

        //to know were we are in the registration form
        next = getResources().getString(R.string.next);
        createAccount = getResources().getString(R.string.create_account);

        mNextCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mNextCreateAccountBtn.getText().toString().trim();
                if(title.equals(next)){
                    if(validateInputFirstPage())
                    {
                        toSecondPage();
                        mNextCreateAccountBtn.setText(createAccount);
                    }
                }else if(title.equals(createAccount)){
                    if(validateInputSecondPage()){
                        mNextCreateAccountBtn.setEnabled(false);
                        registerVolunteer();
                    }
                }
            }
        });

        String[] ageRange = getResources().getStringArray(R.array.age_range);
        mAgeChooser.setOnSpinnerItemSelectedListener((i, o, i1, t1) -> {
            selectedAge = ageRange[i1];
        });

        // Get a RequestQueue
        queue = RequestQueueSingeleton.getInstance(this.getApplicationContext()).
                getRequestQueue();


    }



    private void goToVerificationActivity() {
        Intent i = new Intent(this, VerificationActivity.class);
        startActivity(i);
    }

    private void toSecondPage() {
        mEmailET.setVisibility(View.INVISIBLE);
        mNameET.setVisibility(View.INVISIBLE);
        mPasswordET.setVisibility(View.INVISIBLE);
        mPasswordRetypeET.setVisibility(View.INVISIBLE);
        mPhoneET.setVisibility(View.INVISIBLE);
        mAddressET.setVisibility(View.INVISIBLE);
        mAgeChooser.setVisibility(View.INVISIBLE);
        mCareerSkillsET.setVisibility(View.VISIBLE);
        mPreviousJobsET.setVisibility(View.VISIBLE);
    }

    private void toFirstPage() {
        mCareerSkillsET.setVisibility(View.VISIBLE);
        mPreviousJobsET.setVisibility(View.VISIBLE);
        mEmailET.setVisibility(View.INVISIBLE);
        mNameET.setVisibility(View.INVISIBLE);
        mPasswordET.setVisibility(View.INVISIBLE);
        mPasswordRetypeET.setVisibility(View.INVISIBLE);
        mPhoneET.setVisibility(View.INVISIBLE);
        mAddressET.setVisibility(View.INVISIBLE);
        mAgeChooser.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(mNextCreateAccountBtn.getText().toString().equals(createAccount)){
            mNextCreateAccountBtn.setText(next);
            toSecondPage();
            toFirstPage();
        }else{
            super.onBackPressed();
        }
    }

    private boolean validateInputSecondPage() {

        final String careerSkills = mCareerSkillsET.getText().toString();
        final String previousJobs = mPreviousJobsET.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(careerSkills)) {
            Toast.makeText(this, "please enter your career skills!", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(previousJobs)) {
            Toast.makeText(this, "please enter your previous jobs!", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }
        return true;
    }

    private void bindViews() {
        mEmailET = findViewById(R.id.email_edit_text);
        mNameET = findViewById(R.id.name_edit_text);
        mPasswordET = findViewById(R.id.password_edit_text);
        mPasswordRetypeET = findViewById(R.id.password_retype_edit_text);
        mPhoneET = findViewById(R.id.phone_edit_text);
        mAddressET = findViewById(R.id.address_edit_text);
        mAgeChooser = findViewById(R.id.age_chooser);
        mNextCreateAccountBtn = findViewById(R.id.next_create_account_btn);
        mCareerSkillsET = findViewById(R.id.next_career_skills_edit_text);
        mPreviousJobsET = findViewById(R.id.next_previous_jobs_edit_text);
    }


    private boolean validateInputFirstPage() {

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String pass = mPasswordET.getText().toString();
        final String pass2 = mPasswordRetypeET.getText().toString();
        final String name =mNameET.getText().toString();
        final String phone = mPhoneET.getText().toString();
        final String address = mAddressET.getText().toString();
        final String age = selectedAge;

        //checking if username is empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "please enter your email address!", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "please enter your password!", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        //checking if username is empty
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "please enter your full name !", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "please enter your phone number!", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        //checking if password is empty
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "please enter your address!", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        if(!TextUtils.equals(pass, pass2)){
            Toast.makeText(this, "passwords are not matching! please try again.", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }

        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "please select how many years old are you?", Toast.LENGTH_SHORT).show();
            mNextCreateAccountBtn.setEnabled(true);
            return false;
        }



        return true;

    }


    public void registerVolunteer(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Processing Please wait...");
        pDialog.show();

        //first getting the values
        final String email = mEmailET.getText().toString();
        final String password = mPasswordET.getText().toString();
        final String name =mNameET.getText().toString();
        final String phone = mPhoneET.getText().toString();
        final String address = mAddressET.getText().toString();
        final String age = selectedAge;
        final String careerSkills = mCareerSkillsET.getText().toString();
        final String previousJobs = mPreviousJobsET.getText().toString();

        String url = Constants.REGISTER_URL + "&email="+email + "&password="+password + "&name="+name + "&phone="+phone + "&address="+address+ "&user_type="+Constants.VOLUNTEER + "&career_skils="+careerSkills + "&previous_jobs="+previousJobs + "&age="+age ;
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
                        SharedPrefManager.getInstance(getApplicationContext()).setUserType(Constants.VOLUNTEER);
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
                        SharedPrefManager.getInstance(getApplicationContext()).volunteerLogin(user);
                        finish();
                        goToVerificationActivity();

                        mNextCreateAccountBtn.setEnabled(true);
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        mNextCreateAccountBtn.setEnabled(true);
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
                        mNextCreateAccountBtn.setEnabled(true);
                    }
                });

        queue.add(jsonObjectRequest);
        queue.start();
    }
}