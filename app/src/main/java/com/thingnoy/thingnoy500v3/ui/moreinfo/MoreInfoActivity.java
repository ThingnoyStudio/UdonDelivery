package com.thingnoy.thingnoy500v3.ui.moreinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thekhaeng.slidingmenu.lib.SlidingMenu;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.fragment.MoreInfoFragment;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

public class MoreInfoActivity extends AppCompatActivity {
    private final static String TAG = MoreInfoActivity.class.getSimpleName();
    private FoodProductItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        NameAndImageDao dao = getIntent().getParcelableExtra("dao");
        FoodProductItem item = getIntent().getParcelableExtra("favoriteItem");

        if (savedInstanceState == null) {
            if (item != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.moreinfo_contentContainer, MoreInfoFragment.newInstance(dao, item))
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.moreinfo_contentContainer, MoreInfoFragment.newInstance(dao, new FoodProductItem()))
                        .commit();
            }
        }
    }

}
