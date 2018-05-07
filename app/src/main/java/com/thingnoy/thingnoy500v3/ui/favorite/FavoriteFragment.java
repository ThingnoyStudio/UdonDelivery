package com.thingnoy.thingnoy500v3.ui.favorite;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.UserAddressAdapter;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.api.result.profile.ProfileResultGroup;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.ui.profile.adapter.ProfileConverter;
import com.thingnoy.thingnoy500v3.ui.profile.adapter.item.AddressItemGroup;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FavoriteFragment extends Fragment {
    private static final String TAG = FavoriteFragment.class.getSimpleName();
    public static final String KEY_HISTORY_GROUP = "key_history_group";

    private UdonFoodServiceManager serviceManager;
    private View containerServiceUnavailable;


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

    }

    private void initialize() {
//        requestProfile(1);//todo: fix user id
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
        containerServiceUnavailable = rootView.findViewById(R.id.container_service_unavailable);
    }

    private void setupAppbar() {

    }

    private void setupView() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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

    public void requestFavorite(int id) {
//        serviceManager.requestProfile(id, new UdonFoodServiceManager.UdonFoodManagerCallback<ProfileResultGroup>() {
//            @Override
//            public void onSuccess(ProfileResultGroup result) {
//                Log.e(TAG, "requestProfile>> onSuccess: " + new GetPrettyPrintJson().getJson(result));
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                showServiceUnavailableView();
//            }
//        });
    }

}
