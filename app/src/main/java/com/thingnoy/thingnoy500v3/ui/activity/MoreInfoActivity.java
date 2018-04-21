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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

//        initInstance();

        NameAndImageDao dao = getIntent().getParcelableExtra("dao");

        if (savedInstanceState == null){
//            Toast.makeText(MoreInfoActivity.this,"getResPromotionName : "+dao.getPromotionDao().get(0).getResPromotionName(),Toast.LENGTH_SHORT).show();
//            try {
//                theDate = dateFormat.parse(dao.getPromotionDao().get(0).getResPromotionStart().toString());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Toast.makeText(MoreInfoActivity.this,"getResPromotionStart : "+output,Toast.LENGTH_SHORT).show();
//            Toast.makeText(MoreInfoActivity.this,"getResPromotionEnd : "+dao.getPromotionDao().get(0).getResPromotionEnd(),Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.moreinfo_contentContainer, MoreInfoFragment.newInstance(dao))
                    .commit();
        }
    }

//    private void initInstance() {
////        getSupportActionBar().setHomeButtonEnabled(true);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    }

//    private void setupToolbar() {
////        setSupportActionBar( toolbar );
////        ActionBar actionbar = getSupportActionBar();
////        if( actionbar != null ){
////            actionbar.setDisplayHomeAsUpEnabled( false );
////            actionbar.setDisplayShowTitleEnabled( false );
////        }
////
////        icCart.setOnClickListener( onClickCart() );
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public void onClick(View v) {
//    }
}
