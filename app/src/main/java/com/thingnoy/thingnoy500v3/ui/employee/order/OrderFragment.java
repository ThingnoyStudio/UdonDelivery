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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.hwangjr.rxbus.RxBus;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.result.emporder.OrderResultGroup;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.event.event_main.GoToEmpOrderDetailEvent;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.authen.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.OrderAdapter;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.OrderConverter;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItem;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItemGroup;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class OrderFragment extends Fragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    private static final String KEY_EMP_ORDER_GROUP = "key_emp_order_group";

    private UdonFoodServiceManager serviceManager;
    private View containerServiceUnavailable;
    private LoginResultGroup userInfo;
    private OrderItemGroup itemGroup;
    private SweetAlertDialog mDialog;
    private RecyclerView rcOrder;
    private View containerEmpty;
    private Button btnTryAgain;
    private OrderAdapter orderAdapter;
    private SwipeRefreshLayout swipeContainer;


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
        View rootView = inflater.inflate(R.layout.fragment_emp_order, container, false);
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
        setDataToAdapter(itemGroup);
    }

    private void initialize() {
        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
//                showContent(false);
            } else {
                requestGetOrder(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
//                showContent(true);
            }
        } else {
//            showContent(false);
        }
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        orderAdapter = new OrderAdapter();
        orderAdapter.setOnItemClickListener(onClickItemListener());
    }


    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        bindView(rootView);
        setupView();
    }

    private void bindView(View rootView) {
        rcOrder = rootView.findViewById(R.id.rc_emp_order);

        swipeContainer = rootView.findViewById(R.id.swipe_emp_order);
        btnTryAgain = rootView.findViewById(R.id.btn_try_again);
        containerEmpty = rootView.findViewById(R.id.container_empty_emp_order);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
    }

    private void setupView() {
        loadUserInfoFromCache();
        btnTryAgain.setOnClickListener(onClickTryAgain());
        swipeContainer.setOnRefreshListener(onPullToRefresh());

        rcOrder.setHasFixedSize(true);
        rcOrder.setItemAnimator(new DefaultItemAnimator());
        rcOrder.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcOrder.setAdapter(orderAdapter);
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
        Log.e(TAG, "onResume");
        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
//                showContent(false);
            } else {
                requestGetOrder(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
//                showContent(true);
            }
        } else {
//            showContent(false);
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        RxBus.get().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
        outState.putParcelable(KEY_EMP_ORDER_GROUP, itemGroup);
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        this.itemGroup = ((OrderItemGroup) savedInstanceState.getParcelable(
                KEY_EMP_ORDER_GROUP));
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

    private void goToOrderDetail(OrderItem item) {
        RxBus.get().post(new GoToEmpOrderDetailEvent(item));
    }

    private void updateEmptyView() {
        containerServiceUnavailable.setVisibility(View.GONE);
        if (orderAdapter.hasItems()) {
            rcOrder.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        } else {
            rcOrder.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setDataToAdapter(OrderItemGroup group) {
        orderAdapter.setItems(group.getBaseItems());
        updateEmptyView();
    }


    /*
     * Event & Request
     */

    public void requestGetOrder(int idEmployee) {
        serviceManager.requestGetOrder(idEmployee, new UdonFoodServiceManager.UdonFoodManagerCallback<OrderResultGroup>() {
            @Override
            public void onSuccess(OrderResultGroup result) {
                dismissSwipeLayout();
                if (result.getData() != null) {
                    OrderItemGroup newGroup = OrderConverter
                            .createItemFromResult(result);
                    itemGroup = newGroup;
                    setDataToAdapter(itemGroup);
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

    private View.OnClickListener onClickTryAgain() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo.getData() != null) {
                    if (userInfo.getData().size() <= 0) {
                        //Not Login
//                        showContent(false);
                    } else {
                        requestGetOrder(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
//                        showContent(true);
                    }
                } else {
//                    showContent(false);
                }
            }
        };
    }

    private OrderAdapter.onClickEmpOrderListener onClickItemListener() {
        return new OrderAdapter.onClickEmpOrderListener() {
            @Override
            public void onClickItem(OrderItem item, int position) {
                Log.e(TAG,"onClickItemListener>> item: "+new GetPrettyPrintJson().getJson(item));
                goToOrderDetail(item);
            }
        };
    }

    private SwipeRefreshLayout.OnRefreshListener onPullToRefresh() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestGetOrder(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
            }
        };
    }
}
