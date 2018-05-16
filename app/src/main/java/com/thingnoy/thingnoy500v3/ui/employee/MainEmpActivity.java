package com.thingnoy.thingnoy500v3.ui.employee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.event.event_main.GoToEmpOrderDetailEvent;
import com.thingnoy.thingnoy500v3.ui.bottom.profile.ProfileFragment;
import com.thingnoy.thingnoy500v3.ui.employee.order.OrderFragment;
import com.thingnoy.thingnoy500v3.ui.employee.orderdetail.OrderDetailActivity;
import com.thingnoy.thingnoy500v3.ui.main.home.MainActivity;
import com.thingnoy.thingnoy500v3.util.BottomNavigationBehavior;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import static com.thingnoy.thingnoy500v3.ui.employee.orderdetail.OrderDetailActivity.EXTRA_ORDER_DETAIL_ITEM;

public class MainEmpActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    private ImageView ivLogo;
    private TextView tvTitle;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_emp);
        RxBus.get().register(this);

        setupToolbar();
        bindView();
        setupView();

        if (savedInstanceState == null) {
            loadFragment(OrderFragment.newInstance());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }


    private void bindView() {
        ivLogo = findViewById(R.id.iv_logo);
        tvTitle = findViewById(R.id.tv_title);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavClick());
    }

    private void setupView() {
        tvTitle.setText("รายการนำส่งอาหาร");

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
        bottomNavigationView.setSelectedItemId(R.id.item_history);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onBottomNavClick() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_history:
                        tvTitle.setText("รายการนำสั่ง");
                        ivLogo.setImageResource(R.drawable.ic_history_thick);
                        loadFragment(OrderFragment.newInstance());
                        return true;
                    case R.id.item_me:
                        tvTitle.setText("ข้อมูลโปรไฟล์");
                        ivLogo.setImageResource(R.drawable.ic_user);
                        loadFragment(ProfileFragment.newInstance());
                        return true;
                }
                return false;
            }
        };
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main_employee, fragment)
                .commit();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(false);
            actionbar.setDisplayShowTitleEnabled(false);
        }
//        toolbar.setTitle("รายการนำส่งอาหาร");
    }

    @Subscribe
    public void goToOrderDetailActivity(GoToEmpOrderDetailEvent event) {
        Log.e(TAG, "goToOrderDetailActivity" + new GetPrettyPrintJson().getJson(event.getItem()));
        Intent i = new Intent(MainEmpActivity.this, OrderDetailActivity.class);
        i.putExtra(EXTRA_ORDER_DETAIL_ITEM, event.getItem());
        startActivity(i);
    }

}
