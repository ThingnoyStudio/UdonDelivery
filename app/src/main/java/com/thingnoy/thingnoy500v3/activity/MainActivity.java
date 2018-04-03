package com.thingnoy.thingnoy500v3.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.fragment.ResMainListFragment;
import com.thingnoy.thingnoy500v3.util.BottomNavigationBehavior;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements ResMainListFragment.FragmentListener
//        implements MainFragment.FragmentListener
{

    DrawerLayout drawerLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    private Call<PhotoItemCollectionDao> call;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setupToolbar();
        setubBottomNav();

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer, MainFragment.newInstance())
//                    .commit();

            loadFragment(new ResMainListFragment());
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer, ResMainListFragment.newInstance())
//                    .commit();

        }


    }

    private void init() {
        toolbar = findViewById(R.id.toolBar);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavClick());

    }

    private void setubBottomNav() {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        bottomNavigationView.setSelectedItemId(R.id.item_home);
//        bottomNavigationView.inflateMenu(R.menu.menu_bottom);
//        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
//        bottomNavigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color));
//        bottomNavigationView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bottom_nav_item_color));
//        bottomNavigationView.setOnNavigationItemSelectedListener( onClickBottomNav() );
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
                        toolbar.setTitle("nearby");
                        Toast.makeText(MainActivity.this, "nearby selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.item_history:
                        // do this event
                        toolbar.setTitle("History");
                        Toast.makeText(MainActivity.this, "history selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_home:
                        // do this event
                        toolbar.setTitle("udon");
                        Toast.makeText(MainActivity.this, "home selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_favorite:
                        // do this event
                        toolbar.setTitle("Favorite");
                        Toast.makeText(MainActivity.this, "favorite selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item_me:
                        // do this event
                        toolbar.setTitle("Me");
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

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        actionBarDrawerToggle.syncState();
    }

//        @Override
//        public void onConfigurationChanged (Configuration newConfig){
//            super.onConfigurationChanged(newConfig);
////        actionBarDrawerToggle.onConfigurationChanged(newConfig);
//        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onPhotoItemClicked(DataResProDao dao) {
//// Mobile
//        Intent intent = new Intent(MainActivity.this,
//                MoreInfoActivity.class);
//        intent.putExtra("dao",dao);
//        startActivity(intent);
//    }

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
}
