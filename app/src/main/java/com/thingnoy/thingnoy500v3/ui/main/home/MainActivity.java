package com.thingnoy.thingnoy500v3.ui.main.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.event.event_main.GoToOrderDetailActivityEvent;
import com.thingnoy.thingnoy500v3.ui.main.restaurant.ResMainListFragment;
import com.thingnoy.thingnoy500v3.ui.moreinfo.MoreInfoActivity;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.FavoriteFragment;
import com.thingnoy.thingnoy500v3.ui.bottom.history.HistoryFragment;
import com.thingnoy.thingnoy500v3.ui.bottom.historydetail.HistoryDetailActivity;
import com.thingnoy.thingnoy500v3.ui.bottom.profile.ProfileFragment;
import com.thingnoy.thingnoy500v3.util.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity implements ResMainListFragment.FragmentListener{
//        implements MainFragment.FragmentListener

    public static final String EXTRA_HISTORY_ITEM = "extra_item";



    Toolbar toolbar;

    private BottomNavigationView bottomNavigationView;

    private TextView tvTitle;
    private ImageView ivLogo;
    private ResMainListFragment resMainListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RxBus.get().register(this);

        bindView();
        setupView();
        setupToolbar();
        setupBottomNav();

//        if (savedInstanceState == null) {
//            loadFragment(ResMainListFragment.newInstance());
//        }

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            resMainListFragment = ResMainListFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, resMainListFragment)
                    .commit();
        }
//        else {
//            // Or set the fragment from restored state info
//            resMainListFragment = (ResMainListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.contentContainer);
//        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);

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

//                    case R.id.item_nearby:
//                        // do this event
//                        tvTitle.setText(R.string.menu_nearby);
//                        ivLogo.setImageResource(R.drawable.ic_nearby);
////                        Toast.makeText(MainActivity.this, "nearby selected", Toast.LENGTH_SHORT).show();
//                        return true;

                    case R.id.item_history:
                        // do this event
                        tvTitle.setText(R.string.history);
                        ivLogo.setImageResource(R.drawable.ic_history_thick);
                        loadFragment(HistoryFragment.newInstance());
//                        Toast.makeText(MainActivity.this, "history selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_home:
                        // do this event
                        tvTitle.setText(R.string.app_name2);
                        ivLogo.setImageResource(R.drawable.ic_home);
                        loadFragment(ResMainListFragment.newInstance());
//                        Toast.makeText(MainActivity.this, "home selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_favorite:
                        // do this event
                        tvTitle.setText(R.string.menu_favorite);
                        ivLogo.setImageResource(R.drawable.ic_like);
                        loadFragment(FavoriteFragment.newInstance());
//                        Toast.makeText(MainActivity.this, "favorite selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_me:
                        // do this event
                        tvTitle.setText("ข้อมูลโปรไฟล์");
                        ivLogo.setImageResource(R.drawable.ic_user);
                        loadFragment(ProfileFragment.newInstance());
//                        Toast.makeText(MainActivity.this, "me selected", Toast.LENGTH_SHORT).show();
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
//        transaction.addToBackStack(null);
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

    @Subscribe
    public void goToOrderDetailActivity(GoToOrderDetailActivityEvent event) {
        Intent i = new Intent(this, HistoryDetailActivity.class);
        i.putExtra(EXTRA_HISTORY_ITEM, event.getItem());
        startActivity(i);
    }
}
