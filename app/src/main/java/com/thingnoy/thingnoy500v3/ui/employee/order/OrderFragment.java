package com.thingnoy.thingnoy500v3.ui.employee.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.hwangjr.rxbus.RxBus;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.result.favorite.DataFavorite;
import com.thingnoy.thingnoy500v3.api.result.favorite.FavoriteResultGroup;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.authen.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.FavoriteAdapter;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.FavoriteConverter;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteGroup;
import com.thingnoy.thingnoy500v3.ui.moreinfo.MoreInfoActivity;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class OrderFragment extends Fragment {
    private static final String TAG = OrderFragment.class.getSimpleName();

    private UdonFoodServiceManager serviceManager;
    private View containerServiceUnavailable;
    private LoginResultGroup userInfo;
    private FavoriteGroup itemGroup;
    private SweetAlertDialog mDialog;
    private RecyclerView rcOrder;
    private View containerEmpty;


    public OrderFragment() {
        super();
        serviceManager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
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
        RxBus.get().register(this);
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            initialize();
        } else {
            restoreView(savedInstanceState);
        }
    }

    private void restoreView(Bundle savedInstanceState) {
        serFavoriteToAdapter(itemGroup);
    }

    private void initialize() {
        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
                showContent(false);
            } else {
                requestGetFavorite(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                showContent(true);
            }
        } else {
            showContent(false);
        }
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        bindView(rootView);

        setupView();
    }

    private void bindView(View rootView) {
        rcOrder = rootView.findViewById(R.id.rc_emp_order);

        containerEmpty = rootView.findViewById(R.id.container_empty_emp_order);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
    }

    private void setupView() {
        loadUserInfoFromCache();

    }

    private void loadUserInfoFromCache() {
        userInfo = new LoginResultGroup();
        userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, "" + USERINFO);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
                showContent(false);
            } else {
                requestGetFavorite(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                showContent(true);
            }
        } else {
            showContent(false);
        }


    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        outState.putParcelable(KEY_FAVORITE_GROUP, itemGroup);
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        this.itemGroup = ((FavoriteGroup) savedInstanceState.getParcelable(KEY_FAVORITE_GROUP));
    }

    public void showServiceUnavailableView() {
        rcOrder.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.GONE);
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }

    public void dismissSwipeLayout() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeContainer.setRefreshing(false);
            }
        }, 500);
    }

    private void showErrorDialog(String content) {
        mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("กรุณาทำการเข้าสู่ระบบ");
        mDialog.setContentText("" + content);
        mDialog.setConfirmText("ไปยังหน้า Login");
        mDialog.setCancelText("ยกเลิก");
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                mDialog.dismissWithAnimation();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
//        mDialog.setCancelable(false);

        mDialog.show();
    }


    /*
     * Event & Request
     */

    public void requestGetFavorite(int id) {
        serviceManager.requestGetFavorite(id, new UdonFoodServiceManager.UdonFoodManagerCallback<FavoriteResultGroup>() {
            @Override
            public void onSuccess(FavoriteResultGroup result) {
                dismissSwipeLayout();
                if (result.getData() != null) {
//                    Log.e(TAG, "requestGetFavorite: " + new GetPrettyPrintJson().getJson(result));
                    rawResult = result;
                    itemGroup = convertFavorite(result.getData());
                    setFavoriteToAdapter(itemGroup);
                }
                updateEmptyView();
            }

            @Override
            public void onFailure(Throwable t) {
                dismissSwipeLayout();
                showServiceUnavailableView();
            }
        });
    }

}
