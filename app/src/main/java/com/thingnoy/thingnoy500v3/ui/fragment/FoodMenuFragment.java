package com.thingnoy.thingnoy500v3.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.FoodMenuConverter;
import com.thingnoy.thingnoy500v3.adapter.FoodProductAdapter;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItemGroup;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.event.AddFoodToCartEvent;
import com.thingnoy.thingnoy500v3.event.ClearAddedButtonStateAllEvent;
import com.thingnoy.thingnoy500v3.event.ClearAddedButtonStateEvent;
import com.thingnoy.thingnoy500v3.event.RemoveFoodFromCartEvent;
import com.thingnoy.thingnoy500v3.util.ItemAnimation;

import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class FoodMenuFragment extends Fragment {
    private final static String TAG = FoodMenuFragment.class.getSimpleName();
//    public static final int REQ_ORDER = 1000;
    public static final String KEY_BEER_GROUP = "key_beer_group";
//    public static final String KEY_FOOD_ITEM_IN_MENU = "key_food_item_in_menu";
    private final UdonFoodServiceManager serviceManager;
    private NameAndImageDao dao;
    private RecyclerView rcFood;
    private FoodProductAdapter foodAdapter;
    private View containerEmpty;
    private View containerServiceUnavailable;
    private boolean isHasItem;
    private CardView containerProgressbar;
    private FoodProductItemGroup itemGroup;

    public FoodMenuFragment() {
        super();
        serviceManager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static FoodMenuFragment newInstance(NameAndImageDao dao) {
        FoodMenuFragment fragment = new FoodMenuFragment();
        Bundle args = new Bundle();

        args.putParcelable("dao", dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        init(savedInstanceState);

        assert getArguments() != null;
        dao = getArguments().getParcelable("dao");

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e(TAG, "onViewCreated");
        RxBus.get().register(this);

        if (savedInstanceState == null) {
            initialize();
        } else {
            restoreView(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_food_menu, container, false);
        bindView(rootView);
        setupInstance();
        initInstances(rootView, savedInstanceState);
        setupView();
//        callGetMenuById();
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressLint("SetTextI18n")
    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

//        ivImg = rootView.findViewById(R.id.ivImg);
//        tvName = rootView.findViewById(R.id.tvName);
//        tvDescription = rootView.findViewById(R.id.tvDescription);
//
//        tvName.setText(dao.getRestaurantNameDao().getResName());
//
//        if (dao.getPromotionDao() != null) {
//            tvDescription.setText("โปรโมชั่น : " + dao.getPromotionDao().get(0).getResPromotionName() +
//                    "\n สิ้นสุดโปรโมชั่น : " + dao.getPromotionDao().get(0).getResPromotionEnd());
//        }else {
//            tvDescription.setText("ร้านนี้ไม่มีโปรโมชั่น "+ Constant.FACESAD);
//        }
////        tvDescription.setText(dao.getUsername()+"\n"+dao.getCamera());
//
//        Glide.with(FoodMenuFragment.this)// โหลดรูป
//                .load(dao.getRestaurantNameDao().getResImg())// โหลดจาก url นี้
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.loading)// กรณี กำลังโหลด
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
//                .into(ivImg);// โหลดเข้า imageView ตัวนี้
    }

    private void bindView(View rootView) {
        rcFood = rootView.findViewById(R.id.rc_food_order);
        containerEmpty = rootView.findViewById(R.id.container_empty);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        containerProgressbar = rootView.findViewById(R.id.container_progressbar);
    }

    private void setupInstance() {
        foodAdapter = new FoodProductAdapter();
        foodAdapter.setOnClickProductItem(onClickProduct());
    }

    private void setupView() {
        rcFood.setLayoutManager(new StaggeredGridLayoutManager(2, VERTICAL));
        rcFood.setItemAnimator(new DefaultItemAnimator());
        rcFood.setAdapter(foodAdapter);

        containerEmpty.setVisibility(View.GONE);

        Log.e(TAG, "setupView");
        containerProgressbar.setVisibility(View.VISIBLE);


//        topRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
//        snapHelperTop.attachToRecyclerView(rcFood);
    }

    private void initialize() {
//        foodAdapter.initDefaultItemForLoadmore();
//        callGetMenuById();
//        requestFoodMenuO(dao.getResId());
        requestFoodMenu(dao.getResId());
        onClearAddedButtonAllStateEvent();
    }

    private void restoreView(Bundle savedInstanceState) {
        Log.e(TAG, "restoreView");
        setFoodProductToAdapter(getFoodItemGroup());
//        if (savedInstanceState )
        containerProgressbar.setVisibility(View.GONE);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        Log.e(TAG, "onSaveInstanceState");
        outState.putParcelable(KEY_BEER_GROUP, getFoodItemGroup());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        Log.e(TAG, "onRestoreInstanceState");
        setFoodItemGroup((FoodProductItemGroup) savedInstanceState.getParcelable(KEY_BEER_GROUP));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    public void setFoodProductItemToAdapter(List<BaseItem> foodItemFromResult) {
        foodAdapter.setItems(foodItemFromResult, ItemAnimation.BOTTOM_UP);
    }

    public void onClearAddedButtonStateEvent(FoodProductItem item) {
        foodAdapter.clearAddedState(item);
    }

    public void onClearAddedButtonAllStateEvent() {
        foodAdapter.clearAddedStateAll();
        rcFood.smoothScrollToPosition(0);
    }

//    public List<BaseItem> getItemsFromAdapter() {
//        return foodAdapter.getItems();
//    }

    private FoodProductAdapter.OnClickFoodProductListener onClickProduct() {
        return new FoodProductAdapter.OnClickFoodProductListener() {
            @Override
            public void onClickLike(FoodProductItem item, int position) {
                showToast("onClickLike: " + position);
            }

            @Override
            public void onClickUnLike(FoodProductItem item, int position) {
                showToast("onClickUnLike: " + position);
            }

            @Override
            public void onClickItem(FoodProductItem item, int position) {
//                showToast("onClickItem: " + position);
//                Log.e(TAG,"onClickItem !!");
            }

            @Override
            public void onClickAddToCart(FoodProductItem item, int position) {
                showToast("เพิ่มสินค้าลงในตะกร้าแล้ว");
                Log.e(TAG,"onClickAddToCart !!");
                addFoodItemToCart(item);
            }

            @Override
            public void onClickAdded(FoodProductItem item, int position) {
                showToast("นำสินค้าออกจากตะกร้าแล้ว");
                Log.e(TAG,"onClickAdded !!");
                deleteFoodItemFromCart(item, position);
            }
        };
    }

    /******************
     * Presenter
     ****************/
    public FoodProductItemGroup getFoodItemGroup() {
        return itemGroup;
    }

    public void setFoodItemGroup(FoodProductItemGroup items) {
        Log.e(TAG, "setFoodItemGroup");
        this.itemGroup = items;
    }

    public void setFoodProductToAdapter(FoodProductItemGroup group) {
        setFoodProductItemToAdapter(group.getFoods());
    }

    public void addFoodItemToCart(FoodProductItem item) {
        Log.e(TAG,"addFoodItemToCart>> post!!");
        RxBus.get().post(new AddFoodToCartEvent(item));
    }

    public void deleteFoodItemFromCart(FoodProductItem item, int position) {
        Log.e(TAG,"deleteFoodItemFromCart>> post!!");
        RxBus.get().post(new RemoveFoodFromCartEvent(item));
    }

    @Subscribe
    public void onClearAddedButtonStateEvent(ClearAddedButtonStateEvent event) {
        Log.e(TAG,"@Subscribe>> onClearAddedButtonStateEvent!!");
        onClearAddedButtonStateEvent(event.getItem());
    }

    @Subscribe
    public void onClearAddedButtonStateAllEvent(ClearAddedButtonStateAllEvent event) {
        Log.e(TAG,"@Subscribe>> onClearAddedButtonStateAllEvent!!");
        onClearAddedButtonAllStateEvent();
    }

    /**************
     * Function
     **************/

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

//    private void requestFoodMenuO(int restaurantId){
//        serviceManager.requestFoodMenuO(restaurantId, new UdonFoodServiceManager.UdonFoodManagerCallback<FoodMenuResultGroupO>() {
//            @Override
//            public void onSuccess(FoodMenuResultGroupO result) {
//                containerProgressbar.setVisibility(View.GONE);
//                isHasItem = true;
//
////                itemGroup = convertFoodProduct(result);
////                setFoodProductToAdapter(itemGroup);
////                updateEmptyCartView();
//
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
////                containerProgressbar.setVisibility(View.GONE);
////                isHasItem = false;
////                itemGroup = null;
////
////                showServiceUnavailableView();
//            }
//        });
//    }

    private void requestFoodMenu(int id){
        serviceManager.requestFoodMenu(id, new UdonFoodServiceManager.UdonFoodManagerCallback<FoodMenuResultGroup>() {
            @Override
            public void onSuccess(FoodMenuResultGroup result) {
                Log.e(TAG,new Gson().toJson(result));

                containerProgressbar.setVisibility(View.GONE);
                isHasItem = true;

                itemGroup = convertFoodMenu(result);
                setFoodProductToAdapter(itemGroup);
                updateEmptyCartView();

            }

            @Override
            public void onFailure(Throwable t) {
                showToast("requestMenu: failure");
                containerProgressbar.setVisibility(View.GONE);
                isHasItem = false;
                itemGroup = null;

                showServiceUnavailableView();
            }
        });
    }

    private FoodProductItemGroup convertFoodMenu(FoodMenuResultGroup result) {
        String normalMenuTitle = " เมนูอร่อย ";
        String recommendedMenuTitle = " เมนูแนะนำ ";
        FoodProductItemGroup foodMenuResultGroup2 = new FoodProductItemGroup();
        foodMenuResultGroup2.setFoods(
                FoodMenuConverter.createSectionAndOrder(result,recommendedMenuTitle,normalMenuTitle));
        return foodMenuResultGroup2;
    }

//    private FoodProductItemGroup convertFoodProduct(FoodMenuResultGroupO dao) {
//        String normalMenuTitle = " เมนูอร่อย ";
//        String recommendedMenuTitle = " เมนูแนะนำ ";
////        List<BaseItem> orderFoodList = new ArrayList<>();
////       orderFoodList.addAll(FoodProductConverter.createSectionAndOrder(dao, recommendedMenuTitle, normalMenuTitle));
//        FoodProductItemGroup foodProductItemGroup = new FoodProductItemGroup();
//        foodProductItemGroup.setFoods(
//                FoodProductConverter.createSectionandOrder(dao, recommendedMenuTitle, normalMenuTitle));
//
//        return foodProductItemGroup;
////        addOldBeerToNewBeerGroupIfAvailable(newGroup);
//
//
////        foodAdapter.setItems(orderFoodList);
////        foodAdapter.notifyDataSetChanged();
//    }

    public void showServiceUnavailableView() {
        rcFood.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.GONE);
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }

    private void updateEmptyCartView() {
        Log.e(TAG, "isHasMenu: " + isHasItem);
        containerServiceUnavailable.setVisibility(View.GONE);
        if (isHasItem) {
            rcFood.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        } else {
            rcFood.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
        }
    }
}
