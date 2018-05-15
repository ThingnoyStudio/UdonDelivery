package com.thingnoy.thingnoy500v3.ui.ordering.address;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwangjr.rxbus.RxBus;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.ui.ordering.address.adapter.UserAddressAdapter;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.api.result.userAddress.UserAddressResultGroup;
import com.thingnoy.thingnoy500v3.event.event_ordering.GetAddressFromMapEvent;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectAddressEvent;
import com.thingnoy.thingnoy500v3.ui.ordering.map.MapsActivity;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;


public class AddressFragment extends Fragment {
    private static final String TAG = AddressFragment.class.getSimpleName();
    public static final String EXTRA_PRODUCT_LIST = "extra_product_list";
    public static final int REQ_ORDER = 1000;
    private final UdonFoodServiceManager manager;

    private View llGotoLocal;
    private List<FoodProductItem> items;
    private RecyclerView rcUserAddress;
    private List<DataUserAddress> dataUserList;
    private UserAddressAdapter userAddressAdapter;
    private LoginResultGroup userInfo;

    public AddressFragment() {
        super();
        manager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static AddressFragment newInstance() {
        AddressFragment fragment = new AddressFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_address, container, false);
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
        setAddressToAdapter(dataUserList);
    }

    private void setAddressToAdapter(List<DataUserAddress> dataUserList) {
        userAddressAdapter.setItems(dataUserList);
    }

    private void initialize() {
        userInfo = new LoginResultGroup();
        userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, "" + USERINFO);
        requestUserAddress(Integer.parseInt(userInfo.getData().get(0).getName().getIDCustomer()));
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        userAddressAdapter = new UserAddressAdapter();
        userAddressAdapter.setOnCheckedListener(onUserAddressChecked());
    }


    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        setupInstance();
        rcUserAddress = rootView.findViewById(R.id.rc_user_address);
        rcUserAddress.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));
        rcUserAddress.setAdapter(userAddressAdapter);

        llGotoLocal = rootView.findViewById(R.id.ll_go_to_location);
        llGotoLocal.setOnClickListener(onClickMap());
    }

    public void setupInstance() {
//        items = getActivity().getIntent().getParcelableArrayListExtra(EXTRA_PRODUCT_LIST);
//        if (items == null) {
//            throw new NullPointerException("You must send FoodProductItems to this activity.");
//        }
    }


    @Override
    public void onStart() {
        super.onStart();
//        RxBus.get().register(this);
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

    private void requestUserAddress(int id) {
        manager.requestUserAddress(id, new UdonFoodServiceManager.UdonFoodManagerCallback<UserAddressResultGroup>() {
            @Override
            public void onSuccess(UserAddressResultGroup result) {
                Log.e(TAG, new GetPrettyPrintJson().getJson(result));
                if (result.getData() != null) {
                    dataUserList = result.getData();
                    setAddressToAdapter(dataUserList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestUserAddress:" + t.getMessage());
            }
        });
    }

    private UserAddressAdapter.onUserAddressChecked onUserAddressChecked() {
        return new UserAddressAdapter.onUserAddressChecked() {
            @Override
            public void onChecked(DataUserAddress item, int position) {
//                Toast.makeText(getActivity(), "item: " + item.getCustomerAddRoad() + " position: " + position, Toast.LENGTH_SHORT).show();
                Log.e(TAG, new GetPrettyPrintJson().getJson(userAddressAdapter.getItems()));

                addAddressToAddNewOrderBody(item);
            }
        };
    }

    private void addAddressToAddNewOrderBody(DataUserAddress item) {
        RxBus.get().post(new SelectAddressEvent(item));
    }

    private void onGetAddressFromMap(boolean result) {
        RxBus.get().post(new GetAddressFromMapEvent(result));
    }

    private View.OnClickListener onClickMap() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                i.putParcelableArrayListExtra(EXTRA_PRODUCT_LIST, (ArrayList<? extends Parcelable>) items);
                startActivityForResult(i, REQ_ORDER);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ORDER) {
            if (resultCode == RESULT_OK) {
                Log.e(TAG, "onActivityResult>> requestCode: " + requestCode);
                onGetAddressFromMap(true);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
