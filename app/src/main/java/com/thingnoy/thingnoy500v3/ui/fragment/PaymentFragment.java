package com.thingnoy.thingnoy500v3.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.hwangjr.rxbus.RxBus;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.api.dao.PaymentDao;
import com.thingnoy.thingnoy500v3.event_ordering.SelectPaymentEvent;
import com.thingnoy.thingnoy500v3.ui.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.ordersuccess.DialogOrderSuccessFragment;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.StringUtils;
import com.thingnoy.thingnoy500v3.util.ViewAnimation;

import java.util.List;


public class PaymentFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = PaymentFragment.class.getSimpleName();
    public static final String EXTRA_PRODUCT_LIST = "extra_product_list";
    public static final int REQ_ORDER = 1000;

    private View llGotoLocal;
    private List<FoodProductItem> items;
    private ImageView ivCashOnPayPal;
    private CheckBox cbCashOnDelivery;
    private MaterialRippleLayout mrDeliveryFee;
    private View lytPayment;
    private static final int COP = 2;
    private static final int COD = 1;
    private EditText edtCash;
    private EditText edtTotalPrice;
    private EditText edtChange;
    private double totalPrice = 0.0;
    private View lytHint;
    private Button btnPrice, btn1, btn5, btn10, btn20, btn50, btn100, btn200, btn500, btn1000;
    private SweetAlertDialog mDialog;


    public PaymentFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static PaymentFragment newInstance(double totalPrice) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();

        args.putDouble("totalPrice", totalPrice);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (getArguments() != null) {
            totalPrice = getArguments().getDouble("totalPrice");
        }

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);
        initInstances(rootView, savedInstanceState);
        RxBus.get().register(this);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        setupInstance();

        bindView(rootView);
        setupView();
    }

    private void bindView(View rootView) {
        cbCashOnDelivery = rootView.findViewById(R.id.cb_cash_on_delivery);
        ivCashOnPayPal = rootView.findViewById(R.id.iv_cash_on_paypal);
        lytPayment = rootView.findViewById(R.id.lyt_payment);
        mrDeliveryFee = rootView.findViewById(R.id.mr_del_fee);

        lytHint = rootView.findViewById(R.id.lyt_hint_cash_under_total_price);

        edtCash = rootView.findViewById(R.id.c_edt_cash);

        edtTotalPrice = rootView.findViewById(R.id.edt_total_price);
        edtChange = rootView.findViewById(R.id.edt_change);

        btnPrice = rootView.findViewById(R.id.btn_price);
        btn1 = rootView.findViewById(R.id.btn_1);
        btn5 = rootView.findViewById(R.id.btn_5);
        btn10 = rootView.findViewById(R.id.btn_10);
        btn20 = rootView.findViewById(R.id.btn_20);
        btn50 = rootView.findViewById(R.id.btn_50);
        btn100 = rootView.findViewById(R.id.btn_100);
        btn200 = rootView.findViewById(R.id.btn_200);
        btn500 = rootView.findViewById(R.id.btn_500);
        btn1000 = rootView.findViewById(R.id.btn_1000);
    }

    private void setupView() {
        mrDeliveryFee.setOnClickListener(onClickRipCOD());
        cbCashOnDelivery.setOnClickListener(onClickCheckBoxCOD());
        ivCashOnPayPal.setOnClickListener(onClickCOP());

        btnPrice.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn20.setOnClickListener(this);

        btn50.setOnClickListener(this);
        btn100.setOnClickListener(this);
        btn200.setOnClickListener(this);
        btn500.setOnClickListener(this);
        btn1000.setOnClickListener(this);

        edtTotalPrice.setText(StringUtils.getCommaPrice(getActivity(), totalPrice));
        btnPrice.setText(StringUtils.getCommaPrice(getActivity(), totalPrice));
        edtCash.addTextChangedListener(onTextChangeListener());

    }

    private void updateCash(View v) {
        Button btn = (Button) v;
        double cash = 0.0;
        if (!getCash().equals("")) {
            cash = StringUtils.getDoubleFromComma(getContext(), getCash());
        }

        double btnCash = StringUtils.getDoubleFromComma(getActivity(), btn.getText().toString().trim());
        double newCash = cash + btnCash;
        edtCash.setText(StringUtils.getCommaPrice(getActivity(), newCash));
    }

    private void checkCash(String s) {
        if (!s.equals("") && !s.isEmpty()) {
            double cash = StringUtils.getDoubleFromComma(getActivity(), s);
            if (cash < totalPrice) {
                lytHint.setVisibility(View.VISIBLE);
            } else {
                lytHint.setVisibility(View.GONE);
            }
        }

    }

    private void reCalChange(String s) {
        double cash = 0.0;
        if (!s.equals("") && !s.isEmpty()) {
            cash = StringUtils.getDoubleFromComma(getActivity(), s);
        }

        double chg = cash - totalPrice;
        edtChange.setText(StringUtils.getCommaPrice(getContext(), chg));
    }

    public void setupInstance() {
        items = getActivity().getIntent().getParcelableArrayListExtra(EXTRA_PRODUCT_LIST);
        if (items == null) {
            throw new NullPointerException("You must send FoodProductItems to this activity.");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        RxBus.get().unregister(this);
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    private void toggleSection(View bt, final View lyt) {
        boolean show = toggleArrow(bt);
        if (cbCashOnDelivery.isChecked()) {
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
//                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            ViewAnimation.collapse(lyt);
        }
    }

    private boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
//            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
//            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }

    private void addPaymentToAddNewOrderBody(PaymentDao item) {
        RxBus.get().post(new SelectPaymentEvent(item));
        Log.e(TAG,"Pos>> addPaymentToAddNewOrderBody: "+new GetPrettyPrintJson().getJson(item));
    }

    private String getCash() {
        return edtCash.getText().toString().trim();
    }

//    private void showErrorDialog(String content) {
//        mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
//        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        mDialog.setTitleText("กรุณาทำการเข้าสู่ระบบ");
//        mDialog.setContentText(""+content);
//        mDialog.setConfirmText("ไปยังหน้า Login");
//        mDialog.setCancelText("ยกเลิก");
//        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                mDialog.dismissWithAnimation();
//                startActivity(new Intent(getContext(),LoginActivity.class));
//            }
//        });
////        mDialog.setCancelable(false);
//
//        mDialog.show();
//    }

    private void showProgressDialog(boolean isShow) {
        if (isShow) {
            mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mDialog.setTitleText("กำลังทำรายการ");
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


    /*
     ************ Event & Subscribe
     */

    private TextWatcher onTextChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.equals("")){
//                    checkCash(s.toString());
//                }else {
//                    checkCash("0.0");
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    edtCash.setText("0.0");
                    checkCash(s.toString());
//                    reCalChange(s.toString());
                    edtChange.setText("0.0");

                } else {
                    checkCash(s.toString());
                    reCalChange(s.toString());

                    PaymentDao mPayment = new PaymentDao();
                    mPayment.setCash(StringUtils.getDoubleFromComma(getContext(), getCash()));
                    mPayment.setPaymentType(COD);
                    addPaymentToAddNewOrderBody(mPayment);

                }
            }
        };
    }

    private View.OnClickListener onClickRipCOD() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCashOnDelivery.isChecked()) {// is Check
                    cbCashOnDelivery.setChecked(false);

                    PaymentDao mPayment = new PaymentDao();
                    mPayment.setCash(-1);
                    mPayment.setPaymentType(-1);
                    addPaymentToAddNewOrderBody(mPayment);

                } else {// Not Check
                    cbCashOnDelivery.setChecked(true);

                    PaymentDao mPayment = new PaymentDao();
                    mPayment.setCash(-1);
                    mPayment.setPaymentType(-1);
                    addPaymentToAddNewOrderBody(mPayment);

                }

                toggleSection(v, lytPayment);
            }
        };
    }

    private View.OnClickListener onClickCheckBoxCOD() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbCashOnDelivery.isChecked()) {
                    PaymentDao mPayment = new PaymentDao();
                    mPayment.setCash(-1);
                    mPayment.setPaymentType(-1);
                    addPaymentToAddNewOrderBody(mPayment);
                }

                toggleSection(v, lytPayment);
            }
        };
    }

    private View.OnClickListener onClickCOP() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PaymentDao mPayment = new PaymentDao();
                mPayment.setCash(-1);
                mPayment.setPaymentType(COP);
                addPaymentToAddNewOrderBody(mPayment);

            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v == btnPrice) {
            updateCash(v);
        }
        if (v == btn1) {
            updateCash(v);
        }
        if (v == btn5) {
            updateCash(v);
        }
        if (v == btn10) {
            updateCash(v);
        }

        if (v == btn20) {
            updateCash(v);
        }
        if (v == btn50) {
            updateCash(v);
        }
        if (v == btn100) {
            updateCash(v);
        }
        if (v == btn200) {
            updateCash(v);
        }
        if (v == btn500) {
            updateCash(v);
        }
        if (v == btn1000) {
            updateCash(v);
        }
    }
}
