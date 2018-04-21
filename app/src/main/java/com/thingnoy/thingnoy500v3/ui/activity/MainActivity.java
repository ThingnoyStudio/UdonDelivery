package com.thingnoy.thingnoy500v3.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.CartAdapter;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.ui.fragment.ResMainListFragment;
import com.thingnoy.thingnoy500v3.util.BottomNavigationBehavior;

import java.util.List;

import retrofit2.Call;

import static com.thingnoy.thingnoy500v3.R.drawable.ic_cart_new;

public class MainActivity extends AppCompatActivity implements ResMainListFragment.FragmentListener
//        implements MainFragment.FragmentListener
{
    public static final String KEY_FOOD_ITEM_IN_CART = "key_food_item_in_cart";
    DrawerLayout drawerLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private Call<PhotoItemCollectionDao> call;
    private BottomNavigationView bottomNavigationView;
    private TextView tvFoodAmount;
    private CartAdapter cartAdapter;
    private TextView tvTitle;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
        setupView();
        setupToolbar();
        setupBottomNav();

        if (savedInstanceState == null) {
            loadFragment(ResMainListFragment.newInstance());
        }

    }

    private void setupView() {
//        cartAdapter = new CartAdapter();
//        List<BaseItem> items = Hawk.get(KEY_FOOD_ITEM_IN_CART);
//        if (items != null){
//            cartAdapter.setItems(items);
//            updateAllCartView();
//        }

    }

    private void bindView() {
        toolbar = findViewById(R.id.toolBar);
        ivLogo = findViewById(R.id.iv_logo);
        tvTitle = findViewById(R.id.tv_title);
//        tvFoodAmount = findViewById(R.id.tv_product_count);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavClick());
    }

    private void setupBottomNav() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        bottomNavigationView.setSelectedItemId(R.id.item_home);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onBottomNavClick() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {

                    case R.id.item_nearby:
                        // do this event
                        tvTitle.setText("nearby");
                        ivLogo.setImageResource(R.drawable.ic_nearby);
                        Toast.makeText(MainActivity.this, "nearby selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.item_history:
                        // do this event
                        tvTitle.setText("History");
                        ivLogo.setImageResource(R.drawable.ic_history_thick);
                        Toast.makeText(MainActivity.this, "history selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_home:
                        // do this event
                        tvTitle.setText("Udon Food Delivery");
                        ivLogo.setImageResource(R.drawable.ic_home);
                        loadFragment(ResMainListFragment.newInstance());
//                        Toast.makeText(MainActivity.this, "home selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_favorite:
                        // do this event
                        tvTitle.setText("Favorite");
                        ivLogo.setImageResource(R.drawable.ic_like);
                        Toast.makeText(MainActivity.this, "favorite selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_me:
                        // do this event
                        tvTitle.setText("Me");
                        ivLogo.setImageResource(R.drawable.ic_user);
                        Toast.makeText(MainActivity.this, "me selected", Toast.LENGTH_SHORT).show();
                        return true;

                }
                return false;
            }
        };
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(false);
            actionbar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("udon");
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemClicked(NameAndImageDao nameAndImageDao) {
        Intent intent = new Intent(MainActivity.this,
                MoreInfoActivity.class);
        intent.putExtra("dao", nameAndImageDao);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("ยืนยันการออก?")
                .setMessage("ต้องการออกจาก Udon Delivery หรือไม่?")
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();

//                        {
//
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        WelcomeActivity.super.onBackPressed();
//                    }
//    }).create().show();
    }

    private void updateAllCartView() {
        updateBeerAmountIntoCartView();
//        updateTotalPrice();
//        updateEmptyCartView();
    }
    private void updateBeerAmountIntoCartView() {
        int amount = cartAdapter.getItemCount();
        tvFoodAmount.setVisibility(amount == 0 ? View.GONE : View.VISIBLE);
        tvFoodAmount.setText(String.valueOf(amount));
//        fabCouter.setCount(amount);
    }
}
