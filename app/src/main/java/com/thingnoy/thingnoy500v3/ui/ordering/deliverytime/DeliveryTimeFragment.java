package com.thingnoy.thingnoy500v3.ui.ordering.deliverytime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hwangjr.rxbus.RxBus;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.result.derivery_time.DataDeliveryTime;
import com.thingnoy.thingnoy500v3.api.result.derivery_time.DeliverTimeResultGroup;
import com.thingnoy.thingnoy500v3.event.event_ordering.SelectDeliveryTimeEvent;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;


public class DeliveryTimeFragment extends Fragment {
    private static final String TAG = DeliveryTimeFragment.class.getSimpleName();
    public static final String EXTRA_PRODUCT_LIST = "extra_product_list";
    public static final int REQ_ORDER = 1000;
    private final UdonFoodServiceManager manager;

    private List<DataDeliveryTime> dataDeliveryTimeList;
    private MaterialSpinner spn_delivery_time;
    private ArrayList<String> listTime;
    private ArrayAdapter<String> adapter;

    public DeliveryTimeFragment() {
        super();
        manager = UdonFoodServiceManager.getInstance();
    }

    @SuppressWarnings("unused")
    public static DeliveryTimeFragment newInstance() {
        DeliveryTimeFragment fragment = new DeliveryTimeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_delivery_time, container, false);
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
//        setDeliveryTimeToSpinner(dataDeliveryTimeList);
    }



    private void initialize() {
        requestDeliveryTime();
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
//        userAddressAdapter = new UserAddressAdapter();
//        userAddressAdapter.setOnCheckedListener(onUserAddressChecked());
    }


    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        spn_delivery_time = rootView.findViewById(R.id.spn_delivery_time);

        spn_delivery_time.setOnItemSelectedListener(onDeliveryTimeSelected());
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
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

    private void setDeliveryTimeToSpinner(List<DataDeliveryTime> deliveryTimes) {

        if (deliveryTimes != null && deliveryTimes.size() > 0) {
            listTime = new ArrayList<>();
            listTime.add("ระบุเวลาจัดส่ง");

            for (int i = 0; i < deliveryTimes.size(); i++) {
                listTime.add(deliveryTimes.get(i).getDeliveryTime() + " น.");
            }

            adapter = new ArrayAdapter<>(
                    Contextor.getInstance().getContext(),
                    android.R.layout.simple_spinner_item,
                    listTime);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spn_delivery_time.setAdapter(adapter);

//            Log.e(TAG, "listTime: " + new GetPrettyPrintJson().getJson(listTime));

        }
    }



    private void requestDeliveryTime() {
        manager.requestDeliveryTime(new UdonFoodServiceManager.UdonFoodManagerCallback<DeliverTimeResultGroup>() {
            @Override
            public void onSuccess(DeliverTimeResultGroup result) {
                Log.e(TAG, new GetPrettyPrintJson().getJson(result));
                if (result.getData() != null) {
                    dataDeliveryTimeList = result.getData();
                    setDeliveryTimeToSpinner(dataDeliveryTimeList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "requestDeliveryTime:" + t.getMessage());
            }
        });
    }

    private MaterialSpinner.OnItemSelectedListener onDeliveryTimeSelected() {
        return new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if (position == 0) {
                    DataDeliveryTime deliveryTime = new DataDeliveryTime();
                    deliveryTime.setIDDeliveryTime(-1);
                    deliveryTime.setDeliveryTime("");

                    addDeliveryTimeToAddNewOrderBody(deliveryTime);
                } else {
                    DataDeliveryTime deliveryTime = new DataDeliveryTime();
                    deliveryTime.setIDDeliveryTime(dataDeliveryTimeList.get(position - 1).getIDDeliveryTime());
                    deliveryTime.setDeliveryTime(dataDeliveryTimeList.get(position - 1).getDeliveryTime());

                    addDeliveryTimeToAddNewOrderBody(deliveryTime);
                }

            }
        };
    }

    private void addDeliveryTimeToAddNewOrderBody(DataDeliveryTime item) {
        RxBus.get().post(new SelectDeliveryTimeEvent(item));
    }

}
