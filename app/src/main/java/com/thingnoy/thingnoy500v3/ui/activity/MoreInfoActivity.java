package com.thingnoy.thingnoy500v3.ui.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thekhaeng.slidingmenu.lib.SlidingMenu;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.ui.fragment.MoreInfoFragment;

public class MoreInfoActivity extends AppCompatActivity {

    private SlidingMenu menu;
    private RecyclerView rvCart;
    private LinearLayout containerEmpty;
    private TextView tvTotalPrice;
    private Button btnConfirmOrder;
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

//        initInstance();

        NameAndImageDao dao = getIntent().getParcelableExtra("dao");

        if (savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.moreinfo_contentContainer, MoreInfoFragment.newInstance(dao))
                    .commit();
        }
    }

}
