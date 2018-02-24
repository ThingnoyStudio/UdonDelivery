package com.thingnoy.thingnoy500v3.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.FoodProductAdapter;
import com.thingnoy.thingnoy500v3.adapter.FoodProductConverter;
import com.thingnoy.thingnoy500v3.adapter.dao.FoodProductCollectionDao;
import com.thingnoy.thingnoy500v3.adapter.item.BaseOrderFoodItem;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.manager.http.HttpManager;
import com.thingnoy.thingnoy500v3.util.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.StaggeredGridLayoutManager.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResSummaryFragment extends Fragment {


    private DataResProDao dao;
    private ImageView ivImg;
    private TextView tvName;
    private TextView tvDescription;
    private RecyclerView rcFoodOrder;
    private FoodProductAdapter foodProductAdapter;

    public ResSummaryFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ResSummaryFragment newInstance(DataResProDao dao) {
        ResSummaryFragment fragment = new ResSummaryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_res_summary, container, false);
        initInstances(rootView, savedInstanceState);
        setupView(rootView);
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
//        Glide.with(ResSummaryFragment.this)// โหลดรูป
//                .load(dao.getRestaurantNameDao().getResImg())// โหลดจาก url นี้
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.loading)// กรณี กำลังโหลด
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
//                .into(ivImg);// โหลดเข้า imageView ตัวนี้
        rcFoodOrder = rootView.findViewById(R.id.rc_food_order);
    }

    private void setupView(View rootView) {
        rcFoodOrder.setLayoutManager(new StaggeredGridLayoutManager(2,VERTICAL));
        foodProductAdapter = new FoodProductAdapter();
        rcFoodOrder.setAdapter(foodProductAdapter);

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
                if (response.isSuccessful()) {
                    FoodProductCollectionDao dao = response.body();//รับของ

                    //setup FoodOrder
                    setFoodProduct(dao);
                } else {
                    Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<FoodProductCollectionDao> call, Throwable t) {
//                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + t.toString());
            }
        });
    }

    private void setFoodProduct(FoodProductCollectionDao dao) {

        String normalMenuTitle = "เมนูอร่อย";
        String recommendedMenu = "เมนูแนะนำ";
        String currency = getString(R.string.baht);

        List<BaseOrderFoodItem> orderFoodList = new ArrayList<>();
        orderFoodList.addAll(FoodProductConverter.createSectionandOrder(dao, recommendedMenu, normalMenuTitle, currency));
        orderFoodList.add(FoodProductConverter.createEmpty());

        foodProductAdapter.setOrderFoodItemList(orderFoodList);
        foodProductAdapter.notifyDataSetChanged();

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
