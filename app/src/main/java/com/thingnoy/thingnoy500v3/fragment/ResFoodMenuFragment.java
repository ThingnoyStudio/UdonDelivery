package com.thingnoy.thingnoy500v3.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.FoodProductAdapter;
import com.thingnoy.thingnoy500v3.adapter.FoodProductConverter;
import com.thingnoy.thingnoy500v3.adapter.dao.FoodProductCollectionDao;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItemGroup;
import com.thingnoy.thingnoy500v3.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.event.AddFoodToCartEvent;
import com.thingnoy.thingnoy500v3.event.ClearAddedButtonStateAllEvent;
import com.thingnoy.thingnoy500v3.event.ClearAddedButtonStateEvent;
import com.thingnoy.thingnoy500v3.event.RemoveFoodFromCartEvent;
import com.thingnoy.thingnoy500v3.manager.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.StaggeredGridLayoutManager.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResFoodMenuFragment extends Fragment {

    public static final int REQ_ORDER = 1000;
    public static final String KEY_BEER_GROUP = "key_beer_group";
    public static final String KEY_FOOD_ITEM_IN_MENU = "key_food_item_in_menu";
    private NameAndImageDao dao;
    private ImageView ivImg;
    private TextView tvName;
    private TextView tvDescription;
    private RecyclerView rcFood;
    private FoodProductAdapter foodAdapter;
    private View containerEmpty;
    private View containerServiceUnavailable;
    private boolean isHasItem;
    private CardView containerProgressbar;
    private FoodProductItem item;
    private FoodProductItemGroup itemGroup;

    public ResFoodMenuFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ResFoodMenuFragment newInstance(NameAndImageDao dao) {
        ResFoodMenuFragment fragment = new ResFoodMenuFragment();
        Bundle args = new Bundle();

        args.putParcelable("dao", dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("val", "onCreate");
        init(savedInstanceState);

        dao = getArguments().getParcelable("dao");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("val", "onViewCreated");
        RxBus.get().register(this);

        if (savedInstanceState == null) {
            initialize();
        } else {
            restoreView(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("val", "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_res_food_menu, container, false);
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
//        Glide.with(ResFoodMenuFragment.this)// โหลดรูป
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
        rcFood.setAdapter(foodAdapter);

        containerEmpty.setVisibility(View.GONE);

        Log.e("val", "setupView");
        containerProgressbar.setVisibility(View.VISIBLE);


//        topRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
//        snapHelperTop.attachToRecyclerView(rcFood);
    }

    private void initialize() {
//        foodAdapter.initDefaultItemForLoadmore();
        callGetMenuById();
    }

    private void restoreView(Bundle savedInstanceState) {
        Log.e("val", "restoreView");
        setFoodProductToAdapter(getFoodItemGroup());
//        if (savedInstanceState )
        containerProgressbar.setVisibility(View.GONE);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        Log.e("val", "onSaveInstanceState");
        outState.putParcelable(KEY_BEER_GROUP, getFoodItemGroup());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        Log.e("val", "onRestoreInstanceState");
        setFoodItemGroup((FoodProductItemGroup) savedInstanceState.getParcelable(KEY_BEER_GROUP));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    public void setFoodProductItemToAdapter(List<BaseItem> foodItemFromResult) {
        foodAdapter.setItems(foodItemFromResult);
    }

    public void onClearAddedButtonStateEvent(FoodProductItem item) {
        foodAdapter.clearAddedState(item);
    }

    public void onClearAddedButtonAllStateEvent() {
        foodAdapter.clearAddedStateAll();
        rcFood.smoothScrollToPosition(0);
    }

    public List<BaseItem> getItemsFromAdapter() {
        return foodAdapter.getItems();
    }

//    @Subscribe
//    public void onClearAddedButtonStateEvent(ClearAddedButtonStateEvent event) {
////        getView().onClearAddedButtonStateEvent( event.getItem() );
//        foodAdapter.clearAddedState(event.getItem());
//    }
//
//    @Subscribe
//    public void onClearAddedButtonStateAllEvent(ClearAddedButtonStateAllEvent event) {
////        getView().onClearAddedButtonAllStateEvent();
//        foodAdapter.clearAddedStateAll();
//        rcFood.smoothScrollToPosition(0);
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
                Log.e("val", "GetItem on cart : " + getItemsFromAdapter().size());
                showToast("onClickItem: " + position);
            }

            @Override
            public void onClickAddToCart(FoodProductItem item, int position) {
                showToast("AddToCart: " + position);
                addFoodItemToCart(item);
            }

            @Override
            public void onClickAdded(FoodProductItem item, int position) {
                showToast("onClickAdded: " + position);
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
        Log.e("val", "setFoodItemGroup");
        this.itemGroup = items;
    }

    public void setFoodProductToAdapter(FoodProductItemGroup group) {
        setFoodProductItemToAdapter(group.getFoods());
    }

    public void addFoodItemToCart(FoodProductItem item) {
        RxBus.get().post(new AddFoodToCartEvent(item));
    }

    public void deleteFoodItemFromCart(FoodProductItem item, int position) {
        RxBus.get().post(new RemoveFoodFromCartEvent(item));
    }

    @Subscribe
    public void onClearAddedButtonStateEvent(ClearAddedButtonStateEvent event) {
        onClearAddedButtonStateEvent(event.getItem());
    }

    @Subscribe
    public void onClearAddedButtonStateAllEvent(ClearAddedButtonStateAllEvent event) {
        onClearAddedButtonAllStateEvent();
    }

    /**************
     * Function
     **************/

    private void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void callGetMenuById() {
        Call<FoodProductCollectionDao> call = HttpManager.getInstance()
                .getService()
                .getFoodMenuById(dao.getResId());

        call.enqueue(new Callback<FoodProductCollectionDao>() {
            @Override
            public void onResponse(Call<FoodProductCollectionDao> call, Response<FoodProductCollectionDao> response) {
                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า  onResponse : " + response.isSuccessful());
//                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                containerProgressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    FoodProductCollectionDao dao = response.body();//รับของ

                    //region chkIshasItem
                    if (dao != null) {
                        if (dao.getmData().getmRecommendedMenu().size() > 0) {
                            isHasItem = true;
                            Log.e("isHasitem", "getmRecommendedMenu != null");
                        } else if (dao.getmData().getmNormalmenu().size() > 0) {
                            isHasItem = true;
                            Log.e("isHasitem", "getmNormalmenu != null");
                        } else {
                            isHasItem = false;
                        }

                        Log.e("isHasitem", "dao != null");
                    } else {
                        isHasItem = false;
                        Log.e("isHasitem", "dao = null");
                    }
                    //endregion

                    FoodProductItemGroup newGroup = new FoodProductItemGroup();
                    newGroup = convertFoodProduct(dao);
                    itemGroup = newGroup;
                    setFoodProductToAdapter(itemGroup);
                    updateEmptyCartView();


                } else {
                    showServiceUnavailableView();
                    Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<FoodProductCollectionDao> call, Throwable t) {
//                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                containerProgressbar.setVisibility(View.GONE);

                itemGroup = null;

                showServiceUnavailableView();
                Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + t.toString());
            }
        });
    }

    private FoodProductItemGroup convertFoodProduct(FoodProductCollectionDao dao) {
        String normalMenuTitle = " เมนูอร่อย ";
        String recommendedMenu = " เมนูแนะนำ ";
//        List<BaseItem> orderFoodList = new ArrayList<>();
//       orderFoodList.addAll(FoodProductConverter.createSectionandOrder(dao, recommendedMenu, normalMenuTitle));
        FoodProductItemGroup foodProductItemGroup = new FoodProductItemGroup();
        foodProductItemGroup.setFoods(FoodProductConverter.createSectionandOrder(dao, recommendedMenu, normalMenuTitle));
        return foodProductItemGroup;
//        addOldBeerToNewBeerGroupIfAvailable(newGroup);


//        foodAdapter.setItems(orderFoodList);
//        foodAdapter.notifyDataSetChanged();
    }

    public void showServiceUnavailableView() {
        rcFood.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.GONE);
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }

    private void updateEmptyCartView() {
        Log.e("isHasMenu", "isHasMenu: " + isHasItem);
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
