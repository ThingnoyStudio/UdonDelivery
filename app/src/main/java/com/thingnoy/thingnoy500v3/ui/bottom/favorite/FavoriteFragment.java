package com.thingnoy.thingnoy500v3.ui.bottom.favorite;

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
import com.thingnoy.thingnoy500v3.event.event_main.AddFoodFavoriteToCartEvent;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.FavoriteAdapter;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.FavoriteConverter;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteGroup;
import com.thingnoy.thingnoy500v3.ui.authen.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.moreinfo.MoreInfoActivity;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.VERTICAL;
import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class FavoriteFragment extends Fragment {
    private static final String TAG = FavoriteFragment.class.getSimpleName();
    public static final String KEY_FAVORITE_GROUP = "key_favorite_group";

    private UdonFoodServiceManager serviceManager;
    private View containerServiceUnavailable;
    private LoginResultGroup userInfo;
    private RecyclerView rcFavorite;
    private View containerEmpty;
    private View containerGotoLogin;
    private Button btnGoToLogin;
    private SwipeRefreshLayout swipeContainer;
    private Button btnTryAgain;
    private FavoriteGroup itemGroup;
    private FavoriteAdapter favoriteAdapter;
    private SweetAlertDialog mDialog;
    private FavoriteResultGroup rawResult;


    public FavoriteFragment() {
        super();
        serviceManager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
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

    private void showContent(boolean isShow) {
        if (isShow) {
//            rcFavorite.setVisibility(View.VISIBLE);
            containerGotoLogin.setVisibility(View.GONE);
            containerServiceUnavailable.setVisibility(View.GONE);
        } else {
//            rcFavorite.setVisibility(View.GONE);
            containerGotoLogin.setVisibility(View.VISIBLE);
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
        rcFavorite = rootView.findViewById(R.id.rc_favorite);
        containerEmpty = rootView.findViewById(R.id.container_empty_favorite);
        containerGotoLogin = rootView.findViewById(R.id.container_go_to_login);
        btnGoToLogin = rootView.findViewById(R.id.btn_go_to_login);

        swipeContainer = rootView.findViewById(R.id.swipe_favorite);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        btnTryAgain = rootView.findViewById(R.id.btn_try_again);
    }

    private void setupView() {
        loadUserInfoFromCache();

        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.setOnClickFavoriteItemListener(onClickFavorite());

//        rcFavorite.setHasFixedSize(true);
//        rcFavorite.setLayoutManager(new LinearLayoutManager(getContext(),
//                LinearLayoutManager.VERTICAL, false));
        rcFavorite.setLayoutManager(new StaggeredGridLayoutManager(2, VERTICAL));
        rcFavorite.setItemAnimator(new DefaultItemAnimator());
        rcFavorite.setAdapter(favoriteAdapter);

        swipeContainer.setOnRefreshListener(onPullRefresh());

        btnTryAgain.setOnClickListener(onClickTryAgain());
        btnGoToLogin.setOnClickListener(onClickLogin());

    }


    private void serFavoriteToAdapter(FavoriteGroup group) {
        setFavoriteItemToAdapter(group.getFavorite());
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
        rcFavorite.setVisibility(View.GONE);
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

    private void updateEmptyView() {
        containerServiceUnavailable.setVisibility(View.GONE);
        if (favoriteAdapter.hasItems()) {
            if (favoriteAdapter.getItemCount() == 1) {
                rcFavorite.setVisibility(View.GONE);
                containerEmpty.setVisibility(View.VISIBLE);
            } else {
                rcFavorite.setVisibility(View.VISIBLE);
                containerEmpty.setVisibility(View.GONE);
            }

        } else {
            rcFavorite.setVisibility(View.GONE);
            containerEmpty.setVisibility(View.VISIBLE);
        }
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

    private void onRemoveLikeMenu(FavoriteFoodItem item) {
        favoriteAdapter.removeItem(item);
        updateEmptyView();
    }

    private void goToMoreInfoActivity(FavoriteFoodItem item) {
        NameAndImageDao dao = new NameAndImageDao();

        int restaurantPosition = 0;
        for (int i = 0; i < rawResult.getData().size(); i++) {
            if (item.getIdRestaurant().equals(rawResult.getData().get(i).getRes().getIDRestaurant())) {
                restaurantPosition = i;
            }
        }

        Log.e(TAG, "position: " + restaurantPosition);
        if (rawResult.getData().get(restaurantPosition).getRes().getmRespro() != null) {
            dao.setDeliveryFee(false);
            dao.setPromotionId(Integer.parseInt(
                    rawResult.getData().get(restaurantPosition).getRes().getmRespro()
                            .get(0).getmIDResPromotion()));
        } else {
            dao.setDeliveryFee(true);
        }
        dao.setResId(Integer.parseInt(rawResult.getData().get(restaurantPosition).getRes().getIDRestaurant()));
        dao.setResName(rawResult.getData().get(restaurantPosition).getRes().getResName());
        dao.setResImage(rawResult.getData().get(restaurantPosition).getRes().getResImg());

        Intent intent = new Intent(getContext(),
                MoreInfoActivity.class);
        intent.putExtra("dao", dao);
        intent.putExtra("favoriteItem", getFood(item));
        Log.e(TAG,"goToMoreInfoActivity>> dao: "+new GetPrettyPrintJson().getJson(dao));
        Log.e(TAG, "goToMoreInfoActivity>> item: " + new GetPrettyPrintJson().getJson(getFood(item)));
        startActivity(intent);
    }


    private FoodProductItem getFood(FavoriteFoodItem item) {
        FoodProductItem foodProductItem = new FoodProductItem();
        foodProductItem.setmFoodName(item.getFoodName());
        foodProductItem.setLike(true);
        foodProductItem.setmFoodImg(item.getFoodImg());
        foodProductItem.setPrice(Double.parseDouble(item.getFoodPrice()));
        foodProductItem.setmFoodTypeName(item.getFoodTypeName());
        foodProductItem.setmIDFood(item.getIDFood());
        foodProductItem.setDetailFoods(item.getDetailFoods());
        return foodProductItem;
//        Log.e(TAG, "onAddFoodToCart>> Post: " + new GetPrettyPrintJson().getJson(foodProductItem));

//        RxBus.get().post(new AddFoodFavoriteToCartEvent(foodProductItem));
    }

    private FavoriteGroup convertFavorite(List<DataFavorite> data) {
        FavoriteGroup favoriteGroup = new FavoriteGroup();
        favoriteGroup.setFavorite(FavoriteConverter.createFavoriteList(data));
        return favoriteGroup;
    }

    private void setFavoriteToAdapter(FavoriteGroup itemGroup) {
        setFavoriteItemToAdapter(itemGroup.getFavorite());
    }

    private void setFavoriteItemToAdapter(List<BaseItem> favorite) {
        favoriteAdapter.setItems(favorite);
        updateEmptyView();
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

    private void requestDelFavorite(int idFood, int idUser) {
        serviceManager.requestDelFavorite(idFood, idUser, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                Log.e(TAG, "requestDelFavorite>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
                updateEmptyView();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestDelFavorite>> onFailure: " + t.getMessage());
            }
        });
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
                        requestGetFavorite(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                        showContent(true);
                    }
                } else {
                    showContent(false);
                }
            }
        };
    }

    private SwipeRefreshLayout.OnRefreshListener onPullRefresh() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                setFavoriteItemGroup(null);

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
        };
    }

    private FavoriteAdapter.OnClickFavoriteListener onClickFavorite() {
        return new FavoriteAdapter.OnClickFavoriteListener() {
            @Override
            public void onClickLike(FavoriteFoodItem item, int position) {

            }

            @Override
            public void onClickUnLike(FavoriteFoodItem item, int position) {
                onRemoveLikeMenu(item);
                LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);
                if (userInfo != null && userInfo.getData() != null) {
                    int idUser = Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer());
                    int idFood = Integer.parseInt(item.getIDFood());
                    requestDelFavorite(idFood, idUser);
                } else {
                    showErrorDialog("คุณยังไม่ได้เข้าสู่ระบบ");
                }
            }

            @Override
            public void onClickItem(FavoriteFoodItem item, int position) {

            }

            @Override
            public void onClickAddToCart(FavoriteFoodItem item, int position) {
                goToMoreInfoActivity(item);
            }

            @Override
            public void onClickAdded(FavoriteFoodItem item, int position) {

            }
        };
    }
}
