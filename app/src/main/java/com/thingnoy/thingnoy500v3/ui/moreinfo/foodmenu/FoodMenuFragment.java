package com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.NewFoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.new_restaurant.NewRestaurantResultGroup;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.FoodMenuConverter;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.FoodProductAdapter;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.NewFoodMenuConverter;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItemGroup;
import com.thingnoy.thingnoy500v3.api.dao.NameAndImageDao;
import com.thingnoy.thingnoy500v3.api.request.favorite.AddFavoriteBody;
import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.FoodMenuResultGroup;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.event.event_main.AddFoodToCartEvent;
import com.thingnoy.thingnoy500v3.event.event_main.ClearAddedButtonStateAllEvent;
import com.thingnoy.thingnoy500v3.event.event_main.ClearAddedButtonStateEvent;
import com.thingnoy.thingnoy500v3.event.event_main.RemoveFoodFromCartEvent;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.authen.login.LoginActivity;
import com.thingnoy.thingnoy500v3.util.DownloadImage;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.ItemAnimation;
import com.thingnoy.thingnoy500v3.util.ShowToast;

import java.util.List;

import static android.support.v7.widget.StaggeredGridLayoutManager.*;
import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;

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
    private SweetAlertDialog mDialog;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;

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

        if (savedInstanceState != null) {
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
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
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

        registerShareDialog();

//        topRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        SnapHelper snapHelperTop = new GravitySnapHelper(Gravity.TOP);
//        snapHelperTop.attachToRecyclerView(rcFood);
    }

    private void registerShareDialog() {
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void initialize() {
//        foodAdapter.initDefaultItemForLoadmore();
//        callGetMenuById();
//        requestFoodMenuO(dao.getResId());

        LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);
        if (userInfo != null && userInfo.getData() != null) {
            requestFoodMenu(dao.getResId());
            requestFoodMenuWithLike(dao.getResId(), Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
        } else {
            requestFoodMenu(dao.getResId());
        }


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

    private void onMoreButtonClick(final View view, final FoodProductItem food) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                final String hashtag = "#UdonFoodDelivery" + "เมนู" + food.getmFoodName();
                final String hashtag2 = hashtag.replace(" ", "");
                shareData(food, hashtag2);
                return true;
            }
        });
        popupMenu.inflate(R.menu.menu_food_more);
        popupMenu.show();
    }

    private void shareData(final FoodProductItem food, final String hashTag) {

        Log.e(TAG, "ShareData: " + hashTag);
        DownloadImage downloadImage = new DownloadImage();
        downloadImage.setOnDownloadImageListener(new DownloadImage.onDownloadListener() {
            @Override
            public void onDownloadSuccess(Bitmap bmResult) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bmResult)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .setShareHashtag(new ShareHashtag.Builder()
                                .setHashtag(hashTag)
                                .build())
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                    .build();
                    shareDialog.show(content);
                }
            }
        });
        downloadImage.execute(Uri.parse(food.getmFoodImg()));

//        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
//                .setContentUrl(Uri.parse("URL[will open website or app]"))
//                .setQuote("sdfghjkl")
//                .setShareHashtag(new ShareHashtag.Builder()
//                        .setHashtag("#"+food.getmFoodName())
//                        .build())
//                .build();

//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                    .build();
//            shareDialog.show(shareLinkContent);
//        }
    }


    /*
     * Presenter
     */
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
        Log.e(TAG, "addFoodItemToCart>> post!!");
        RxBus.get().post(new AddFoodToCartEvent(item));
    }

    public void deleteFoodItemFromCart(FoodProductItem item, int position) {
        Log.e(TAG, "deleteFoodItemFromCart>> post!!");
        RxBus.get().post(new RemoveFoodFromCartEvent(item));
    }


    /*
     * Function
     */


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


    private FoodProductItemGroup convertFoodMenu(FoodMenuResultGroup result) {
        String normalMenuTitle = " เมนูอร่อย ";
        String recommendedMenuTitle = " เมนูแนะนำ ";
        FoodProductItemGroup foodMenuResultGroup2 = new FoodProductItemGroup();
        foodMenuResultGroup2.setFoods(
                FoodMenuConverter.createSectionAndOrder(result, recommendedMenuTitle, normalMenuTitle));
        return foodMenuResultGroup2;
    }

    private FoodProductItemGroup convertNewFoodMenu(NewFoodMenuResultGroup result) {
        String normalMenuTitle = " เมนูอร่อย ";
        String recommendedMenuTitle = " เมนูแนะนำ ";
        FoodProductItemGroup foodMenuResultGroup2 = new FoodProductItemGroup();
        foodMenuResultGroup2.setFoods(
                NewFoodMenuConverter.createSectionAndOrder(result, recommendedMenuTitle, normalMenuTitle));
        return foodMenuResultGroup2;
    }

//    private FoodProductItemGroup convertFoodProduct(FoodMenuResultGroupO dao) {
//        String normalMenuTitle = " เมนูอร่อย ";
//        String recommendedMenuTitle = " เมนูแนะนำ ";
////        List<BaseItem> orderFoodList = new ArrayList<>();
////       orderFoodList.addAll(FoodProductConverter.createSectionAndOrder(dao, recommendedMenuTitle, normalMenuTitle));
//        FoodProductItemGroup foodProductItemGroup = new FoodProductItemGroup();
//        foodProductItemGroup.setFoodDetails(
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


    /*
     * Event
     */

    @Subscribe
    public void onClearAddedButtonStateEvent(ClearAddedButtonStateEvent event) {
        Log.e(TAG, "@Subscribe>> onClearAddedButtonStateEvent!!");
        onClearAddedButtonStateEvent(event.getItem());
    }

    @Subscribe
    public void onClearAddedButtonStateAllEvent(ClearAddedButtonStateAllEvent event) {
        Log.e(TAG, "@Subscribe>> onClearAddedButtonStateAllEvent!!");
        onClearAddedButtonAllStateEvent();
    }

    private void requestFoodMenu(int id) {
        serviceManager.requestFoodMenu(id, new UdonFoodServiceManager.UdonFoodManagerCallback<FoodMenuResultGroup>() {
            @Override
            public void onSuccess(FoodMenuResultGroup result) {

                containerProgressbar.setVisibility(View.GONE);
                isHasItem = true;

                itemGroup = convertFoodMenu(result);
                setFoodProductToAdapter(itemGroup);
                updateEmptyCartView();

            }

            @Override
            public void onFailure(Throwable t) {
                new ShowToast().shortToast("requestMenu: failure");
                containerProgressbar.setVisibility(View.GONE);
                isHasItem = false;
                itemGroup = null;

                showServiceUnavailableView();
            }
        });
    }

    private void requestFoodMenuWithLike(int idRestaurant, int idUser) {
        serviceManager.requestGetFoodMuenuWithLike(idRestaurant, idUser, new UdonFoodServiceManager.UdonFoodManagerCallback<NewFoodMenuResultGroup>() {
            @Override
            public void onSuccess(NewFoodMenuResultGroup result) {
                containerProgressbar.setVisibility(View.GONE);
                isHasItem = true;

                itemGroup = convertNewFoodMenu(result);
                setFoodProductToAdapter(itemGroup);
                updateEmptyCartView();

            }

            @Override
            public void onFailure(Throwable t) {
                new ShowToast().shortToast("requestMenu: failure");

                containerProgressbar.setVisibility(View.GONE);
                isHasItem = false;
                itemGroup = null;

                showServiceUnavailableView();
            }
        });
    }

    private void requestAddFavorite(AddFavoriteBody body) {
        serviceManager.requestAddFavorite(body, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                Log.e(TAG, "requestAddFavorite>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestAddFavorite>> onFailure: " + t.getMessage());
            }
        });
    }

    private void requestDelFavorite(int idFood, int idUser) {
        serviceManager.requestDelFavorite(idFood, idUser, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                Log.e(TAG, "requestDelFavorite>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestDelFavorite>> onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private FoodProductAdapter.OnClickFoodProductListener onClickProduct() {
        return new FoodProductAdapter.OnClickFoodProductListener() {
            @Override
            public void onClickLike(FoodProductItem item, int position) {
//                showToast("onClickLike: " + position);
                LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);
                if (userInfo != null && userInfo.getData() != null) {
                    AddFavoriteBody body = new AddFavoriteBody();
                    body.setIdcustomer(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
                    body.setIdfood(Integer.parseInt(item.getmIDFood()));
                    requestAddFavorite(body);
                } else {
                    showErrorDialog("คุณยังไม่ได้เข้าสู่ระบบ");
                }

            }

            @Override
            public void onClickUnLike(FoodProductItem item, int position) {
//                showToast("เลิกถูกใจ: " + position);
                LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);
                if (userInfo != null && userInfo.getData() != null) {
                    int idUser = Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer());
                    int idFood = Integer.parseInt(item.getmIDFood());
                    requestDelFavorite(idFood, idUser);
                } else {
                    showErrorDialog("คุณยังไม่ได้เข้าสู่ระบบ");
                }
            }

            @Override
            public void onClickItem(FoodProductItem item, int position) {
//                showToast("onClickItem: " + position);
//                Log.e(TAG,"onClickItem !!");
            }

            @Override
            public void onClickAddToCart(FoodProductItem item, int position) {
                new ShowToast().shortToast("เพิ่มสินค้าลงในตะกร้าแล้ว");
                Log.e(TAG, "onClickAddToCart !!");
                addFoodItemToCart(item);
            }

            @Override
            public void onClickAdded(FoodProductItem item, int position) {
                new ShowToast().shortToast("นำสินค้าออกจากตะกร้าแล้ว");

                Log.e(TAG, "onClickAdded !!");
                deleteFoodItemFromCart(item, position);
            }

            @Override
            public void onClickMore(View view, FoodProductItem item, int position) {
                onMoreButtonClick(view, item);
            }
        };
    }
}
