package com.thingnoy.thingnoy500v3.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.UserAddressAdapter;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;

import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.profile.ProfileResultGroup;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.profile.adapter.ProfileConverter;
import com.thingnoy.thingnoy500v3.ui.profile.adapter.item.AddressItemGroup;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();
    public static final String KEY_HISTORY_GROUP = "key_history_group";

    private UdonFoodServiceManager serviceManager;

    private CircleImageView image;
    private TextView tvName, tvPoint, tvPhone;
    private UserAddressAdapter userAddressAdapter;
    private List<DataUserAddress> dataUserList;
    private RecyclerView rcAddress;
    private CollapsingToolbarLayout collapsingToolbar;
    private View containerServiceUnavailable;
    private AppBarLayout appbar;
    private AppBarLayout.OnOffsetChangedListener mListener;
    private Button btnLogout;
    private LoginResultGroup userInfo;
    private TextView tvAddress;
    private LoginButton btnFaceLogout;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private ProfileTracker profileTracker;
    private Profile profile;
    private View mScrollView;
    private View nested_no_acount;
    private Button btnGoToLogin;
    private View containerGotoLogin;


    public ProfileFragment() {
        super();
        serviceManager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        FacebookSdk.sdkInitialize(getContext());
        AppEventsLogger.activateApp(getContext());
        callbackManager = CallbackManager.Factory.create();

        profileTracker();
        tokenTracker();

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initInstances(rootView, savedInstanceState);
//        RxBus.get().register(this);
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
        setupAppbar();
    }

    private void restoreView(Bundle savedInstanceState) {
        setAddressToAdapter(dataUserList);
    }

    private void initialize() {
//        requestProfile(1);/

        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
                showContent(false);
            } else {
                showContent(true);
                setupUserInfoView();
            }
        } else {
            showContent(false);
        }


    }


    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        userAddressAdapter = new UserAddressAdapter();
        userAddressAdapter.setOnCheckedListener(onSelectAddressListener());
    }

    private UserAddressAdapter.onUserAddressChecked onSelectAddressListener() {
        return new UserAddressAdapter.onUserAddressChecked() {
            @Override
            public void onChecked(DataUserAddress item, int position) {

            }
        };
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        bindView(rootView);

        setupView();
    }


    private void bindView(View rootView) {
        tvName = rootView.findViewById(R.id.tv_profile_name);
        tvPoint = rootView.findViewById(R.id.tv_point);
        tvPhone = rootView.findViewById(R.id.tv_phone);
        rcAddress = rootView.findViewById(R.id.rc_address);
        tvAddress = rootView.findViewById(R.id.tv_address);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        nested_no_acount = rootView.findViewById(R.id.nested_info);
        containerGotoLogin = rootView.findViewById(R.id.container_go_to_login);
        btnGoToLogin = rootView.findViewById(R.id.btn_go_to_login);

        image = rootView.findViewById(R.id.cv_profile);

        mScrollView = rootView.findViewById(R.id.nested_content);

        btnLogout = rootView.findViewById(R.id.btn_logout);
        btnFaceLogout = rootView.findViewById(R.id.login_button);
    }

    private void setupAppbar() {
        appbar = getView().findViewById(R.id.app_bar_layout_profile);
        collapsingToolbar = getView().findViewById(R.id.collapsing_toolbar_profile);
//        image = getView().findViewById(R.id.cv_profile);

        mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (collapsingToolbar.getHeight() + verticalOffset < collapsingToolbar.getScrimVisibleHeightTrigger()) {
//                    appbar.animate().alpha(1).setDuration(600);
//                } else {
//                    appbar.animate().alpha(0).setDuration(600);
//                }
                int min_height = collapsingToolbar.getScrimVisibleHeightTrigger() * 2;
//                int min_height = ViewCompat.getMinimumHeight(collapsingToolbar) * 2;

                float scale = (float) (min_height + verticalOffset) / min_height;

                image.setScaleX(scale >= 0 ? scale : 0);
                image.setScaleY(scale >= 0 ? scale : 0);
            }
        };

        appbar.addOnOffsetChangedListener(mListener);

    }

    private void setupView() {

        loadUserInfoFromCache();

        rcAddress.setHasFixedSize(true);
        rcAddress.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcAddress.setAdapter(userAddressAdapter);

        btnLogout.setOnClickListener(onClickLogout());
        btnGoToLogin.setOnClickListener(onClickGotoLogin());

//         If using in a fragment
        btnFaceLogout.setFragment(this);

    }

    private void loadUserInfoFromCache() {
        userInfo = new LoginResultGroup();
        userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, "" + USERINFO);
    }


    private void showContent(boolean isShow) {
        if (isShow) {
            Log.e(TAG,"isShow: "+ true);
            mScrollView.setVisibility(View.VISIBLE);
            nested_no_acount.setVisibility(View.GONE);
            containerGotoLogin.setVisibility(View.GONE);
        } else {
            Log.e(TAG,"isShow: "+ false);
            clearUserInfo();
            mScrollView.setVisibility(View.GONE);
            nested_no_acount.setVisibility(View.VISIBLE);
            containerGotoLogin.setVisibility(View.VISIBLE);
//            image.setImageResource(0);
            image.setImageResource(R.drawable.ic_pic_loading);
        }

    }

    private void goToLogin() {
        showContent(false);
//        Intent i = new Intent(getContext(), LoginActivity.class);
//        startActivity(i);
    }

    private void setupUserInfoView() {

        if (userInfo != null) {
            setupViewContentFromCache(userInfo);

            if (userInfo.getData().get(0).getAddress() != null) {
                if (userInfo.getData().get(0).getAddress().size()> 0){
                    tvAddress.setVisibility(View.VISIBLE);
                    AddressItemGroup itemGroup = ProfileConverter.createAddressItemFromResult(userInfo.getData().get(0).getAddress());
                    dataUserList = itemGroup.getAddresses();
                    setAddressToAdapter(dataUserList);
                }else {
                    tvAddress.setVisibility(View.GONE);
                }

            } else {
                tvAddress.setVisibility(View.GONE);
            }
        }
    }

    private void clearUserInfo() {
        new CacheManager<String>().clearCache("" + USERINFO);
    }

    @Override
    public void onResume() {
        super.onResume();
        profile = Profile.getCurrentProfile();
        accessToken = AccessToken.getCurrentAccessToken();
//        if (userInfo.getData().size() <= 0) {
//            showContent(false);
//        } else {
//            showContent(true);
//            setupUserInfoView();
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here

    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here

    }

    public void showServiceUnavailableView() {
        containerServiceUnavailable.setVisibility(View.VISIBLE);
    }

    /*
     * Event & Subscribe
     */

    public void requestProfile(int id) {
        serviceManager.requestProfile(id, new UdonFoodServiceManager.UdonFoodManagerCallback<ProfileResultGroup>() {
            @Override
            public void onSuccess(ProfileResultGroup result) {

//                setupViewContent(result);

//                AddressItemGroup itemGroup = ProfileConverter.createAddressItemFromResult(result.getData().get(0).getAddress());
//                dataUserList = itemGroup.getAddresses();
//                setAddressToAdapter(dataUserList);
            }

            @Override
            public void onFailure(Throwable t) {
                showServiceUnavailableView();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupViewContent(ProfileResultGroup result) {
        if (result != null) {
            loadImageProfile(result.getData().get(0).getName().getCustomerImage());
            tvName.setText(result.getData().get(0).getName().getCustomerFName() + " " + result.getData().get(0).getName().getCustomerLName());
            tvPhone.setText(result.getData().get(0).getName().getCustomerPhone());
            tvPoint.setText("แต้มสะสม : " + result.getData().get(0).getName().getCustomerPoint() + " แต้ม");
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupViewContentFromCache(LoginResultGroup result) {
        if (result != null) {
            loadImageProfile(result.getData().get(0).getName().getCustomerImage());
            tvName.setText(result.getData().get(0).getName().getCustomerFName() + " " + result.getData().get(0).getName().getCustomerLName());
            tvPhone.setText(result.getData().get(0).getName().getCustomerPhone());
            tvPoint.setText("แต้มสะสม : " + result.getData().get(0).getName().getCustomerPoint() + " แต้ม");
        }
    }

    private void loadImageProfile(String url) {
        Glide.with(ProfileFragment.this)// โหลดรูป
                .load(url)// โหลดจาก url นี้
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_pic_loading)// กรณี กำลังโหลด
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
                .into(image);// โหลดเข้า imageView ตัวนี้
    }

    private void setAddressToAdapter(List<DataUserAddress> dataUserList) {
        userAddressAdapter.setItems(dataUserList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    private View.OnClickListener onClickLogout() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        };
    }

    private View.OnClickListener onClickGotoLogin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        };
    }

    private void tokenTracker() {
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                Log.e(TAG, "tokenTracker>> oldAccessToken: " + new GetPrettyPrintJson().getJson(oldAccessToken));
                Log.e(TAG, "tokenTracker>> currentAccessToken: " + new GetPrettyPrintJson().getJson(currentAccessToken));


            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
    }

    private void profileTracker() {
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
                Log.e(TAG, "profileTracker>> oldProfile:" + new GetPrettyPrintJson().getJson(oldProfile));
                Log.e(TAG, "profileTracker>> currentProfile:" + new GetPrettyPrintJson().getJson(currentProfile));
                profile = currentProfile;

//                if (userInfo.getData().size() <= 0) {
                    if (profile == null) {// Not has an FaceBook profile
                        showContent(false);
                        btnLogout.setVisibility(View.VISIBLE);
                    } else {
                        showContent(true);
                        btnLogout.setVisibility(View.GONE);
                    }
//                }
//                else {
//                    showContent(true);
//                }

            }
        };
    }
}
