package com.thingnoy.thingnoy500v3.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hwangjr.rxbus.RxBus;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResultGroup;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.event.GoToOrderDetailActivityEvent;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.history.adapter.HistoryAdapter;
import com.thingnoy.thingnoy500v3.ui.history.adapter.HistoryConverter;
import com.thingnoy.thingnoy500v3.ui.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.ui.history.adapter.item.HistoryItemGroup;
import com.thingnoy.thingnoy500v3.ui.login.LoginActivity;

import java.util.List;

import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class HistoryFragment extends Fragment {
    private static final String TAG = HistoryFragment.class.getSimpleName();
    public static final String KEY_HISTORY_GROUP = "key_history_group";
    private HistoryAdapter historyAdapter;
    private RecyclerView rcHistory;
    private View containerEmpty;
    private SwipeRefreshLayout swipeContainer;
    private View containerServiceUnavailable;
    private Button btnTryAgain;
    private UdonFoodServiceManager serviceManager;
    private HistoryItemGroup itemGroup;
    private LoginResultGroup userInfo;
    private View containerGotoLogin;
    private Button btnGoToLogin;


    public HistoryFragment() {
        super();

    }

    @SuppressWarnings("unused")
    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        initInstances(rootView, savedInstanceState);
        RxBus.get().register(this);
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
        setHistoryToAdapter(getHistoryItemGroup());
    }

    private void initialize() {

        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
                showContent(false);
            } else {
                requestHistory(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                showContent(true);
            }
        } else {
            showContent(false);
        }

    }

    private void showContent(boolean isShow) {
        if (isShow){
            rcHistory.setVisibility(View.VISIBLE);
            containerGotoLogin.setVisibility(View.GONE);
        }else {
            rcHistory.setVisibility(View.GONE);
            containerGotoLogin.setVisibility(View.VISIBLE);
        }
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        serviceManager = UdonFoodServiceManager.getInstance();
        historyAdapter = new HistoryAdapter();
        historyAdapter.setOnItemClickListener(onClickItemListener());
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        bindView(rootView);
        setupView();
    }

    private void bindView(View rootView) {
        rcHistory = rootView.findViewById(R.id.rc_order_history);
        containerEmpty = rootView.findViewById(R.id.container_empty_history);
        containerGotoLogin = rootView.findViewById(R.id.container_go_to_login);
        btnGoToLogin = rootView.findViewById(R.id.btn_go_to_login);

        swipeContainer = rootView.findViewById(R.id.swipe_history);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        btnTryAgain = rootView.findViewById(R.id.btn_try_again);
    }

    private void setupView() {

        loadUserInfoFromCache();

        rcHistory.setHasFixedSize(true);
        rcHistory.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcHistory.setAdapter(historyAdapter);

        swipeContainer.setOnRefreshListener(onPullRefresh());
        updateEmptyView();

        btnTryAgain.setOnClickListener(onClickTryAgain());
        btnGoToLogin.setOnClickListener(onClickLogin());
    }

    private View.OnClickListener onClickLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        };
    }

    private View.OnClickListener onClickTryAgain() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo.getData() != null) {
                    if (userInfo.getData().size() <= 0) {
                        //Not Login
                        showContent(false);
                    } else {
                        requestHistory(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                        showContent(true);
                    }
                } else {
                    showContent(false);
                }
            }
        };
    }

    private void updateEmptyView() {

    }

    private void loadUserInfoFromCache() {
        userInfo = new LoginResultGroup();
        userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, "" + USERINFO);
    }

    private SwipeRefreshLayout.OnRefreshListener onPullRefresh() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setHistoryItemGroup(null);

                if (userInfo.getData() != null) {
                    if (userInfo.getData().size() <= 0) {
                        //Not Login
                        showContent(false);
                    } else {
                        requestHistory(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                        showContent(true);
                    }
                } else {
                    showContent(false);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        RxBus.get().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here

        outState.putParcelable(KEY_HISTORY_GROUP, getHistoryItemGroup());
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here

        setHistoryItemGroup(
                (HistoryItemGroup) savedInstanceState.getParcelable(KEY_HISTORY_GROUP));
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


    public void showServiceUnavailableView() {
        rcHistory.setVisibility(View.GONE);
        containerEmpty.setVisibility(View.GONE);
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }


    public void setHistoryItemToAdapter(List<BaseItem> items) {
        historyAdapter.setItems(items);
        updateEmptyCartView();
    }

    private void updateEmptyCartView() {
        containerServiceUnavailable.setVisibility(View.GONE);
        if (historyAdapter.hasItems()) {
            rcHistory.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.GONE);
        } else {
            rcHistory.setVisibility(View.VISIBLE);
            containerEmpty.setVisibility(View.VISIBLE);
        }
    }


    /*
     * Event & Subscribe
     */
    private HistoryAdapter.onClickHistoryListener onClickItemListener() {
        return new HistoryAdapter.onClickHistoryListener() {
            @Override
            public void onClickItem(HistoryItem item, int position) {
                Log.e(TAG, "onHistoryClick : " + position);
                goToOrderDetailActivity(item);
            }
        };
    }

    /*
     * Presenter
     */

    public void goToOrderDetailActivity(HistoryItem item) {
        RxBus.get().post(new GoToOrderDetailActivityEvent(item));
    }


    public void requestHistory(int id) {
        serviceManager.requestHistory(id, new UdonFoodServiceManager.UdonFoodManagerCallback<HistoryResultGroup>() {
            @Override
            public void onSuccess(HistoryResultGroup result) {
                dismissSwipeLayout();
                HistoryItemGroup newGroup = HistoryConverter.createHistoryItemFromResult(result);
                addOldHistoryToNewHistoryGroupIfAvailable(newGroup);
                itemGroup = newGroup;
                setHistoryToAdapter(itemGroup);
            }

            @Override
            public void onFailure(Throwable t) {
                itemGroup = null;
                dismissSwipeLayout();
                showServiceUnavailableView();
            }

            private void addOldHistoryToNewHistoryGroupIfAvailable(HistoryItemGroup newGroup) {
                if (itemGroup != null) {
                    newGroup.getHistoryList().addAll(0, itemGroup.getHistoryList());
                }
            }
        });
    }


    public void setHistoryToAdapter(HistoryItemGroup group) {
        setHistoryItemToAdapter(group.getBaseItems());
    }

    public HistoryItemGroup getHistoryItemGroup() {
        return itemGroup;
    }

    public void setHistoryItemGroup(HistoryItemGroup itemGroup) {
        this.itemGroup = itemGroup;
    }

}
