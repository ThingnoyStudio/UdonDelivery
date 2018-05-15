package com.thingnoy.thingnoy500v3.ui.ordering.payment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.api.dao.PaymentDao;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectPaymentEvent;
import com.thingnoy.thingnoy500v3.util.Config;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.ShowToast;
import com.thingnoy.thingnoy500v3.util.StringUtils;
import com.thingnoy.thingnoy500v3.util.ViewAnimation;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


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
    public static final int COP = 2;
    public static final int COD = 1;
    private EditText edtCash;
    private EditText edtTotalPrice;
    private EditText edtChange;
    private double totalPrice = 0.0;
    private View lytHint;
    private Button btnPrice, btn1, btn5, btn10, btn20, btn50, btn100, btn200, btn500, btn1000;
    private SweetAlertDialog mDialog;
    private EditText edtPaypalCash;
    private EditText edtTotalPricePaypal;
    private Button btnPaypal;
    private View lytPaypal;
    private View lytHintPaypal;
    private MaterialRippleLayout mrPayPal;
    private boolean isPayPalShow = true;
//    private BraintreeFragment mBraintreeFragment;

    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIEN_ID);
    String address, comment;
    private static final int PAYPAL_REQUEST_CODE = 10100;


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


        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);


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

//        try {
//            mBraintreeFragment = BraintreeFragment.newInstance(getActivity(), Config.PAYPAL_CLIEN_ID);
//            // mBraintreeFragment is ready to use!
//            Log.e(TAG,"mBraintreeFragment>> : Useful");
//
//        } catch (InvalidArgumentException e) {
//            // There was an issue with your authorization string.
//            Log.e(TAG,"mBraintreeFragment>> InvalidArgumentException: "+e.getMessage());
//        }


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

        mrPayPal = rootView.findViewById(R.id.mr_paypal);
        edtPaypalCash = rootView.findViewById(R.id.edt_cash_paypal);
        edtTotalPricePaypal = rootView.findViewById(R.id.edt_total_price_paypal);
        btnPaypal = rootView.findViewById(R.id.btn_check_out_with_paypal);
        lytPaypal = rootView.findViewById(R.id.lyt_paypal);
        lytHintPaypal = rootView.findViewById(R.id.lyt_paypal_hint_cash_under_total_price);

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

        edtTotalPricePaypal.setText(StringUtils.getCommaPrice(getActivity(), totalPrice));
        edtPaypalCash.setText(StringUtils.getCommaPrice(getActivity(), totalPrice));
        mrPayPal.setOnClickListener(onClickPaypal());
        ivCashOnPayPal.setOnClickListener(onClickCOP());
        btnPaypal.setOnClickListener(onClickPayPay());


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDateil = confirmation.toJSONObject().toString(2);
                        JSONObject jsonObject = new JSONObject(paymentDateil);

                        String state = jsonObject.getJSONObject("response").getString("state");

                        Log.e(TAG,"State: "+new GetPrettyPrintJson().getJson(jsonObject));
                        //Request
//                        Request reQuest = new Request(
//                                Common.currentUser.getPhone(),
//                                Common.current.getName(),
//                                address,
//                                totalPrice+"",
//                                "0",
//                                comment,
//                                P
//                                )

                        // Submit to Firebase

                        Log.e(TAG,"ชำระบริการเรียบร้อยแล้ว state: "+state);
//                        new ShowToast().shortToast("ชำระบริการเรียบร้อยแล้ว ขอบคุณที่ใช้บริการค่ะ");

                        onPayPalCheckOut(state);

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }else if (resultCode == RESULT_CANCELED){
                new ShowToast().shortToast("การชำระเงินถูกยกเลิก");
                Log.e(TAG,"การชำระเงินถูกยกเลิก");
            }else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                new ShowToast().shortToast("การชำระเงินเกิดข้อผิดพลาด");
                Log.e(TAG,"การชำระเงินเกิดข้อผิดพลาด");
            }
        }
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

    private void togglePayPal(View bt, final View lyt) {
        boolean show = isPayPalShow;
        if (show) {
            isPayPalShow = false;
            ViewAnimation.expand(lyt, new ViewAnimation.AnimListener() {
                @Override
                public void onFinish() {
//                    Tools.nestedScrollTo(nested_scroll_view, lyt);
                }
            });
        } else {
            isPayPalShow = true;
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

    private void onPayPalCheckOut(String state) {
        if (state.equals("approved")){
            PaymentDao mPayment = new PaymentDao();
            mPayment.setPaymentType(COP);
            mPayment.setApproved(true);
            addPaymentToAddNewOrderBody(mPayment);
        }

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

    private void addPaymentToAddNewOrderBody(PaymentDao item) {
        RxBus.get().post(new SelectPaymentEvent(item));
        Log.e(TAG, "Pos>> addPaymentToAddNewOrderBody: " + new GetPrettyPrintJson().getJson(item));
    }

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

//                isPayPalShow = true;
                togglePayPal(v, lytPaypal);

            }
        };
    }

    private View.OnClickListener onClickPaypal() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePayPal(v, lytPaypal);
            }
        };
    }

    private View.OnClickListener onClickPayPay() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PayPal.authorizeAccount(mBraintreeFragment);

                address = "";
                comment = "";
                String foodPrice = edtPaypalCash.getText().toString()
                        .replace("฿","")
                        .replace(",","");

                PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(foodPrice),
                        "THB",
                        "UdonFoodDelivery",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(getActivity().getApplicationContext(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent,PAYPAL_REQUEST_CODE);

            }
        };
    }

//    PaymentMethodNonceCreatedListener d = new PaymentMethodNonceCreatedListener() {
//        @Override
//        public void onPaymentMethodNonceCreated(PaymentMethodNonce paymentMethodNonce) {
//            Log.e(TAG,"PaymentMethodNonceCreatedListener: "+new GetPrettyPrintJson().getJson(paymentMethodNonce));
//        }
//    };

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
