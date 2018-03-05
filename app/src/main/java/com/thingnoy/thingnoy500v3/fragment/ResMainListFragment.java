package com.thingnoy.thingnoy500v3.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.PromotionAdapter;
import com.thingnoy.thingnoy500v3.adapter.RestaurantAdapter;
import com.thingnoy.thingnoy500v3.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.dao.promotion.PromotionCollectionDao;
import com.thingnoy.thingnoy500v3.dao.restaurant.RestaurantCollectionDao;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;
import com.thingnoy.thingnoy500v3.manager.ResMainListManager;
import com.thingnoy.thingnoy500v3.manager.http.HttpManager;
import com.thingnoy.thingnoy500v3.util.Constant;

public class ResMainListFragment extends Fragment implements ItemClickListener {

    private RecyclerView rcPromotion;
    private View containerServiceUnavailable;
    private PromotionAdapter promotionAdapter;
    private RecyclerView rcRestaurant;
    private RestaurantAdapter restaurantAdapter;
    private View resProgressbar, promoProgressbar;
    private ResMainListManager resMainlistManager;
    private NameAndImageDao nameAndImg;


    public interface FragmentListener {
        void onItemClicked(NameAndImageDao nameAndImageDao);
    }

    public ResMainListFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ResMainListFragment newInstance() {
        ResMainListFragment fragment = new ResMainListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_res_main_list, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }


    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        rcPromotion = rootView.findViewById(R.id.rc_promotion_list);
        rcRestaurant = rootView.findViewById(R.id.rc_restaurant_list);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        resProgressbar = rootView.findViewById(R.id.res_progressbar);
        promoProgressbar = rootView.findViewById(R.id.promo_progressbar);


        setupView();

        if (savedInstanceState == null) {
            refreshData();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupView() {
//        resProgressbar.setVisibility(View.VISIBLE);
//        promoProgressbar.setVisibility(View.VISIBLE);

        resMainlistManager = new ResMainListManager();

        promotionAdapter = new PromotionAdapter();
        restaurantAdapter = new RestaurantAdapter();

        promotionAdapter.setDao(resMainlistManager.getPromotionCollectionDao());
        restaurantAdapter.setDao(resMainlistManager.getRestaurantCollectionDao());

        rcPromotion.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rcPromotion.setAdapter(promotionAdapter);
        rcPromotion.setItemAnimator(new DefaultItemAnimator());

//        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
//        snapHelperStart.attachToRecyclerView(rcPromotion);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rcPromotion);


        rcRestaurant.setHasFixedSize(true);
        rcRestaurant.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
//        rcRestaurant.setLayoutManager(new LinearLayoutManager(getContext()));
        rcRestaurant.setAdapter(restaurantAdapter);
        rcRestaurant.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(rcRestaurant);

        //set onclick
        restaurantAdapter.setItemClickListener(this);
        promotionAdapter.setItemClickListener(this);

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
        outState.putBundle("resManager", resMainlistManager.onSaveInstanceState());
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        resMainlistManager.onRestoreInstanceState(savedInstanceState.getBundle("resManager"));
    }

    @Override
    public void onClick(View view, int position, boolean isPromotionClick) {
        if (isPromotionClick) {
//            showToast("proClick: " + position);

            nameAndImg = new NameAndImageDao();
            nameAndImg.setResId(Integer.parseInt(resMainlistManager.getPromotionCollectionDao().getmData().get(position).getmIDRestaurant()));
            nameAndImg.setResName(resMainlistManager.getPromotionCollectionDao().getmData().get(position).getmResName());
            nameAndImg.setResImage(resMainlistManager.getPromotionCollectionDao().getmData().get(position).getmResImg());

            FragmentListener listener = (FragmentListener) getActivity();//ส่งสัญญาณไปให้ Activity ทำงาน
            assert listener != null;
            listener.onItemClicked(nameAndImg);
        } else {
//            showToast("resClick: " + position);

            nameAndImg = new NameAndImageDao();
            nameAndImg.setResId(Integer.parseInt(resMainlistManager.getRestaurantCollectionDao().getmData().get(position).getmIDRestaurant()));
            nameAndImg.setResName(resMainlistManager.getRestaurantCollectionDao().getmData().get(position).getmResName());
            nameAndImg.setResImage(resMainlistManager.getRestaurantCollectionDao().getmData().get(position).getmResImg());
            FragmentListener listener = (FragmentListener) getActivity();//ส่งสัญญาณไปให้ Activity ทำงาน
            assert listener != null;
            listener.onItemClicked(nameAndImg);
        }
    }

    private void refreshData() {
        if (resMainlistManager.getResCount() == 0) {
            resProgressbar.setVisibility(View.VISIBLE);
            rcRestaurant.setVisibility(View.GONE);
            callGetResAll();
        }
        if (resMainlistManager.getProCount() == 0) {
            promoProgressbar.setVisibility(View.VISIBLE);
            rcRestaurant.setVisibility(View.GONE);
            callGetResPro();
        }
    }

    private void callGetResPro() {
        Call<PromotionCollectionDao> call = HttpManager.getInstance()
                .getService()
                .getPromotion();
        call.enqueue(new Callback<PromotionCollectionDao>() {
            @Override
            public void onResponse(Call<PromotionCollectionDao> call, Response<PromotionCollectionDao> response) {
                if (response.isSuccessful()) {

                    PromotionCollectionDao dao = response.body();

                    resMainlistManager.setPromotionCollectionDao(dao);

                    promotionAdapter.setDao(resMainlistManager.getPromotionCollectionDao());
                    promotionAdapter.notifyDataSetChanged();

                    promoProgressbar.setVisibility(View.GONE);
                } else {
                    showToast("ดาวน์โหลดรายการโปรโมชั่น ไม่สำเร็จ! " + Constant.FACESAD);

                    promoProgressbar.setVisibility(View.GONE);
                    showServiceUnavailableView();
                }
            }

            @Override
            public void onFailure(Call<PromotionCollectionDao> call, Throwable t) {
                showToast("ดาวน์โหลดรายการโปรโมชั่น ล้มเหลว! : " + t);

                promoProgressbar.setVisibility(View.GONE);
                showServiceUnavailableView();
            }
        });
    }

    private void callGetResAll() {
        Call<RestaurantCollectionDao> call = HttpManager.getInstance()
                .getService()
                .getRestaurant();
        call.enqueue(new Callback<RestaurantCollectionDao>() {
            @Override
            public void onResponse(Call<RestaurantCollectionDao> call, Response<RestaurantCollectionDao> response) {
                if (response.isSuccessful()) {
                    RestaurantCollectionDao dao = response.body();

                    resMainlistManager.setRestaurantCollectionDao(dao);

                    restaurantAdapter.setDao(resMainlistManager.getRestaurantCollectionDao());
                    restaurantAdapter.notifyDataSetChanged();

                    resProgressbar.setVisibility(View.GONE);

                } else {
                    showToast("ดาวน์โหลดรายการร้านอาหาร ไม่สำเร็จ! " + Constant.FACESAD);
                    resProgressbar.setVisibility(View.GONE);
                    showServiceUnavailableView();
                }
            }

            @Override
            public void onFailure(Call<RestaurantCollectionDao> call, Throwable t) {
                showToast("ดาวน์โหลดรายการร้านอาหาร ล้มเหลว! : " + t);
                resProgressbar.setVisibility(View.GONE);
                showServiceUnavailableView();
            }
        });
    }

    private void showToast(String massage) {
        Toast.makeText(getContext(), massage, Toast.LENGTH_SHORT).show();
    }

    public void showServiceUnavailableView() {
        rcPromotion.setVisibility(View.GONE);
        rcRestaurant.setVisibility(View.GONE);
        resProgressbar.setVisibility(View.GONE);
        promoProgressbar.setVisibility(View.GONE);
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }

}
