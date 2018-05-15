package com.thingnoy.thingnoy500v3.ui.ordering.main;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.thingnoy.thingnoy500v3.ui.ordering.main.adapter.OrderViewPagerAdapter;
import com.thingnoy.thingnoy500v3.ui.ordering.main.adapter.PagerAdapter;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.dao.PaymentDao;
import com.thingnoy.thingnoy500v3.api.request.AddNewOrderBody;
import com.thingnoy.thingnoy500v3.api.request.Address;
import com.thingnoy.thingnoy500v3.api.result.derivery_time.DataDeliveryTime;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.event.event_ordering.GetAddressFromMapEvent;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectAddressEvent;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectDeliveryTimeEvent;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectLocateAddressEvent;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectPaymentEvent;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.ordering.deliverytime.DeliveryTimeFragment;
import com.thingnoy.thingnoy500v3.ui.ordering.payment.PaymentFragment;
import com.thingnoy.thingnoy500v3.ui.ordering.address.AddressFragment;
import com.thingnoy.thingnoy500v3.ui.ordering.ordersuccess.DialogOrderSuccessFragment;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.thingnoy.thingnoy500v3.ui.ordering.payment.PaymentFragment.COP;
import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;

public class OrderingActivity extends AppCompatActivity implements DialogInterface.OnDismissListener {
    private static final String TAG = OrderingActivity.class.getSimpleName();
    public static final String EXTRA_PRODUCT_LIST = "extra_product_list";
    private static final String EXTRA_NAME_AND_IMAGE_DAO = "extra_name_and_image_dao";
    private static final String EXTRA_TOTAL_PRICE = "extra_total_price";

    public static final int REQ_ORDER = 1000;
    private static final int MAX_STEP = 3;

    private List<FoodProductItem> items;
    private CustomViewPager vpOrdering;
    private OrderViewPagerAdapter pagerAdapter;
    private ImageButton ibtnClose;
    private Toolbar toolbar;
    private TextView tvOrderingTitle;
    private TabLayout tabLayout;
    private CircleIndicator indicator;
    private Button btnCheckOut;

    private NameAndImageDao dao;
    private double totalPrice;
    private boolean paymentStatus = false;
    private Address address;
    private PaymentDao mPayment;
    private MaterialRippleLayout mRippleNext;
    private DataDeliveryTime deliveryTime;
    private int deliveryTimeId = -1;
    private MaterialRippleLayout mRippleBack;
    private int currentPagerPosition = 0;
    private UdonFoodServiceManager manager;
    private SweetAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering);

        manager = UdonFoodServiceManager.getInstance();

        setupInstance();
        bindView();
        setupView();
        setupViewPager();

        tvOrderingTitle.setText(pagerAdapter.getPageTitle(0));
        RxBus.get().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    public void setupInstance() {
        items = getIntent().getParcelableArrayListExtra(EXTRA_PRODUCT_LIST);
        Log.e(TAG, "Ordering getInstance: " + new GetPrettyPrintJson().getJson(items));
        dao = getIntent().getParcelableExtra(EXTRA_NAME_AND_IMAGE_DAO);
        totalPrice = getIntent().getDoubleExtra(EXTRA_TOTAL_PRICE, 0);

        if (items == null || dao == null) {
            throw new NullPointerException("You must send FoodProductItems or NameAndImageDao to this activity.");
        }
    }

    private void bindView() {
        tvOrderingTitle = findViewById(R.id.tv_ordering_title);
        toolbar = findViewById(R.id.toolbar_ordering);
        vpOrdering = findViewById(R.id.vpg_ordering_step);

        ibtnClose = findViewById(R.id.btn_close);
        indicator = findViewById(R.id.indicator);
        btnCheckOut = findViewById(R.id.btn_check_out);

        mRippleNext = findViewById(R.id.mr_next);
        mRippleBack = findViewById(R.id.mr_back);
    }

    private void setupView() {
        address = new Address();
        pagerAdapter = new OrderViewPagerAdapter(getSupportFragmentManager());
        vpOrdering.setAdapter(pagerAdapter);

        vpOrdering.setScrollEnable(false);

        vpOrdering.addOnPageChangeListener(onPageChangeListener());
        vpOrdering.setScrollEnable(false);

        ibtnClose.setOnClickListener(onClickClose());
        btnCheckOut.setOnClickListener(onClickCheckOut());

        mRippleNext.setOnClickListener(onRippleNextClick());
        mRippleBack.setOnClickListener(onRippleBackClick());

        indicator.setViewPager(vpOrdering);
    }

    private void onNext() {
        int current = vpOrdering.getCurrentItem() + 1;
        if (current < MAX_STEP) {
            // move to next screen
            vpOrdering.setCurrentItem(current, true);
        } else {
//            finish();
        }
    }

    private void setupViewPager() {
        setViewPager(vpOrdering);
    }

    private void setViewPager(ViewPager vp) {

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        pagerAdapter.addFragmentPage(AddressFragment.newInstance(),
                "เลือกที่อยู่");
        pagerAdapter.addFragmentPage(DeliveryTimeFragment.newInstance(),
                "เลือกเวลาจัดส่ง");
        pagerAdapter.addFragmentPage(PaymentFragment.newInstance(totalPrice),
                "ช่องทางการชำระเงิน");

        vp.setAdapter(pagerAdapter);
    }

    private void requestAddNewOrder(final AddNewOrderBody body) {
        manager.AddOrder(body, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {

                submitAction(body);


                Toast.makeText(getApplicationContext(), "เพิ่มรายการสั่งซื้อสำเร็จ : " + result.getData(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "requestAddNewOrder Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog(boolean isShow) {
        if (isShow) {
            mDialog = new SweetAlertDialog(OrderingActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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

    private void submitAction(final AddNewOrderBody body) {
        showProgressDialog(true);
//        progress_bar.setVisibility(View.VISIBLE);
//        show_dialog.setAlpha(0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showDialogPaymentSuccess(body);
                showProgressDialog(false);
//                progress_bar.setVisibility(View.GONE);
//                show_dialog.setAlpha(1f);
            }
        }, 1000);
    }

    private void showDialogPaymentSuccess(AddNewOrderBody body) {

        Log.e(TAG, "showDialogPaymentSuccess>> AddNewOrderBody: " + new GetPrettyPrintJson().getJson(body));

        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogOrderSuccessFragment dialog = DialogOrderSuccessFragment.newInstance(body);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, dialog).addToBackStack(null).commit();

    }



    /*
     * Event & Subscribe
     */

    @Subscribe
    public void onSelectUserAddress(SelectAddressEvent event) {
        if (event != null) {
            address.setmAddressId(event.getItem().getmIDCustomerAddress());
            address.setmAddressName(event.getItem().getCustomerAddNo() + " " + event.getItem().getCustomerAddRoad());
            address.setLatitude(0.0);
            address.setLongitude(0.0);

            Log.e(TAG, "Subscribe onSelectUserAddress: " + new GetPrettyPrintJson().getJson(address));
        }

    }

    @Subscribe
    public void onSelectLocateAddress(SelectLocateAddressEvent event) {
        if (event != null) {
            address.setmAddressId(0);
            address.setmAddressName("เลือกจากแผนที่");//todo: เลือกจากแผนที่
            address.setLatitude(event.getItem().latitude);
            address.setLongitude(event.getItem().longitude);

            Log.e(TAG, "Subscribe onSelectLocateAddress: " + new GetPrettyPrintJson().getJson(address));
        }

    }

    @Subscribe
    public void onSelectPayment(SelectPaymentEvent event) {
        if (event != null) {
            mPayment = new PaymentDao();
            mPayment.setApproved(event.getItem().isApproved());
            mPayment.setCash(event.getItem().getCash());
            mPayment.setPaymentType(event.getItem().getPaymentType());
            Log.e(TAG, "Subscribe onSelectPayment: " + new GetPrettyPrintJson().getJson(mPayment));

            if (mPayment.getPaymentType() == COP && mPayment.isApproved()){
                btnCheckOut.performClick();
            }


            if (event.getItem().getCash() < totalPrice || event.getItem().getCash() <= 0) {
                btnCheckOut.setVisibility(View.GONE);
            } else {
                if (event.getItem().getCash() >= totalPrice) {
                    btnCheckOut.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Subscribe
    public void onSelectDeliveryTime(SelectDeliveryTimeEvent event) {
        if (event != null) {
            this.deliveryTimeId = event.getItem().getIDDeliveryTime();

            deliveryTime = new DataDeliveryTime();
            deliveryTime.setIDDeliveryTime(event.getItem().getIDDeliveryTime());
            deliveryTime.setDeliveryTime(event.getItem().getDeliveryTime());

            Log.e(TAG, "Subscribe onSelectDeliveryTime: " + new GetPrettyPrintJson().getJson(event.getItem()));
        }
    }

    @Subscribe
    public void onGetAddressFromMap(GetAddressFromMapEvent event) {
        if (event != null) {
            onNext();
        }
    }


    private View.OnClickListener onClickCheckOut() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address != null && items != null) {
                    List<FoodProductItem> newListItems = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        FoodProductItem food = new FoodProductItem();
                        if (items.get(i).getReason() != null) {
                            food.setReason(items.get(i).getReason().isEmpty() ? "" : items.get(i).getReason());
                        }

                        if (items.get(i).getAddOn() != null) {
                            food.setAddOn(items.get(i).getAddOn().getIDFoodDetails().equals("-1") ? null : items.get(i).getAddOn());
                        } else {
//                            DetailFoodItem detailFoodItem = new DetailFoodItem();
//                            detailFoodItem.setIDFoodDetails("");
//                            detailFoodItem.setFoodDetailName("");
//                            detailFoodItem.setSelectedIndex(0);
//                            detailFoodItem.setFoodDetailsPrice(0);
                            food.setAddOn(null);
                        }

                        food.setmFoodName(items.get(i).getmFoodName());
                        food.setAmount(items.get(i).getAmount());
                        food.setmIDFood(items.get(i).getmIDFood());
                        food.setPrice(items.get(i).getPrice());
                        newListItems.add(i, food);
                    }

                    LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);

                    AddNewOrderBody addNewOrderBody = new AddNewOrderBody();
                    addNewOrderBody.setmItems(newListItems);
                    addNewOrderBody.setmCustomerId(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                    addNewOrderBody.setmPaymentStatus(paymentStatus);// TODO: paymentStat
                    addNewOrderBody.setmPaymentType(mPayment.getPaymentType());
                    addNewOrderBody.setmRestaurantId(dao.getResId());
                    addNewOrderBody.setmTotalPrice(totalPrice);
                    addNewOrderBody.setmPay(mPayment.getCash());
                    addNewOrderBody.setmDeliveryFee(dao.isDeliveryFee() ? 50.0 : 0);
                    addNewOrderBody.setmPromotionId(dao.getPromotionId());
                    addNewOrderBody.setAddress(address);
                    addNewOrderBody.setmDeliveryTimeId(deliveryTimeId);
                    addNewOrderBody.setmDeliveryTime(deliveryTime.getDeliveryTime());

                    Log.e(TAG, "AddNewOrderBody: " + new GetPrettyPrintJson().getJson(addNewOrderBody));

//                    submitAction(addNewOrderBody);

                    requestAddNewOrder(addNewOrderBody);

                }

            }
        };
    }

    private View.OnClickListener onClickClose() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
    }

    private ViewPager.OnPageChangeListener onPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageChangeListener position:" + position);

                tvOrderingTitle.setText(pagerAdapter.getPageTitle(position));

                if (position == pagerAdapter.getCount() - 1) {
//                    btnCheckOut.setVisibility(View.VISIBLE);
                    mRippleBack.setVisibility(View.VISIBLE);
                    mRippleNext.setVisibility(View.INVISIBLE);

                } else if (position == 0) {
                    mRippleNext.setVisibility(View.VISIBLE);
                    mRippleBack.setVisibility(View.INVISIBLE);
                    btnCheckOut.setVisibility(View.GONE);

                } else {
                    mRippleNext.setVisibility(View.VISIBLE);
                    mRippleBack.setVisibility(View.VISIBLE);
                    btnCheckOut.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentPagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    private View.OnClickListener onRippleNextClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPagerPosition == 0) {
                    if (address.getmAddressId() > 0) {
                        onNext();
                    } else {
                        Toast.makeText(getApplicationContext(), "กรุณาเลือกที่อยู่สำหรับการจัดส่ง", Toast.LENGTH_SHORT).show();
                    }
                }
                if (currentPagerPosition == 1) {
                    if (deliveryTimeId > 0) {
                        Log.e(TAG, "deliveryId:" + deliveryTimeId);
                        onNext();
                    } else {
                        Toast.makeText(getApplicationContext(), "กรุณาเลือกเวลาจัดส่ง", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        };
    }

    private View.OnClickListener onRippleBackClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = vpOrdering.getCurrentItem() - 1;

                if (current >= 0) {
                    // move to next screen
                    vpOrdering.setCurrentItem(current);
                } else {
//                    finish();
                }
            }
        };
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Log.e(TAG, "onDismiss");
        setResult(RESULT_OK);
        finish();

    }
}
