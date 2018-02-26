package com.thingnoy.thingnoy500v3.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.PageAdapter;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.view.SlidingTabLayout;

public class MoreInfoFragment extends Fragment {

    private DataResProDao dao;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager2;
    private android.support.v7.widget.Toolbar toobar;
    private ImageView image;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    public MoreInfoFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MoreInfoFragment newInstance(DataResProDao dao) {//รับ dao
        MoreInfoFragment fragment = new MoreInfoFragment();
        Bundle args = new Bundle();

        //รับ dao
        args.putParcelable("dao", dao);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        dao = getArguments().getParcelable("dao");
//        Toast.makeText(getActivity(),dao.getPromotionDao().get(0).getResPromotionEnd().toString(),Toast.LENGTH_SHORT).show();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more_info, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

        setHasOptionsMenu(true);// ขออนุญาต สร้างเมนู

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        collapsingToolbarLayout = rootView.findViewById(R.id.collapsing_toolbar);
        //set title bar
        collapsingToolbarLayout.setTitle(dao.getRestaurantNameDao().getResName());

        toobar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toobar);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = rootView.findViewById(R.id.image);
        viewPager2 = rootView.findViewById(R.id.viewPager2);
        tabLayout = rootView.findViewById(R.id.tabLayout);

        loadImageAppBar();

        tabLayout.setupWithViewPager(viewPager2);
        setUpViewPager(viewPager2);
        setUpTabIcon();

//        loadFoodProduct();


//        viewPager = rootView.findViewById(R.id.viewPager);
//        viewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                switch (position){
//                    case 0:
//                        return ResFoodMenuFragment.newInstance(dao);
//                    case 1:
//                        return ResInfoFragment.newInstance(dao);
//                    default:
//                        return null;
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return 2;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                switch (position){
//                    case 0:
//                        return "ร้าน";
//                    case 1:
//                        return "รายละเอียด";
////                    case 2:
////                        return "Tags";
//                    default:
//                        return "";
//                }
//            }
//        });
//
//        slidingTabLayout = rootView.findViewById(R.id.slidingTabLayout);
//        slidingTabLayout.setViewPager(viewPager);

    }



//    private void loadFoodProduct() {
//        Call<FoodProductCollectionDao> call = HttpManager.getInstance()
//                .getService()
//                .getFoodById(dao.getRestaurantNameDao().getIDRestaurant());
//        call.enqueue(new Callback<FoodProductCollectionDao>() {
//            @Override
//            public void onResponse(Call<FoodProductCollectionDao> call, Response<FoodProductCollectionDao> response) {
//                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า  onResponse : " + response.isSuccessful());
////                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
//                if (response.isSuccessful()) {
//                    FoodProductCollectionDao dao = response.body();//รับของ
//
//                    //setup FoodOrder
//                    setFoodProduct(dao);
//
////                    resProManager.setDao(dao);
//
////                    resProAdapter.setDao(dao);//ยัด dao ให้ Adapter
////                    resProAdapter.setDao(resProManager.getDao());//ยัด dao ให้ Adapter ให้ไปเอามาจาก manager แทน
////                    resProAdapter.notifyDataSetChanged();// สั่ง adapter ให้บอก ListView ให้ refresh
////                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getRestaurantNameDao().getResName());
////                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getPromotionDao().get(0).getResPromotionName());
////                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getPromotionDao().get(0).getResPromotionEnd());
//
//                } else {
//                    Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FoodProductCollectionDao> call, Throwable t) {
////                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
//                Log.e("MoreInfoFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + t.toString());
//            }
//        });
//    }

    private void setUpTabIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_food);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_open);
    }
    private void setUpViewPager(ViewPager viewPager2) {
        PageAdapter pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager(),2);
        pagerAdapter.addFragmentPage(ResFoodMenuFragment.newInstance(dao),
                "Foods");
        pagerAdapter.addFragmentPage(ResInfoFragment.newInstance(dao),
                "Restaurant");
        viewPager2.setAdapter(pagerAdapter);
    }
    private void loadImageAppBar() {
        Glide.with(MoreInfoFragment.this)// โหลดรูป
                .load(dao.getRestaurantNameDao().getResImg())// โหลดจาก url นี้
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)// กรณี กำลังโหลด
                        .diskCacheStrategy(DiskCacheStrategy.ALL)) //เก็บลงแคช ทุกชนาด
                .into(image);// โหลดเข้า imageView ตัวนี้
    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
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

}
