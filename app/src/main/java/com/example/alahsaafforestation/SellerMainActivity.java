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
import com.example.alahsaafforestation.sellerfragments.SellerAddNewProduct;
import com.example.alahsaafforestation.sellerfragments.SellerMainFragment;
import com.example.alahsaafforestation.sellerfragments.SellerMyProductsFragment;
import com.example.alahsaafforestation.utils.SharedPrefManager;

public class SellerMainActivity extends AppCompatActivity implements ChatsAdapter.ChatsOnClickListener, SellerMyProductsFragment.OnAddProductClicked{



    ImageView mHomeBtn;
    ImageView mEmailBtn;
    ImageView mMenuBtn;

    //for set the other person name when starting chat with him
    TextView mChattingWithName;

    FragmentManager fm;
    SellerMainFragment main;
    SellerMyProductsFragment myProductsFragment;
    ChattingFragment chatsFragment;

    ChatInActionFragment messagingFragment;

    boolean outOfMainFragment = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);

        mMenuBtn = findViewById(R.id.menu_btn);
        mHomeBtn = findViewById(R.id.home_btn);
        mEmailBtn = findViewById(R.id.email_btn);

        mChattingWithName = findViewById(R.id.chatting_with_name_tv);
        mChattingWithName.setVisibility(View.GONE);

        if(savedInstanceState == null){
            main = new SellerMainFragment();
            myProductsFragment = new SellerMyProductsFragment();
            chatsFragment = new ChattingFragment();


            fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.content_fragment, main, SellerMainFragment.TAG).commit();
        }




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
                fm.beginTransaction().replace(R.id.content_fragment, main, SellerMainFragment.TAG).commit();
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


    public void goToProfile(){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.seller_main_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.profile:
                goToProfile();
                break;
            case R.id.products:
                fm.beginTransaction().replace(R.id.content_fragment, myProductsFragment, SellerMyProductsFragment.TAG).commit();
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
    public void onAddProductClicked() {
        SellerAddNewProduct sellerAddNewProduct = new SellerAddNewProduct();
        fm.beginTransaction().replace(R.id.content_fragment,  sellerAddNewProduct, SellerAddNewProduct.TAG).commit();
    }


    @Override
    public void onBackPressed() {
        ChatInActionFragment chatFragment = (ChatInActionFragment) getSupportFragmentManager().findFragmentByTag(ChatInActionFragment.TAG);
        SellerAddNewProduct sellerAddNewProduct = (SellerAddNewProduct) getSupportFragmentManager().findFragmentByTag(SellerAddNewProduct.TAG);
        if (chatFragment != null && chatFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, chatsFragment, ChattingFragment.TAG).commit();
            mMenuBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down_24));
            mChattingWithName.setVisibility(View.GONE);
        }
        if (sellerAddNewProduct != null && sellerAddNewProduct.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, myProductsFragment, SellerMyProductsFragment.TAG).commit();
            return;
        }

        if(outOfMainFragment){
            outOfMainFragment =false;
            fm.beginTransaction().replace(R.id.content_fragment, main, SellerMainFragment.TAG).commit();
        }

        else
            finish();
    }
}