package com.thingnoy.thingnoy500v3.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andremion.counterfab.CounterFab;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.thekhaeng.slidingmenu.lib.SlidingMenu;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.CartAdapter;
import com.thingnoy.thingnoy500v3.adapter.PageAdapter;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.event.AddFoodToCartEvent;
import com.thingnoy.thingnoy500v3.event.ClearAddedButtonStateAllEvent;
import com.thingnoy.thingnoy500v3.event.ClearAddedButtonStateEvent;
import com.thingnoy.thingnoy500v3.event.GoToOrderDetailActivityEvent;
import com.thingnoy.thingnoy500v3.event.RemoveFoodFromCartEvent;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.ui.activity.MapsActivity;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

public class MoreInfoFragment extends Fragment {
    private final static String TAG = MoreInfoFragment.class.getSimpleName();
    private static final String EXTRA_PRODUCT_LIST_TEST = "extra_product_list_test";
    public static final int REQ_ORDER = 1000;

    public static final String KEY_FOOD_ITEM_IN_CART = "key_food_item_in_cart";
    public static final String EXTRA_PRODUCT_LIST = "extra_product_list";
    private final UdonFoodServiceManager serviceManager;
    private NameAndImageDao dao;
    private TabLayout tabLayout;
    private ViewPager viewPager2;
    private android.support.v7.widget.Toolbar toolbar;
    private ImageView image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button btnConfirmOrder;
    private TextView tvTotalPrice;
    private SlidingMenu menu;
    private RecyclerView rcCart;
    private View containerEmpty;
    private ImageView icCart;
    private TextView tvFoodAmount;
    private CartAdapter cartAdapter;



    private CounterFab fabCouter;
    private static Bundle mBundleRecyclerViewState;
    private TextView tvDeliveryFee;


    public MoreInfoFragment() {
        super();
        serviceManager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static MoreInfoFragment newInstance(NameAndImageDao dao) {//รับ dao
        MoreInfoFragment fragment = new MoreInfoFragment();
        Bundle args = new Bundle();

        //รับ dao
        args.putParcelable("dao", dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        assert getArguments() != null;
        dao = getArguments().getParcelable("dao");
        Logger.t(TAG).d(dao);
//        Toast.makeText(getActivity(),dao.getPromotionDao().get(0).getResPromotionEnd().toString(),Toast.LENGTH_SHORT).show();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more_info, container, false);
        bindView(rootView, savedInstanceState);
        setupInstance();
        setupView();
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

        setHasOptionsMenu(true);// ขออนุญาต สร้างเมนู

    }

    private void bindView(View rootView, Bundle savedInstanceState) {

        fabCouter = rootView.findViewById(R.id.counter_fab);
        icCart = rootView.findViewById(R.id.toolbar_cart);
        tvFoodAmount = rootView.findViewById(R.id.tv_product_count);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsing_toolbar);
        toolbar = rootView.findViewById(R.id.toolbar);
        image = rootView.findViewById(R.id.image);
        viewPager2 = rootView.findViewById(R.id.viewPager2);
        tabLayout = rootView.findViewById(R.id.tabLayout);

        menu = new SlidingMenu(Contextor.getInstance().getContext());
        menu.setMenu(R.layout.sliding_cart);
        rcCart = menu.getRootView().findViewById(R.id.rv_cart);
        containerEmpty = menu.findViewById(R.id.container_empty_cart);
        tvTotalPrice = menu.findViewById(R.id.tv_total_price);
        tvDeliveryFee = menu.findViewById(R.id.tv_delivery_fee);
        btnConfirmOrder = menu.findViewById(R.id.btn_confirm_order);


    }

    private void setupInstance() {
        cartAdapter = new CartAdapter();
        cartAdapter.setOnClickCartItem(onClickCartItem());
    }

    private void setupView() {
        setupToolbar();

        loadImageAppBar();
        setupViewPager();
        setupTabLayout();
        setupCart();
        updateAllCartView();
    }

    private void setupToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(false);
        }

        icCart.setOnClickListener(onClickCart());
        fabCouter.setOnClickListener(onClickCart());
        //set title bar
        collapsingToolbarLayout.setTitle(dao.getResName());
    }

    private void setupViewPager() {
        setUpViewPager(viewPager2);
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager2);
        setUpTabIcon();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
    }

    private void setUpTabIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_food);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_open);
    }

    private void setUpViewPager(ViewPager vp) {
        PageAdapter pagerAdapter = new PageAdapter(getActivity()
                .getSupportFragmentManager(), 2);
        pagerAdapter.addFragmentPage(FoodMenuFragment.newInstance(dao),
                "Foods");
        pagerAdapter.addFragmentPage(ResInfoFragment.newInstance(dao),
                "Restaurant");
        vp.setAdapter(pagerAdapter);
    }

    private void setupCart() {
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.elevation_sliding);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);

        btnConfirmOrder.setOnClickListener(onClickConfirmOrder());

//        int itemSpace = (int) getResources().getDimension( R.dimen.default_padding_margin );
        rcCart.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rcCart.addItemDecoration( new LinearLayoutMargin( itemSpace ) );
        rcCart.setAdapter(cartAdapter);
    }

    private void loadImageAppBar() {
        Glide.with(MoreInfoFragment.this)// โหลดรูป
                .load(dao.getResImage())// โหลดจาก url นี้
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_pic_loading)// กรณี กำลังโหลด
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
                .into(image);// โหลดเข้า imageView ตัวนี้
    }

    @Override
    public void onPause() {
        super.onPause();
        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Logger.addLogAdapter(new AndroidLogAdapter());
        List<BaseItem> items = cartAdapter.getItems();
        mBundleRecyclerViewState.putParcelableArrayList(KEY_FOOD_ITEM_IN_CART,
                (ArrayList<? extends Parcelable>) items);
        Log.e(TAG, "onPause: ");
        Logger.t(TAG).d(new GetPrettyPrintJson().getJson(items));
    }

    @Override
    public void onResume() {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Logger.addLogAdapter(new AndroidLogAdapter());
            List<BaseItem> items = mBundleRecyclerViewState.getParcelableArrayList(KEY_FOOD_ITEM_IN_CART);
            cartAdapter.setItems(items);
            updateAllCartView();
            Log.e(TAG, "onResume: ");
            Logger.t(TAG).d(new GetPrettyPrintJson().getJson(items));
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        RxBus.get().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        RxBus.get().unregister(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
//        List<BaseItem> items = cartAdapter.getItems();
//        outState.putParcelableArrayList(KEY_FOOD_ITEM_IN_CART,
//                (ArrayList<? extends Parcelable>) items);
//        Log.e("val","onSaveInstance: "+ items);
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
//        Log.e("val","onRestoreInstance: ");
//        List<BaseItem> items = savedInstanceState.getParcelableArrayList(KEY_FOOD_ITEM_IN_CART);
//        cartAdapter.setItems(items);
//        updateAllCartView();
//        Log.e("val","onRestoreInstance: "+ items);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.e(TAG, "onOptionsItemSelected: onBackPressed");
                clearOrder();
                if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getActivity().onBackPressed();
                    return true;
                } else {
                    getActivity().finish();
                    return true;
                }
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ORDER) {
            if (resultCode == RESULT_OK) {
                clearOrder();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void goToOrderDetailActivity(GoToOrderDetailActivityEvent event) {
//        Intent i = new Intent( this, OrderDetailActivity.class );
//        i.putExtra( EXTRA_HISTORY_ITEM, event.getItem() );
//        startActivity( i );
    }

    public void goToMapActivity(List<BaseItem> items) {
        Intent i = new Intent( getContext(), MapsActivity.class );
        i.putParcelableArrayListExtra( EXTRA_PRODUCT_LIST, (ArrayList<? extends Parcelable>) items );
        i.putExtra(EXTRA_PRODUCT_LIST_TEST,new Gson().toJson(items));
        startActivityForResult( i, REQ_ORDER );
    }

    public void onAddFoodToCartEvent(FoodProductItem item) {
        cartAdapter.addItem(item);
        updateAllCartView();
    }

    public void onRemoveFoodFromCartEvent(FoodProductItem item) {
        cartAdapter.removeItem(item);
        updateAllCartView();
    }

    private void clearOrder() {
        cartAdapter.removeAllItems();
        updateAllCartView();
        menu.showContent();
        clearAddedButtonStateAllEventP();
    }

    private void updateAllCartView() {
        // amount on cart
        updateBeerAmountIntoCartView();
        // total price on menu slide
        updateTotalPrice();
        // check or show empty page on menu slide
        updateEmptyCartView();
    }

    private void updateBeerAmountIntoCartView() {
        int amount = cartAdapter.getItemCount();
        tvFoodAmount.setVisibility(amount == 0 ? View.GONE : View.VISIBLE);
        tvFoodAmount.setText(String.valueOf(amount));
        fabCouter.setCount(amount);
    }

    public boolean hasItems() {
        return cartAdapter.hasItems();
    }

    private void showProductItemView() {
        containerEmpty.setVisibility(GONE);
        btnConfirmOrder.setEnabled(true);
        btnConfirmOrder.setBackgroundResource(R.drawable.btn_active_selector);
    }

    private void hindProductItemView() {
        containerEmpty.setVisibility(View.VISIBLE);
        btnConfirmOrder.setEnabled(false);
        btnConfirmOrder.setBackgroundResource(R.drawable.btn_inactive);
    }

    @SuppressLint("SetTextI18n")
    private void updateTotalPrice() {
        tvDeliveryFee.setText(StringUtils.getCommaPrice(getActivity(),
                dao.isDeliveryFee() ? 50.0 : 0.00));
        tvTotalPrice.setText(StringUtils.getCommaPriceWithBaht(getActivity(),
                cartAdapter.getTotalPrice() + Double.valueOf(tvDeliveryFee.getText().toString())));
    }

    /****************
     * Event Click
     ***************/
    private CartAdapter.OnClickCartItemListener onClickCartItem() {
        return new CartAdapter.OnClickCartItemListener() {
            @Override
            public void onClickIncrease(FoodProductItem item, int position) {
                updateTotalPrice();
            }

            @Override
            public void onClickDecrease(FoodProductItem item, int position) {
                updateTotalPrice();
            }

            @Override
            public void onClickDelete(FoodProductItem item, int position) {
                onRemoveFoodFromCartEvent(item);
                clearAddedButtonStateEventP(item);
                updateTotalPrice();
            }

            @Override
            public void onAddOnSelected(FoodProductItem item, int position) {
                Log.e(TAG, "onSelectedAddOn : " + position);
                updateTotalPrice();
            }
        };
    }

    private View.OnClickListener onClickConfirmOrder() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMapActivity(cartAdapter.getItems());
            }
        };
    }

    private View.OnClickListener onClickCart() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        };
    }


    /******************
     * Presenter
     ****************/

//    public void goToMapActivityP(List<BaseItem> items) {
//        goToMapActivity(items);
//    }

    public void clearAddedButtonStateEventP(FoodProductItem item) {
        Log.e(TAG, "clearAddedButtonStateEventP>> post!!");
        RxBus.get().post(new ClearAddedButtonStateEvent(item));
    }

    public void clearAddedButtonStateAllEventP() {
        Log.e(TAG, "clearAddedButtonStateAllEventP>> post!!");
        RxBus.get().post(new ClearAddedButtonStateAllEvent());
    }

    @Subscribe
    public void onGoToOrderDetailActivity(GoToOrderDetailActivityEvent event) {
        Log.e(TAG, "@Subscribe>> onGoToOrderDetailActivity!");
        goToOrderDetailActivity(event);
    }

    @Subscribe
    public void onAddFoodToCartEvent(AddFoodToCartEvent event) {
        Log.e(TAG, "@Subscribe>> onAddFoodToCartEvent!");
        event.getItem().clearAmount();
        onAddFoodToCartEvent(event.getItem());
    }

    @Subscribe
    public void onRemoveFoodFromCartEvent(RemoveFoodFromCartEvent event) {
        Log.e(TAG, "@Subscribe>> onRemoveFoodFromCartEvent!");
        onRemoveFoodFromCartEvent(event.getItem());
    }

    public void updateEmptyCartView() {
        if (hasItems()) {
            showProductItemView();
        } else {
            hindProductItemView();
        }
    }

}
