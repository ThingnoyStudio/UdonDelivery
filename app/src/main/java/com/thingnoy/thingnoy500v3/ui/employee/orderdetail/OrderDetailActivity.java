package com.thingnoy.thingnoy500v3.ui.employee.orderdetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.google.android.gms.maps.model.LatLng;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.request.orderstate.OrderStateBody;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.ui.authen.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.employee.MainEmpActivity;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItem;
import com.thingnoy.thingnoy500v3.ui.employee.orderdetail.adapter.OrderDetailAdapter;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.ShowToast;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    private static final String TAG = OrderDetailActivity.class.getSimpleName();
    public static final String EXTRA_ORDER_DETAIL_ITEM = "extra_order_detail_item";
    private TextView tvAddress, tvTel, tvChange, tvPay, tvOrderState, tvFoodAmout, tvPaymentType, tvFullName, tvTotalPrice, tvDate;
    private OrderItem item;
    private ImageView btnBack;
    private OrderDetailAdapter orderDetailAdapter;
    private RecyclerView rcOrder;
    private MaterialSpinner spnOrderState;
    private ArrayAdapter adapter;
    private ArrayList<String> listState;
    private Button btnClose;
    private UdonFoodServiceManager serviceManager;
    private SweetAlertDialog mDialog;
    private Button btnNavigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        serviceManager = UdonFoodServiceManager.getInstance();

        setupInstance();
        bindView();
        setupView();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupInstance() {
        item = getIntent().getParcelableExtra(EXTRA_ORDER_DETAIL_ITEM);
        Log.e(TAG, "item: " + new GetPrettyPrintJson().getJson(item));
        if (item == null) {
            throw new NullPointerException("Item cannot null.");
        }
        setDataToAdapter();
    }


    private void bindView() {
        tvAddress = findViewById(R.id.tv_address);
        tvTel = findViewById(R.id.tv_tel);
        tvChange = findViewById(R.id.tv_change);
        tvPay = findViewById(R.id.tv_pay);
        tvOrderState = findViewById(R.id.tv_order_status);
        tvFoodAmout = findViewById(R.id.tv_food_amount);
        tvPaymentType = findViewById(R.id.tv_payment_type);
        tvFullName = findViewById(R.id.tv_full_name);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvDate = findViewById(R.id.tv_date);

        btnBack = findViewById(R.id.toolbar_back);
        rcOrder = findViewById(R.id.rv_order_detail);
        spnOrderState = findViewById(R.id.spn_order_state);
        btnClose = findViewById(R.id.btn_close);
        btnNavigate = findViewById(R.id.btn_navigate);
    }

    private void setupView() {

        setupDataToView();

        try {
            rcOrder.setHasFixedSize(true);
            rcOrder.setLayoutManager(new LinearLayoutManager(
                    getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rcOrder.setAdapter(orderDetailAdapter);
        } catch (Exception e) {
            Log.e(TAG, "SetupRecyclerView: " + e.getMessage());
            e.printStackTrace();
        }


        btnClose.setOnClickListener(onClickClose());
        btnBack.setOnClickListener(onClickBack());
        btnNavigate.setOnClickListener(onClickNavigate());

        setDataToSpinner();
        spnOrderState.setOnItemSelectedListener(onOrderStateSelectedListener());
    }


    @SuppressLint("SetTextI18n")
    private void setupDataToView() {
        try {
            tvDate.setText(item.getOrder().getOrderDate());
            tvTotalPrice.setText(item.getOrder().getOrderTotalPrice() + "฿");
            tvFullName.setText(item.getOrder().getCustomerFName() + " " + item.getOrder().getCustomerLName());
            tvPaymentType.setText(item.getOrder().getPaymentName());
            tvFoodAmout.setText(item.getFood().size() + " รายการ");
            tvOrderState.setText(item.getOrder().getOrderStatus());


            if (item.getOrder().getIDPaymant().equals("1")) {
                double total = Double.parseDouble(item.getOrder().getOrderTotalPrice());
                double pay;
                if (item.getOrder().getOrderpayprice() != null) {
                    tvPay.setText("เตรียมจะจ่าย " + item.getOrder().getOrderpayprice() + "฿");
                    pay = Double.parseDouble(item.getOrder().getOrderpayprice());
                } else {
                    tvPay.setText("เตรียมจะจ่าย 0.0฿");
                    pay = total;
                }

                double change = pay - total;
                if (change > 0) {
                    tvChange.setText("เงินทอน " + change + "฿");
                } else {
                    tvChange.setText("0.0฿");
                }

            } else {
                tvChange.setText("0.0฿");
            }

            tvTel.setText("โทร. " + item.getOrder().getCustomerPhone());

            if (item.getOrder().getCustomerAddNo() != null && item.getOrder().getCustomerAddRoad() != null) {
                tvAddress.setText("บ้านเลขที่ " + item.getOrder().getCustomerAddNo() + " " + item.getOrder().getCustomerAddRoad());
            } else {
                tvAddress.setText("เลือกจากแผนที่");
            }
        } catch (Exception e) {
            Log.e(TAG, "setupDataToView: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setDataToSpinner() {
        listState = new ArrayList<>();
        listState.add("สถานะจัดส่ง");
        listState.add("จัดส่งแล้ว");
        listState.add("ไม่พบผู้รับ");
        listState.add("กำลังจัดส่ง");

        adapter = new ArrayAdapter<>(
                Contextor.getInstance().getContext(),
                android.R.layout.simple_spinner_item,
                listState);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnOrderState.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void updateOrderStatus(String state) {
        tvOrderState.setText(state);
    }

    private void setDataToAdapter() {
        orderDetailAdapter = new OrderDetailAdapter(item.getBaseItems());
    }

    private void showProgressDialog(boolean isShow) {
        if (isShow) {
            mDialog = new SweetAlertDialog(OrderDetailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mDialog.setTitleText("กำลังเข้าสู่ระบบ");
            mDialog.setContentText("กรุณารอสักครู่...");
//        mDialog.setConfirmText("ตกลง");
            mDialog.setCancelable(false);
//        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismissWithAnimation();
//                goToLogin(body);
//            }
//        });

            mDialog.show();
        } else {
            mDialog.dismissWithAnimation();
        }

    }

    private void showErrorDialog(String content) {
        showProgressDialog(false);

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("เกิดข้อผิดพลาด");
        mDialog.setContentText(content);
        mDialog.setCancelable(false);
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });

        mDialog.show();
    }

    private void showSuccessDialog(String title, String content) {
        showProgressDialog(false);

        mDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText(title);
        mDialog.setContentText(content);
        mDialog.setConfirmText("ตกลง");
        mDialog.setCancelable(false);
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();

            }
        });

        mDialog.show();

    }

    /*
     * Event
     */

    private void requestAddOrderState(final String state, String idOrder) {
        showProgressDialog(true);
        OrderStateBody body = new OrderStateBody();
        body.setStatus(state);
        body.setIdorder(idOrder);
        serviceManager.requestAddOrderState(body, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                showSuccessDialog("เปลี่ยนสถานะการจัดส่ง", "เปลี่ยนสถานะการจัดส่งเป็น: " + state);
                Log.e(TAG, "requestAddOrderState>> Success: " + new GetPrettyPrintJson().getJson(result));
                updateOrderStatus(state);
            }

            @Override
            public void onFailure(Throwable t) {
                new ShowToast().shortToast("เปลี่ยนสถานะการสั่งซื้อล้มเหลว: " + t.getMessage());
                Log.e(TAG, "requestAddOrderState>> onFailure: " + t.getMessage());
            }
        });
    }

    private View.OnClickListener onClickBack() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

    private MaterialSpinner.OnItemSelectedListener onOrderStateSelectedListener() {
        return new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object data) {
                if (position != 0) {
                    if (!data.toString().equals(tvOrderState.getText().toString())) {
                        requestAddOrderState(data.toString(), item.getOrder().getIDOrder());
                    }
                }
            }
        };
    }

    private View.OnClickListener onClickClose() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

    private View.OnClickListener onClickNavigate() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getOrder().getMap() != null) {
                    LatLng latLng = getLatlng(item.getOrder().getMap());
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLng.latitude + "," + latLng.longitude + "&mode=d");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                } else {
                    showErrorDialog("ไม่พบตำแหน่งที่อยู่ของลูกค้า");
                }

            }
        };
    }

    private LatLng getLatlng(String map) {
        Log.e(TAG, "map: " + map);
        double lat = Double.parseDouble(map.split(",")[0]);
        double lng = Double.parseDouble(map.split(",")[1]);
        Log.e(TAG, "lat: " + lat);
        Log.e(TAG, "lng: " + lng);

        return new LatLng(lat, lng);
    }
}
