package com.example.alahsaafforestation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alahsaafforestation.adapters.ChatsAdapter;
import com.example.alahsaafforestation.fragments.ChatInActionFragment;
import com.example.alahsaafforestation.fragments.ChattingFragment;
import com.example.alahsaafforestation.fragments.MainFragment;
import com.example.alahsaafforestation.utils.SharedPrefManager;
import com.example.alahsaafforestation.volunteerfragments.VolunteerAddNewServiceFragment;
import com.example.alahsaafforestation.volunteerfragments.VolunteerMainFragment;
import com.example.alahsaafforestation.volunteerfragments.VolunteerMyServicesFragment;

public class VolunteerMainActivity extends AppCompatActivity implements ChatsAdapter.ChatsOnClickListener, VolunteerMyServicesFragment.OnAddServiceClicked{


    ImageView mHomeBtn;
    ImageView mEmailBtn;
    ImageView mMenuBtn;

    TextView mChattingWithName;

    FragmentManager fm;
    VolunteerMainFragment main;
    VolunteerMyServicesFragment myServicesFragment;
    VolunteerAddNewServiceFragment addNewServiceFragment;
    ChattingFragment chatsFragment;

    ChatInActionFragment messagingFragment;

    boolean outOfMainFragment = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_main);

        if(savedInstanceState == null){
            main = new VolunteerMainFragment();
            myServicesFragment = new VolunteerMyServicesFragment();
            addNewServiceFragment = new VolunteerAddNewServiceFragment();
            chatsFragment = new ChattingFragment();


            fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.content_fragment, main, VolunteerMainFragment.TAG).commit();


        }

        mMenuBtn = findViewById(R.id.menu_btn);
        mHomeBtn = findViewById(R.id.home_btn);
        mEmailBtn = findViewById(R.id.email_btn);

        mChattingWithName = findViewById(R.id.chatting_with_name_tv);
        mChattingWithName.setVisibility(View.GONE);


        registerForContextMenu(mMenuBtn);
        mMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(v);
            }
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.beginTransaction().replace(R.id.content_fragment, main, MainFragment.TAG).commit();
                outOfMainFragment = false;
            }
        });

    }



    public void logOut(){
        SharedPrefManager.getInstance(this).logout();
        PackageManager packageManager = getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }

    private void goToWelcomeScreen() {
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
    }

    public void goToProfile(){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.volunteer_main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.profile:
                goToProfile();
                break;
            case R.id.services:
                fm.beginTransaction().replace(R.id.content_fragment, myServicesFragment, VolunteerMyServicesFragment.TAG).commit();
                outOfMainFragment = true;
                break;
            case R.id.messages:
                fm.beginTransaction().replace(R.id.content_fragment, chatsFragment, ChattingFragment.TAG).commit();
                outOfMainFragment = true;
                break;
            case R.id.log_out:
                logOut();
                break;
            default:
                return true;
        }
        return true;
    }





    @Override
    public void chatsOnItemClick(String otherId, String otherName) {
        messagingFragment = ChatInActionFragment.newInstance(otherId);
        fm.beginTransaction().replace(R.id.content_fragment, messagingFragment, ChatInActionFragment.TAG).commit();
        mMenuBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_man));
        mChattingWithName.setText(otherName);
        mChattingWithName.setVisibility(View.VISIBLE);
    }




    @Override
    public void onBackPressed() {
        ChatInActionFragment chatFragment = (ChatInActionFragment) getSupportFragmentManager().findFragmentByTag(ChatInActionFragment.TAG);
        VolunteerAddNewServiceFragment addNewServiceFragment = (VolunteerAddNewServiceFragment) fm.findFragmentByTag(VolunteerAddNewServiceFragment.TAG);
        if (chatFragment != null && chatFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, chatsFragment, ChattingFragment.TAG).commit();
            mMenuBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down_24));
            mChattingWithName.setVisibility(View.GONE);
            return;
        }
        if (addNewServiceFragment != null && addNewServiceFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, myServicesFragment, VolunteerMyServicesFragment.TAG).commit();
            return;
        }
        if(outOfMainFragment){
            outOfMainFragment =false;
            fm.beginTransaction().replace(R.id.content_fragment, main, MainFragment.TAG).commit();
        }

        else
            finish();
    }

    @Override
    public void onAddServiceClicked() {
        VolunteerAddNewServiceFragment addNewServiceFragment = new VolunteerAddNewServiceFragment();
        fm.beginTransaction().replace(R.id.content_fragment, addNewServiceFragment, VolunteerAddNewServiceFragment.TAG).commit();
    }
}