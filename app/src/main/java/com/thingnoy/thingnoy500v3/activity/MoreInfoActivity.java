package com.thingnoy.thingnoy500v3.activity;

import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.stepstone.apprating.listener.RatingDialogListener;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.fragment.MoreInfoFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class MoreInfoActivity extends AppCompatActivity implements RatingDialogListener {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        initInstance();

        DataResProDao dao = getIntent().getParcelableExtra("dao");

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
    public void onPositiveButtonClicked(int star, String comment) {
        Toast.makeText(MoreInfoActivity.this, "Positive HANDLE: i = " + star + ", str = " + comment, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClicked() {
        Toast.makeText(MoreInfoActivity.this, "Negative HANDLE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNeutralButtonClicked() {
        Toast.makeText(MoreInfoActivity.this, "Neutral HANDLE", Toast.LENGTH_SHORT).show();
    }
}
