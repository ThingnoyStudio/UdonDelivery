package com.thingnoy.thingnoy500v3.ui.bottom.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cazaea.sweetalert.SweetAlertDialog;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.request.add_address.AddAddressBody;
import com.thingnoy.thingnoy500v3.api.request.login.LoginBody;
import com.thingnoy.thingnoy500v3.api.result.locate.Locate;
import com.thingnoy.thingnoy500v3.api.result.locate.LocateResultGroup;
import com.thingnoy.thingnoy500v3.api.result.status.StatusResult;
import com.thingnoy.thingnoy500v3.event.event_address.AddressEvent;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter.AddressAdapter;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;

import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.api.result.profile.ProfileResultGroup;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.authen.login.LoginActivity;
import com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter.ProfileConverter;
import com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter.item.AddressItemGroup;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.ShowToast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class ProfileFragment extends Fragment {
    private static final String TAG = ProfileFragment.class.getSimpleName();
    public static final String KEY_HISTORY_GROUP = "key_history_group";

    private UdonFoodServiceManager serviceManager;

    private CircleImageView image;
    private TextView tvName, tvPoint, tvPhone;
    private AddressAdapter addressAdapter;
    private List<DataUserAddress> dataUserList;
    private RecyclerView rcAddress;
    private CollapsingToolbarLayout collapsingToolbar;
    private View containerServiceUnavailable;
    private AppBarLayout appbar;
    private AppBarLayout.OnOffsetChangedListener mListener;
    private Button btnLogout;
    private LoginResultGroup userInfo;
//    private TextView tvAddress;
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
    private MaterialRippleLayout mrAddAddress;
    private LocateResultGroup locateResultGroup;
    private MaterialSpinner spnMainLocate, spnSubLocate;
    private SweetAlertDialog mDialog;
    private View containerEmpty;
    private View containerAddrHeader;
    private View containerAddrList;


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

//        if (userInfo.getData() != null) {
//            if (userInfo.getData().size() <= 0) {
//                //Not Login
//                showContent(false);
//            } else {
//                LoginBody body = new LoginBody();
//                body.setUsername(userInfo.getData().get(0).getName().getCUsername());
//                body.setPass(userInfo.getData().get(0).getName().getCPasswords());
//                requestLogin(body);
//
//                showContent(true);
//                setupUserInfoView();
//            }
//        } else {
//            showContent(false);
//        }
    }

    private void initialize() {
//        requestProfile(1);/

        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
                showContent(false);
            } else {
                LoginBody body = new LoginBody();
                body.setUsername(userInfo.getData().get(0).getName().getCUsername());
                body.setPass(userInfo.getData().get(0).getName().getCPasswords());
                requestLogin(body);

                showContent(true);
                setupUserInfoView();
            }
        } else {
            showContent(false);
        }


    }


    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        addressAdapter = new AddressAdapter();
        addressAdapter.setOnCheckedListener(onSelectAddressListener());
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        bindView(rootView);

        setupView();
        updateEmptyView();
        updateAddressAndFaceButton();
    }

    private void bindView(View rootView) {
        tvName = rootView.findViewById(R.id.tv_profile_name);
        tvPoint = rootView.findViewById(R.id.tv_point);
        tvPhone = rootView.findViewById(R.id.tv_phone);
        rcAddress = rootView.findViewById(R.id.rc_address);
//        tvAddress = rootView.findViewById(R.id.tv_address);
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
        nested_no_acount = rootView.findViewById(R.id.nested_info);
        containerGotoLogin = rootView.findViewById(R.id.container_go_to_login);
        btnGoToLogin = rootView.findViewById(R.id.btn_go_to_login);

        mrAddAddress = rootView.findViewById(R.id.mr_add_address);
        containerEmpty = rootView.findViewById(R.id.container_empty_address);
        image = rootView.findViewById(R.id.cv_profile);

        mScrollView = rootView.findViewById(R.id.nested_content);

        btnLogout = rootView.findViewById(R.id.btn_logout);
        btnFaceLogout = rootView.findViewById(R.id.login_button);

        containerAddrHeader = rootView.findViewById(R.id.container_address_header);
        containerAddrList = rootView.findViewById(R.id.container_address_list);
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

//        rcAddress.setHasFixedSize(true);
        rcAddress.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        rcAddress.setAdapter(addressAdapter);

        btnLogout.setOnClickListener(onClickLogout());
        btnGoToLogin.setOnClickListener(onClickGotoLogin());

//         If using in a fragment
        btnFaceLogout.setFragment(this);

        mrAddAddress.setOnClickListener(onClickAddAddress());

    }

    private void updateAddressAndFaceButton() {
        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {

            } else {
                if (userInfo.getData().get(0).getName().getLoginType().equals("พนักงานจัดส่ง")){
                    containerAddrHeader.setVisibility(View.GONE);
                    containerAddrList.setVisibility(View.GONE);
                    btnFaceLogout.setVisibility(View.GONE);
                    tvPoint.setVisibility(View.GONE);
                    containerEmpty.setVisibility(View.GONE);
                }else {
                    tvPoint.setVisibility(View.VISIBLE);
                    containerAddrHeader.setVisibility(View.VISIBLE);
                    containerAddrList.setVisibility(View.VISIBLE);
                    btnFaceLogout.setVisibility(View.VISIBLE);
                }

            }
        } else {

        }
    }


    private void loadUserInfoFromCache() {
        userInfo = new LoginResultGroup();
        userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, "" + USERINFO);

        if (userInfo.getData() != null) {
            if (userInfo.getData().size() <= 0) {
                //Not Login
                showContent(false);
            } else {
                LoginBody body = new LoginBody();
                body.setUsername(userInfo.getData().get(0).getName().getCUsername());
                body.setPass(userInfo.getData().get(0).getName().getCPasswords());
                requestLogin(body);

                showContent(true);
                setupUserInfoView();
            }
        } else {
            showContent(false);
        }
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_address);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        spnMainLocate = dialog.findViewById(R.id.spn_main_locate);
        spnSubLocate = dialog.findViewById(R.id.spn_sub_locate);

//        final Button spn_to_date = (Button) dialog.findViewById(R.id.spn_to_date);
//        final Button spn_to_time = (Button) dialog.findViewById(R.id.spn_to_time);
//        final TextView tv_email = (TextView) dialog.findViewById(R.id.tv_email);
        final EditText edtHomeNo = (EditText) dialog.findViewById(R.id.edt_home_no);
//        final EditText et_location = (EditText) dialog.findViewById(R.id.et_location);
//        final AppCompatCheckBox cb_allday = (AppCompatCheckBox) dialog.findViewById(R.id.cb_allday);
//        final AppCompatSpinner spn_timezone = (AppCompatSpinner) dialog.findViewById(R.id.spn_timezone);


//        String[] timezones = getResources().getStringArray(R.array.timezone);

        setDataToMainLocateSpinner(getDataForMainSpinner());
//        spn_timezone.setSelection(0);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressEvent event = new AddressEvent();
                event.homeNo = edtHomeNo.getText().toString();
                event.locateName = getIdLocateFromSubSpinner();
//                event.name = et_name.getText().toString();
//                event.location = et_location.getText().toString();
//                event.from = spn_from_date.getText().toString() + " (" + spn_from_time.getText().toString() + ")";
//                event.to = spn_to_date.getText().toString() + " (" + spn_to_time.getText().toString() + ")";
//                event.is_allday = cb_allday.isChecked();
//                event.timezone = spn_timezone.getSelectedItem().toString();
//                displayDataResult(event);

                if (event.homeNo == null || event.homeNo.equals("")) {
                    Toast.makeText(getContext(), "กรุณาป้อนบ้านเลขที่", Toast.LENGTH_SHORT).show();
                } else if (event.locateName == null || event.locateName.equals("")) {
                    Toast.makeText(getContext(), "กรุณาเลือกที่อยู่", Toast.LENGTH_SHORT).show();
                } else {
                    addNewAddress(event);
                    dialog.dismiss();
                }

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void addNewAddress(AddressEvent event) {
        AddAddressBody body = new AddAddressBody();
        body.setCustomerAddNo(Integer.parseInt(event.homeNo));
        body.setIDCustomer(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
        body.setIDLocation(Integer.parseInt(event.locateName));
        requestAddAddress(body);
    }

    private String getIdLocateFromSubSpinner() {
        String id = null;
        int spnMainIndex = spnMainLocate.getSelectedIndex();
        int spmSubIndex = spnSubLocate.getSelectedIndex();
        if (spnMainIndex != 0) {
            if (spnMainIndex == 1 && spmSubIndex != 0) {
                id = locateResultGroup.getData().getmAlley().get(spmSubIndex - 1).getIDLocation();

            } else if (spnMainIndex == 2 && spmSubIndex != 0) {
                id = locateResultGroup.getData().getmRoad().get(spmSubIndex - 1).getIDLocation();

            } else if (spnMainIndex == 3 && spmSubIndex != 0) {
                id = locateResultGroup.getData().getmVillage().get(spmSubIndex - 1).getIDLocation();
            }
        }
        return id;
    }

    private void setDataToMainLocateSpinner(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Contextor.getInstance().getContext(),
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnMainLocate.setAdapter(adapter);
        spnMainLocate.setOnItemSelectedListener(onSelectMainLocate());
    }

    private void setDataToSubLocateSpinner(ArrayList<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Contextor.getInstance().getContext(),
                android.R.layout.simple_spinner_item,
                list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnSubLocate.setAdapter(adapter);
    }

    private ArrayList<String> getAlley() {
        ArrayList<String> list = new ArrayList<>();
        list.add("เลือกซอย");
        if (locateResultGroup.getData().getmAlley() != null && locateResultGroup.getData().getmAlley().size() > 0) {
            for (Locate locate : locateResultGroup.getData().getmAlley()) {
                list.add(locate.getLocationName());
            }
        }
        return list;

    }

    private ArrayList<String> getRoad() {
        ArrayList<String> list = new ArrayList<>();
        list.add("เลือกถนน");
        if (locateResultGroup.getData().getmRoad() != null && locateResultGroup.getData().getmRoad().size() > 0) {
            for (Locate locate : locateResultGroup.getData().getmRoad()) {
                list.add(locate.getLocationName());
            }
        }
        return list;

    }

    private ArrayList<String> getVillage() {
        ArrayList<String> list = new ArrayList<>();
        list.add("เลือกหมู่บ้าน");
        if (locateResultGroup.getData().getmVillage() != null && locateResultGroup.getData().getmVillage().size() > 0) {
            for (Locate locate : locateResultGroup.getData().getmVillage()) {
                list.add(locate.getLocationName());
            }
        }
        return list;
    }

    private ArrayList<String> getDataForMainSpinner() {

        ArrayList<String> list = new ArrayList<>();
        list.add("เลือกประเภทที่อยู่");
        if (locateResultGroup.getData().getmAlley() != null && locateResultGroup.getData().getmAlley().size() > 0) {
            list.add("ซอย");
        }
        if (locateResultGroup.getData().getmRoad() != null && locateResultGroup.getData().getmRoad().size() > 0) {
            list.add("ถนน");
        }
        if (locateResultGroup.getData().getmVillage() != null && locateResultGroup.getData().getmVillage().size() > 0) {
            list.add("หมู่บ้าน");
        }
        return list;
    }

    private void showProgressDialog(boolean isShow, String title) {
//        SweetAlertDialog mDialog = null;
        if (isShow) {
            mDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            mDialog.setTitleText(title);
            mDialog.setContentText("กรุณารอสักครู่...");
//        mDialog.setConfirmText("ตกลง");
            mDialog.setCancelable(false);
//        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//            @Override
//            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                sweetAlertDialog.dismissWithAnimation();
//                goToLogin(body);
//            }
//        });

            mDialog.show();
        } else {
            mDialog.dismissWithAnimation();
        }

    }

    private void showContent(boolean isShow) {
        if (isShow) {
            Log.e(TAG, "isShow: " + true);
            mScrollView.setVisibility(View.VISIBLE);
            nested_no_acount.setVisibility(View.GONE);
            containerGotoLogin.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "isShow: " + false);
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
                if (userInfo.getData().get(0).getAddress().size() > 0) {
//                    tvAddress.setVisibility(View.VISIBLE);
                    AddressItemGroup itemGroup = ProfileConverter.createAddressItemFromResult(userInfo.getData().get(0).getAddress());
                    dataUserList = itemGroup.getAddresses();
                    setAddressToAdapter(dataUserList);
                }

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
        addressAdapter.setItems(dataUserList);
    }

    public void updateEmptyView() {
        if (hasItems()) {
            containerEmpty.setVisibility(View.GONE);
        } else {
            containerEmpty.setVisibility(View.VISIBLE);
        }
    }

    public boolean hasItems() {
        return addressAdapter.hasItems();
    }


    /*
     * Event & Subscribe
     */

    private void requestLocate() {
        serviceManager.requestGetLocate(new UdonFoodServiceManager.UdonFoodManagerCallback<LocateResultGroup>() {
            @Override
            public void onSuccess(LocateResultGroup result) {
                locateResultGroup = result;

                showCustomDialog();

                showProgressDialog(false, "slkjf");

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void requestAddAddress(AddAddressBody body) {
        serviceManager.requestAddAddress(body, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                Log.e(TAG, "requestAddAddress>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void requestDelAddress(int id, final DataUserAddress item) {
        serviceManager.requestDelAddress(id, new UdonFoodServiceManager.UdonFoodManagerCallback<StatusResult>() {
            @Override
            public void onSuccess(StatusResult result) {
                onRemoveAddress(item);
                Log.e(TAG, "requestDelAddress>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void requestLogin(LoginBody body) {
        serviceManager.requestLogin(body, new UdonFoodServiceManager.UdonFoodManagerCallback<LoginResultGroup>() {
            @Override
            public void onSuccess(LoginResultGroup result) {
                Log.e(TAG, "requestLogin>> onSuccess: " + new GetPrettyPrintJson().getJson(result));

                new CacheManager<LoginResultGroup>().saveCache(result, LoginResultGroup.class, "" + USERINFO);
                userInfo = new LoginResultGroup();
                userInfo = result;

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestLogin>> onFailure: " + t.getMessage());
                showServiceUnavailableView();
            }
        });

    }

    private View.OnClickListener onClickAddAddress() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(true, "กำลังโหลดรายการที่อยู่");
                requestLocate();

            }
        };
    }

    private AddressAdapter.onUserAddressChecked onSelectAddressListener() {
        return new AddressAdapter.onUserAddressChecked() {
            @Override
            public void onChecked(DataUserAddress item, int position) {

            }

            @Override
            public void onClickDeleteAddress(DataUserAddress item, int position) {
                requestDelAddress(item.getmIDCustomerAddress(), item);
                new ShowToast().shortToast("del: no= " + item.getCustomerAddNo() + "addr= " + item.getCustomerAddRoad());
            }
        };
    }

    private void onRemoveAddress(DataUserAddress item) {
        addressAdapter.removeItem(item);
        updateEmptyView();
    }

    private MaterialSpinner.OnItemSelectedListener onSelectMainLocate() {
        return new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (position != 0) {
                    spnSubLocate.setVisibility(View.VISIBLE);
                    int mPosition = position - 1;
                    if (mPosition == 0) {
                        Log.e(TAG, "position: " + mPosition);
                        setDataToSubLocateSpinner(getAlley());

                    } else if (mPosition == 1) {
                        Log.e(TAG, "position: " + mPosition);
                        setDataToSubLocateSpinner(getRoad());

                    } else if (mPosition == 2) {
                        Log.e(TAG, "position: " + mPosition);
                        setDataToSubLocateSpinner(getVillage());

                    }
                } else {
                    spnSubLocate.setVisibility(View.INVISIBLE);
                }

            }
        };
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
                showContent(false);
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
                    btnFaceLogout.setVisibility(View.GONE);
                    btnLogout.setVisibility(View.VISIBLE);
                } else {
                    LoginBody body = new LoginBody();
                    body.setIduserface(profile.getId());
                    body.setToken(accessToken.getToken());
                    requestLogin(body);

                    showContent(true);
                    btnLogout.setVisibility(View.GONE);
                    btnFaceLogout.setVisibility(View.VISIBLE);
                }
//                }
//                else {
//                    showContent(true);
//                }

            }
        };
    }
}
