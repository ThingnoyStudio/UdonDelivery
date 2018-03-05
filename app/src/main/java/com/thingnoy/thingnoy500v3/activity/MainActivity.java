package com.thingnoy.thingnoy500v3.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.fragment.ResMainListFragment;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements ResMainListFragment.FragmentListener
//        implements MainFragment.FragmentListener
{

    DrawerLayout drawerLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toobar;
    private Call<PhotoItemCollectionDao> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.contentContainer, MainFragment.newInstance())
//                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, ResMainListFragment.newInstance())
                    .commit();

        }



    }

    private void init() {
        toobar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toobar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(NameAndImageDao nameAndImageDao) {
        Intent intent = new Intent(MainActivity.this,
                MoreInfoActivity.class);
        intent.putExtra("dao", nameAndImageDao);
        startActivity(intent);
    }

//    @Override
//    public void onPhotoItemClicked(DataResProDao dao) {
//// Mobile
//        Intent intent = new Intent(MainActivity.this,
//                MoreInfoActivity.class);
//        intent.putExtra("dao",dao);
//        startActivity(intent);
//    }
}
