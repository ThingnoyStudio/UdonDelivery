package com.thingnoy.thingnoy500v3.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.PromotionAdapter;
import com.thingnoy.thingnoy500v3.adapter.RestaurantAdapter;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;
import com.thingnoy.thingnoy500v3.manager.ResMainListManager;
import com.thingnoy.thingnoy500v3.util.ItemAnimation;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;

public class ResMainListFragment extends Fragment implements ItemClickListener {
    private final static String TAG = ResMainListFragment.class.getSimpleName();
    private final UdonFoodServiceManager serviceManager;

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
        serviceManager = UdonFoodServiceManager.getInstance();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        Log.e(TAG,"initInstances");
        rcPromotion = rootView.findViewById(R.id.rc_promotion_list);
        rcRestaurant = rootView.findViewById(R.id.rc_restaurant_list);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        resProgressbar = rootView.findViewById(R.id.res_progressbar);
        promoProgressbar = rootView.findViewById(R.id.promo_progressbar);

//        containerServiceUnavailable.setVisibility(View.GONE);


        setupView();

        if (savedInstanceState == null) {
            loadData();
        }
//        else {
////            refreshData();
//        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupView() {
        resMainlistManager = new ResMainListManager();

        promotionAdapter = new PromotionAdapter();
        restaurantAdapter = new RestaurantAdapter();

        promotionAdapter.setItems(resMainlistManager.getPromotionResultGroup(),ItemAnimation.RIGHT_LEFT);
        restaurantAdapter.setItems(resMainlistManager.getRestaurantResultGroup(), ItemAnimation.FADE_IN);

        rcPromotion.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        rcPromotion.setAdapter(promotionAdapter);
//        rcPromotion.setItemAnimator(new DefaultItemAnimator());

//        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
//        snapHelperStart.attachToRecyclerView(rcPromotion);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rcPromotion);


        rcRestaurant.setHasFixedSize(true);
        rcRestaurant.setLayoutManager(new StaggeredGridLayoutManager(2, VERTICAL));
//        rcRestaurant.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));
//        rcRestaurant.setLayoutManager(new LinearLayoutManager(getContext()));
//        Animation animation = AnimationUtils.loadAnimation(getContext(),
//                R.anim.up_from_bottom);//create anim
        rcRestaurant.setAdapter(restaurantAdapter);
//        rcRestaurant.setItemAnimator();

        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(rcRestaurant);
//        SnapHelper snapHelper2 = new LinearSnapHelper();
//        snapHelper2.attachToRecyclerView(rcRestaurant);

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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
//        outState.putBundle("resManager", resMainlistManager.onSaveInstanceState());
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
//        resMainlistManager.onRestoreInstanceState(savedInstanceState.getBundle("resManager"));
    }

    @Override
    public void onClick(View view, int position, boolean isPromotionClick) {
        if (isPromotionClick) {
//            showToast("proClick: " + position);

            nameAndImg = new NameAndImageDao();
            nameAndImg.setResId(Integer.parseInt(resMainlistManager.getPromotionResultGroup().getmData().get(position).getmIDRestaurant()));
            nameAndImg.setResName(resMainlistManager.getPromotionResultGroup().getmData().get(position).getmResName());
            nameAndImg.setResImage(resMainlistManager.getPromotionResultGroup().getmData().get(position).getmResImg());
            nameAndImg.setDeliveryFee(false);

            FragmentListener listener = (FragmentListener) getActivity();//ส่งสัญญาณไปให้ Activity ทำงาน
            assert listener != null;
            listener.onItemClicked(nameAndImg);
        } else {
//            showToast("resClick: " + position);

            nameAndImg = new NameAndImageDao();
            nameAndImg.setResId(Integer.parseInt(resMainlistManager.getRestaurantResultGroup().getmData().get(position).getmIDRestaurant()));
            nameAndImg.setResName(resMainlistManager.getRestaurantResultGroup().getmData().get(position).getmResName());
            nameAndImg.setResImage(resMainlistManager.getRestaurantResultGroup().getmData().get(position).getmResImg());
            FragmentListener listener = (FragmentListener) getActivity();//ส่งสัญญาณไปให้ Activity ทำงาน
            assert listener != null;
            listener.onItemClicked(nameAndImg);
        }
    }

    private void loadData(){
        resProgressbar.setVisibility(View.VISIBLE);
        rcRestaurant.setVisibility(View.GONE);
//            callGetResAll();
        requestRestaurant();

        promoProgressbar.setVisibility(View.VISIBLE);
        rcPromotion.setVisibility(View.GONE);
//            callGetResPro();
        requestPromotion();
    }
    private void refreshData() {
        if (resMainlistManager.getResCount() == 0) {
            resProgressbar.setVisibility(View.VISIBLE);
            rcRestaurant.setVisibility(View.GONE);
//            callGetResAll();
            requestRestaurant();
        }
        if (resMainlistManager.getProCount() == 0) {
            promoProgressbar.setVisibility(View.VISIBLE);
            rcPromotion.setVisibility(View.GONE);
//            callGetResPro();
            requestPromotion();
        }
    }

    private void requestPromotion(){
        serviceManager.requestPromotion(new UdonFoodServiceManager.UdonFoodManagerCallback<PromotionResultGroup>() {
            @Override
            public void onSuccess(PromotionResultGroup result) {
                resMainlistManager.setPromotionResultGroup(result);

                promotionAdapter.setItems(resMainlistManager.getPromotionResultGroup(),ItemAnimation.RIGHT_LEFT);

                rcPromotion.setVisibility(View.VISIBLE);
                promoProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showToast("ดาวน์โหลดรายการโปรโมชั่น ล้มเหลว! : " + t);
                promoProgressbar.setVisibility(View.GONE);
//                showServiceUnavailableView();
            }
        });
    }

    private void requestRestaurant(){
        serviceManager.requestRestaurant(new UdonFoodServiceManager.UdonFoodManagerCallback<RestaurantResultGroup>() {
            @Override
            public void onSuccess(RestaurantResultGroup result) {
                resMainlistManager.setRestaurantResultGroup(result);

                restaurantAdapter.setItems(resMainlistManager.getRestaurantResultGroup(),ItemAnimation.FADE_IN);

                rcRestaurant.setVisibility(View.VISIBLE);
                resProgressbar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Throwable t) {
                showToast("ดาวน์โหลดรายการร้านอาหาร ล้มเหลว! : " + t);
                resProgressbar.setVisibility(View.GONE);
//                containerServiceUnavailable.setVisibility(View.VISIBLE);
//                showServiceUnavailableView();
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
