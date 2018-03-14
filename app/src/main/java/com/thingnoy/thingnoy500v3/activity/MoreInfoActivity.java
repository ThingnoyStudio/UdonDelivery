package com.thingnoy.thingnoy500v3.activity;

import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stepstone.apprating.listener.RatingDialogListener;
import com.thekhaeng.slidingmenu.lib.SlidingMenu;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.fragment.MoreInfoFragment;
import com.thingnoy.thingnoy500v3.fragment.ResInfoFragment;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class MoreInfoActivity extends AppCompatActivity implements View.OnClickListener {

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

        initInstance();

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

    private void initInstance() {
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupToolbar() {
//        setSupportActionBar( toolbar );
//        ActionBar actionbar = getSupportActionBar();
//        if( actionbar != null ){
//            actionbar.setDisplayHomeAsUpEnabled( false );
//            actionbar.setDisplayShowTitleEnabled( false );
//        }
//
//        icCart.setOnClickListener( onClickCart() );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    }
}
