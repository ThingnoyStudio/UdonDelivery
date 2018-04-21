package com.thingnoy.thingnoy500v3.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.api.UdonFoodServiceManager;
import com.thingnoy.thingnoy500v3.util.CustomPolygonOptionData;


import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private final static String TAG = MapsActivity.class.getSimpleName();
    public static final String EXTRA_PRODUCT_LIST = "extra_product_list";
    private static final String EXTRA_PRODUCT_LIST_TEST = "extra_product_list_test";

    public static final int DEFAULT_ZOOM = 16;
    private GoogleMap mMap;
    private ImageView btnBack;
    SupportMapFragment mapFragment;
    private Button btnOrder;
    private CardView cardLoading;
    private List<FoodProductItem> items;
    private UdonFoodServiceManager serviceManager;
    private Button btnAddAddr,btnSelectAddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        bindView();
        setupInstance();
        setupView();

    }

    private void setupView() {
        serviceManager = UdonFoodServiceManager.getInstance();

        btnBack.setOnClickListener(onClickBlack());
        btnOrder.setOnClickListener(onClickOrder());
        btnAddAddr.setOnClickListener(onClickAddAddr());
        btnSelectAddr.setOnClickListener(onClickSelectAddr());

    }

    private View.OnClickListener onClickSelectAddr() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private View.OnClickListener onClickAddAddr() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    private void bindView() {
        btnBack = findViewById(R.id.btn_back);
        btnOrder = findViewById(R.id.btn_order);
        cardLoading = findViewById(R.id.card_loading);

        btnSelectAddr = findViewById(R.id.btn_select_address);
        btnAddAddr = findViewById(R.id.btn_add_address);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
    }

    public void setupInstance() {
         items = getIntent().getParcelableArrayListExtra(EXTRA_PRODUCT_LIST);
        if (items == null) {
            throw new NullPointerException("You must send FoodProductItems to this activity.");
        }
        checkMapPermissionAndStartMap();
    }

    private void checkMapPermissionAndStartMap() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (!hasDeniedPermission(report)) {
                            startMap();
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest(); // show request dialog
                    }

                    private boolean hasDeniedPermission(MultiplePermissionsReport report) {
                        List<PermissionDeniedResponse> denyPermission = report.getDeniedPermissionResponses();
                        return denyPermission != null && denyPermission.size() > 0;
                    }

                }).check();
    }

    private void startMap() {
        if (isLocationEnable()) {
            mapFragment.getMapAsync(this);
        } else {
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(myIntent);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (isLocationEnable()) {
            Location location = getLastKnownLocation();
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng current = new LatLng(latitude,longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, DEFAULT_ZOOM));

//                mMap.addPolygon(new CustomPolygonOptionData().getPolygonOptions());
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current,DEFAULT_ZOOM));
            }
        }
        setupMap();

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMapPermissionAndStartMap();
    }

    @Override
    protected void onDestroy() {
        cancelRequest();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        cancelRequest();
        super.onBackPressed();
    }

    public void goToSuccessOrderActivity() {
//        Intent i = new Intent( this, SuccessOrderActivity.class );
//        startActivity( i );
//        setResult( Activity.RESULT_OK );
//        finish();
    }

    public void clearButtonLoadingState() {
        btnOrder.setVisibility(View.VISIBLE);
        cardLoading.setVisibility(View.GONE);
    }

    private void setupMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private Location getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        LocationManager locationManager =
                (LocationManager) this.getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l; // Found best last known location;
            }
        }
        return bestLocation;
    }

    private boolean isLocationEnable() {
        LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private LatLng getCenterLatLngPosition() {
        return mMap.getCameraPosition().target;
    }

    private void showButtonLoadingState() {
        btnOrder.setVisibility(View.GONE);
        cardLoading.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener onClickOrder() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButtonLoadingState();
                LatLng latLng = getCenterLatLngPosition();
                Log.e(TAG, "LatLng: " + latLng);
//                getPresenter().requestAddNewOrder( latlng.latitude, latlng.longitude, items );

            }
        };
    }

    private View.OnClickListener onClickBlack() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }

    /***************
     * Presenter
     */

    public void goToSuccessOrderActivityP() {
        goToSuccessOrderActivity();
    }


    public void requestAddNewOrder( double latitude, double longitude, List<FoodProductItem> items){
//        serviceManager.( latitude, longitude, items, new NongBeerServiceManager.NongBeerManagerCallback<AddNewOrderResult>(){
//            @Override
//            public void onSuccess( AddNewOrderResult result ){
//                goToSuccessOrderActivityP();
//            }
//
//            @Override
//            public void onFailure( Throwable t ){
//                showFailMessage();
//            }
//        } );
    }
    public void showFailMessage(){
        clearButtonLoadingState();
        Toast.makeText( this, "Cannot order beer.", Toast.LENGTH_SHORT ).show();
    }
    public void cancelRequest() {
        clearButtonLoadingState();
//        manager.cancelAddNewOrder();
    }
}
