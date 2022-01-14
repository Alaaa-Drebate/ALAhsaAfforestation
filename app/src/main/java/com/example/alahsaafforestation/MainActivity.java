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
import com.example.alahsaafforestation.adapters.ProductsAdapter;
import com.example.alahsaafforestation.adapters.VolunteersAdapter;
import com.example.alahsaafforestation.fragments.ChatInActionFragment;
import com.example.alahsaafforestation.fragments.ChattingFragment;
import com.example.alahsaafforestation.fragments.MainFragment;
import com.example.alahsaafforestation.fragments.MyCartFragment;
import com.example.alahsaafforestation.fragments.ProductDetailsFragment;
import com.example.alahsaafforestation.fragments.ProductsFragment;
import com.example.alahsaafforestation.fragments.VolunteerDetailsFragment;
import com.example.alahsaafforestation.fragments.VolunteersFragment;
import com.example.alahsaafforestation.offlinedata.Myappdatabas;
import com.example.alahsaafforestation.utils.SharedPrefManager;


public class MainActivity extends AppCompatActivity implements ProductsAdapter.ProductsOnClickListener,
                                                               VolunteersAdapter.VolunteerssOnClickListener,
                                                               ChatsAdapter.ChatsOnClickListener,
                                                                ProductDetailsFragment.ChatWithSellerListener,
                                                                VolunteerDetailsFragment.ChatWithVolunteerListener{


    ImageView mHomeBtn;
    ImageView mEmailBtn;
    ImageView mMenuBtn;

    TextView mChattingWithName;

    FragmentManager fm;
    MainFragment main;
    ProductsFragment productsFragment;
    VolunteersFragment volunteersFragment;
    ChattingFragment chatsFragment;
    MyCartFragment myCartFragment;

    ProductDetailsFragment productDetailsFragment;
    VolunteerDetailsFragment volunteerDetailsFragment;
    ChatInActionFragment messagingFragment;

    boolean outOfMainFragment = false;

    Myappdatabas myappdatabas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            main = new MainFragment();
            productsFragment = new ProductsFragment();
            volunteersFragment = new VolunteersFragment();
            chatsFragment = new ChattingFragment();
            myCartFragment = new MyCartFragment();

            //initializing room database
            myappdatabas = Myappdatabas.getDatabase(this);

            fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.content_fragment, main, MainFragment.TAG).commit();


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

        mEmailBtn.setOnClickListener(v -> {
            fm.beginTransaction().replace(R.id.content_fragment, chatsFragment, ChattingFragment.TAG).commit();
            outOfMainFragment = true;
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
    };

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
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
                fm.beginTransaction().replace(R.id.content_fragment, productsFragment, ProductsFragment.TAG).commit();
                outOfMainFragment = true;
                break;
            case R.id.volunteers:
                fm.beginTransaction().replace(R.id.content_fragment, volunteersFragment, VolunteersFragment.TAG).commit();
                outOfMainFragment = true;
                break;
            case R.id.messages:
                fm.beginTransaction().replace(R.id.content_fragment, chatsFragment, ChattingFragment.TAG).commit();
                outOfMainFragment = true;
                break;
            case R.id.my_cart:
                fm.beginTransaction().replace(R.id.content_fragment, myCartFragment, MyCartFragment.TAG).commit();
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
    public void productsOnItemClick(int product_id, String productName, double price, String sellerName, String image, String available, String description, String plantingDate, String sellerId) {
        productDetailsFragment = ProductDetailsFragment.newInstance(product_id, productName, price, sellerName, image, available, description, plantingDate, sellerId);
        fm.beginTransaction().replace(R.id.content_fragment, productDetailsFragment, ProductDetailsFragment.TAG).commit();
    }


    @Override
    public void volunteerOnItemClick(int id, String name, String description, int availability, String phone, String address, String imageUrl) {
        volunteerDetailsFragment = VolunteerDetailsFragment.newInstance(id, name, description, availability, phone, address, imageUrl);
        fm.beginTransaction().replace(R.id.content_fragment, volunteerDetailsFragment, VolunteerDetailsFragment.TAG).commit();
    }

    @Override
    public void chatWithSeller(String sellerId, String sellerName) {
        chatsOnItemClick(sellerId, sellerName);
    }

    @Override
    public void chatWithVolunteer(String volunteerId, String volunteerName) {
        chatsOnItemClick(volunteerId, volunteerName);
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
        VolunteerDetailsFragment volunteerDetailsFragment = (VolunteerDetailsFragment) getSupportFragmentManager().findFragmentByTag(VolunteerDetailsFragment.TAG);
        ProductDetailsFragment productDetailsFragment = (ProductDetailsFragment) getSupportFragmentManager().findFragmentByTag(ProductDetailsFragment.TAG);
        if (chatFragment != null && chatFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, chatsFragment, ChattingFragment.TAG).commit();
            mMenuBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down_24));
            mChattingWithName.setVisibility(View.GONE);
            return;
        }
        if (volunteerDetailsFragment != null && volunteerDetailsFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, volunteersFragment, VolunteersFragment.TAG).commit();
            return;
        }
        if (productDetailsFragment != null && productDetailsFragment.isVisible()) {
            fm.beginTransaction().replace(R.id.content_fragment, productsFragment, ProductsFragment.TAG).commit();
            return;
        }
        if(outOfMainFragment){
            outOfMainFragment =false;
            fm.beginTransaction().replace(R.id.content_fragment, main, MainFragment.TAG).commit();
        }

        else
            finish();
    }


}