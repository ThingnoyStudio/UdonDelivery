package com.thingnoy.thingnoy500v3.ui.bottom.historydetail;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.ui.bottom.historydetail.adapter.HistoryDetailAdapter;
import com.thingnoy.thingnoy500v3.util.StringUtils;

public class HistoryDetailActivity extends AppCompatActivity {
    private final static String TAG = HistoryDetailActivity.class.getSimpleName();

    public static final String EXTRA_HISTORY_ITEM = "extra_item";
    private Button btnClose;
    private RecyclerView rcOrder;
    private TextView tvTotalPrice;
    private TextView tvDate;
    private Toolbar toolbar;
    private ImageView btnBack;
    private HistoryItem item;
    private HistoryDetailAdapter historyDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        bindView();
        setupInstance();
        setupView();
    }

    private void bindView() {
        btnClose = findViewById( R.id.btn_close );
        rcOrder = findViewById( R.id.rv_order );
        tvTotalPrice = findViewById( R.id.tv_total_price );
        tvDate = findViewById( R.id.tv_date );
        toolbar = findViewById( R.id.toolbar );
        btnBack = toolbar.findViewById( R.id.toolbar_back );
    }

    public void setupInstance(){
        item = getIntent().getParcelableExtra( EXTRA_HISTORY_ITEM );
        if( item == null ){
            throw new NullPointerException( "Item cannot null." );
        }
        historyDetailAdapter = new HistoryDetailAdapter( item.getBaseItems() );
    }

    private void setupView() {
        rcOrder.setHasFixedSize(true);
        rcOrder.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        rcOrder.setAdapter(historyDetailAdapter);
        btnClose.setOnClickListener( onClickBack() );
        btnBack.setOnClickListener( onClickBack() );

        setSupportActionBar( toolbar );
        ActionBar actionbar = getSupportActionBar();
        if( actionbar != null ){
            actionbar.setDisplayHomeAsUpEnabled( false );
            actionbar.setDisplayShowTitleEnabled( false );
        }

        tvDate.setText( item.getOrder().getOrderDate());
        tvTotalPrice.setText( StringUtils.getCommaPriceWithBaht( this, Double.parseDouble(item.getOrder().getOrderTotalPrice()) ) );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private View.OnClickListener onClickBack() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

}
