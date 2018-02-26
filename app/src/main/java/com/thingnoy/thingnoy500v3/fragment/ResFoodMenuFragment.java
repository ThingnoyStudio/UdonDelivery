package com.thingnoy.thingnoy500v3.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
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

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.FoodProductAdapter;
import com.thingnoy.thingnoy500v3.adapter.FoodProductConverter;
import com.thingnoy.thingnoy500v3.adapter.dao.FoodProductCollectionDao;
import com.thingnoy.thingnoy500v3.adapter.item.BaseOrderFoodItem;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
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


    private DataResProDao dao;
    private ImageView ivImg;
    private TextView tvName;
    private TextView tvDescription;
    private RecyclerView rcFoodOrder;
    private FoodProductAdapter foodProductAdapter;
    private View containerEmpty;
    private View containerServiceUnavailable;
    private boolean isHasItem;
    private CardView containerProgressbar;

    public ResFoodMenuFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ResFoodMenuFragment newInstance(DataResProDao dao) {
        ResFoodMenuFragment fragment = new ResFoodMenuFragment();
        Bundle args = new Bundle();

        args.putParcelable("dao", dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        dao = getArguments().getParcelable("dao");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_res_food_menu, container, false);
        initInstances(rootView, savedInstanceState);
        setupView();
        callService();
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
        rcFoodOrder = rootView.findViewById(R.id.rc_food_order);
        containerEmpty = rootView.findViewById(R.id.container_empty);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        containerProgressbar = rootView.findViewById(R.id.container_progressbar);
    }

    private void setupView() {
        rcFoodOrder.setLayoutManager(new StaggeredGridLayoutManager(2, VERTICAL));
        foodProductAdapter = new FoodProductAdapter();
        rcFoodOrder.setAdapter(foodProductAdapter);

        containerEmpty.setVisibility(View.GONE);
        containerProgressbar.setVisibility(View.VISIBLE);


//        topRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
//        snapHelperTop.attachToRecyclerView(rcFoodOrder);
    }

    private void callService() {
        Call<FoodProductCollectionDao> call = HttpManager.getInstance()
                .getService()
                .getFoodById(dao.getRestaurantNameDao().getIDRestaurant());

        call.enqueue(new Callback<FoodProductCollectionDao>() {
            @Override
            public void onResponse(Call<FoodProductCollectionDao> call, Response<FoodProductCollectionDao> response) {
                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า  onResponse : " + response.isSuccessful());
//                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                containerProgressbar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    FoodProductCollectionDao dao = response.body();//รับของ

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
                    //setup FoodOrder
                    setFoodProduct(dao);
                    updateEmptyCartView();


                } else {
                    showServiceUnavailableView();
                    Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<FoodProductCollectionDao> call, Throwable t) {
//                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                containerProgressbar.setVisibility(View.GONE);
                showServiceUnavailableView();
                Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + t.toString());
            }
        });
    }

    private void setFoodProduct(FoodProductCollectionDao dao) {

        String normalMenuTitle = " เมนูอร่อย ";
        String recommendedMenu = " เมนูแนะนำ ";
        String currency = getString(R.string.baht);

        List<BaseOrderFoodItem> orderFoodList = new ArrayList<>();
        orderFoodList.addAll(FoodProductConverter.createSectionandOrder(dao, recommendedMenu, normalMenuTitle, currency));

        foodProductAdapter.setOrderFoodItemList(orderFoodList);
        foodProductAdapter.notifyDataSetChanged();
    }

    public void showServiceUnavailableView() {
        rcFoodOrder.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.GONE);
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }

    private void updateEmptyCartView() {
        Log.e("isHasMenu", "isHasMenu: " + isHasItem);
        containerServiceUnavailable.setVisibility(View.GONE);
        if (isHasItem) {
            rcFoodOrder.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        } else {
            rcFoodOrder.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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


}
