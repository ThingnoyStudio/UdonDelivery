package com.thingnoy.thingnoy500v3.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.PhotoListAdapter;
import com.thingnoy.thingnoy500v3.adapter.ResProAdapter;
import com.thingnoy.thingnoy500v3.api.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.api.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.api.dao.ResProCollectionDao;
import com.thingnoy.thingnoy500v3.util.datatype.MutableInteger;
import com.thingnoy.thingnoy500v3.manager.ResProManager;
import com.thingnoy.thingnoy500v3.api.HttpManager;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {
    private final static String TAG = MoreInfoFragment.class.getSimpleName();
    ListView listView;
    PhotoListAdapter photoListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ResProAdapter resProAdapter;
    private MutableInteger lastPositionInteger;
    private ResProAdapter listAdapter;
    private ResProManager resProManager;

    public interface FragmentListener {
        void onPhotoItemClicked(DataResProDao dao);
    }

    public MainFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        photoListAdapter = new PhotoListAdapter();

        lastPositionInteger = new MutableInteger(-1);

//        resProAdapter = new ResProAdapter(lastPositionInteger);
        resProManager = new ResProManager();
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here

        listView = rootView.findViewById(R.id.listView);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);

        resProAdapter = new ResProAdapter(lastPositionInteger);

        resProAdapter.setDao(resProManager.getDao());

//        listView.setAdapter(photoListAdapter);// ผูก Adapter กับ listView
        listView.setAdapter(resProAdapter);// ผูก Adapter กับ listView

        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener());

        listView.setOnScrollListener(ScrollListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


//                if (position < resProManager.getCount()) {//กรณีที่ไม่คลิก progressbar อันที่หมุนอยู่ล่างสุดน่ะ
//                    Toast.makeText(getActivity(), "click : " + position, Toast.LENGTH_SHORT).show();
                DataResProDao dao = resProManager.getDao().getData().get(position);

//                    Toast.makeText(getActivity(), "ดาต้า : " + dao.getRestaurantNameDao().getResName(), Toast.LENGTH_SHORT).show();

                FragmentListener listener = (FragmentListener) getActivity();//ส่งสัญญาณไปให้ Activity ทำงาน
                listener.onPhotoItemClicked(dao);
//                }
//                Intent intent = new Intent(getContext(), MoreInfoActivity.class);
//                startActivity(intent);
//                showToast("Position : " + position);
//                Toast.makeText(getActivity(),"Position : " + position,Toast.LENGTH_SHORT).show();
            }
        });

//        loadResPro();
////        loadData();

        if (savedInstanceState == null) {
            refreshData();
        }
    }

    private void refreshData() {
        if (resProManager.getCount() == 0) {
            loadResPro();
        } else {
//            loadResPro();
        }
    }

    @NonNull
    private AbsListView.OnScrollListener ScrollListener() {
        return new AbsListView.OnScrollListener() {// ให้ pull ตอนอยู่บน
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

                swipeRefreshLayout.setEnabled(i == 0);// ให้สามารถ pull to refresh ได้ก็ต่อเมื่ออยู่ตำแหน่งบนสุด
            }
        };
    }

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener swipeRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {//ดัก Event เมื่อ pull to refresh
            @Override
            public void onRefresh() {
//                loadData();
                loadResPro();
            }
        };
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadResPro() {
        Call<ResProCollectionDao> call = HttpManager.getInstance().getService().loadResProlist();
        call.enqueue(new Callback<ResProCollectionDao>() {
            @Override
            public void onResponse(Call<ResProCollectionDao> call, Response<ResProCollectionDao> response) {
                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า  onResponse : " + response.isSuccessful());
                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                if (response.isSuccessful()) {
                    ResProCollectionDao dao = response.body();//รับของ

                    resProManager.setDao(dao);

//                    resProAdapter.setItems(dao);//ยัด dao ให้ Adapter
                    resProAdapter.setDao(resProManager.getDao());//ยัด dao ให้ Adapter ให้ไปเอามาจาก manager แทน
                    resProAdapter.notifyDataSetChanged();// สั่ง adapter ให้บอก ListView ให้ refresh
                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getRestaurantNameDao().getResName());
                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getPromotionDao().get(0).getResPromotionName());
                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getPromotionDao().get(0).getResPromotionEnd());

//                    String dd = dao.getData().get(0).getPromotionDao().get(0).getResPromotionEnd();
//                    Log.e("", "date: " + dd);
//
//                    SimpleDateFormat objSDF = null
//                    ;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        objSDF = new SimpleDateFormat("dd-mm-yyyy", Locale.US);
//                    }
//                    try {
//                        Date dt_1 = null;
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                            dt_1 = objSDF.parse("" + String.valueOf(dd));
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                            Log.e("", "date: " +objSDF.format(dt_1));
//                        }
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

//                    String input = dao.getData().get(0).getPromotionDao().get(0).getResPromotionEnd().toGMTString();
//                    SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
//                    Date date = (Date)formatter.parse(input);
//                    System.out.println(date);

//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(date);
//                    String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +         cal.get(Calendar.YEAR);
//                    System.out.println("formatedDate : " + formatedDate);

//                    Log.e("","input: " + input);
//                    Log.e("", "zdt: " + zdt );
//                    Log.e("", "ld: " + ld );
//                    Log.e( "","output: " + output );

                } else {
                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResProCollectionDao> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + t.toString());
            }
        });
    }

    private void loadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getService().loadPhotolist();
        call.enqueue(new Callback<PhotoItemCollectionDao>() {
            @Override
            public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า  onResponse : " + response.isSuccessful());
                if (response.isSuccessful()) {
                    PhotoItemCollectionDao dao = response.body();//รับของ
//                    PhotoListManager.getInstance().setItems(dao);//ยัด data ให้กับ PhotoListManager เมื่อจะใช้ข้อมูลให้เรียก PhotoListManager แทน
                    photoListAdapter.setDao(dao);//ยัด dao ให้ Adapter
                    photoListAdapter.notifyDataSetChanged();// สั่ง adapter ให้บอก ListView ให้ refresh
                    Toast.makeText(Contextor.getInstance().getContext(), "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getUsername(),
                            Toast.LENGTH_SHORT).show();
                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + dao.getData().get(0).getCaption());
                } else {
                    Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody().toString());
                    Toast.makeText(Contextor.getInstance().getContext(), "เซิฟเวอร์ตอบกลับมาว่า : " + response.errorBody().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);//ซ่อนปุ่ม refresh
                Log.e("MainFragment >>", "เซิฟเวอร์ตอบกลับมาว่า : " + t.toString());
                Toast.makeText(Contextor.getInstance().getContext(), "ไม่สามารถติดต่อเซิฟเวอร์ได้ : " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
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
        outState.putBundle("resProManager",
                resProManager.onSaveInstanceState());

        outState.putBundle("lastPositionInteger",
                lastPositionInteger.onSaveInstanceState());
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
        resProManager.onRestoreInstanceState(savedInstanceState.getBundle("resProManager"));

        lastPositionInteger.onRestoreInstanceState(
                savedInstanceState.getBundle("lastPositionInteger"));
    }

}
