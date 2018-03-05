package com.thingnoy.thingnoy500v3.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.thingnoy.thingnoy500v3.dao.promotion.PromotionCollectionDao;
import com.thingnoy.thingnoy500v3.dao.restaurant.RestaurantCollectionDao;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 4/3/2561.
 */

public class ResMainListManager {
    private Context mContext;
    private PromotionCollectionDao promotionCollectionDao;
    private RestaurantCollectionDao restaurantCollectionDao;

    public ResMainListManager() {
        this.mContext = Contextor.getInstance().getContext();

        //Load data from Persistent Storage
        loadCache();
    }

    public PromotionCollectionDao getPromotionCollectionDao() {
        return promotionCollectionDao;
    }

    public void setPromotionCollectionDao(PromotionCollectionDao promotionCollectionDao) {
        this.promotionCollectionDao = promotionCollectionDao;
        //Save to Persistent Storage
        saveProCache();
    }


    public RestaurantCollectionDao getRestaurantCollectionDao() {
        return restaurantCollectionDao;
    }

    public void setRestaurantCollectionDao(RestaurantCollectionDao restaurantCollectionDao) {
        this.restaurantCollectionDao = restaurantCollectionDao;
        //Save to Persistent Storage
        saveResCache();
    }

    public int getResCount() {
        if (restaurantCollectionDao == null) {
            return 0;
        }
        if (restaurantCollectionDao.getmData() == null) {
            return 0;
        }
        return restaurantCollectionDao.getmData().size();
    }

    public int getProCount() {
        if (promotionCollectionDao == null) {
            return 0;
        }
        if (promotionCollectionDao.getmData() == null) {
            return 0;
        }
        return promotionCollectionDao.getmData().size();
    }

    public Bundle onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("resdao", restaurantCollectionDao);
        bundle.putParcelable("prodao", promotionCollectionDao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        restaurantCollectionDao = savedInstanceState.getParcelable("resdao");
        promotionCollectionDao = savedInstanceState.getParcelable("prodao");
    }

    private void saveProCache() {
        PromotionCollectionDao proCacheDao = new PromotionCollectionDao();
        if (promotionCollectionDao != null && promotionCollectionDao.getmData() != null) {
            proCacheDao.setmData(promotionCollectionDao.getmData().subList(
                    0, Math.min(20, promotionCollectionDao.getmData().size())));//ถ้าเกิดมีมากกว่า 20 เอาแค่ 20 ถ้ามีน้อยกว่า 20 ก็เอาเท่าที่มี
        }
        String projson = new Gson().toJson(proCacheDao);// แปลง obj dao 20 ตัวนั้น เป็น ่json string

        Log.e("saveprojson", "projson: " + projson);

        SharedPreferences prefs = mContext.getSharedPreferences("proShared",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Add/Edit/Delete
        editor.putString("projson", projson);// เก็บสตริง json ลง เครื่อง
        editor.apply();
    }

    private void saveResCache() {
        RestaurantCollectionDao resCacheDao = new RestaurantCollectionDao();// new dao ขึ้นมาใหม่ แล้วตัดเอาแค่ 20 ตัว
        if (restaurantCollectionDao != null && restaurantCollectionDao.getmData() != null) {
            resCacheDao.setmData(restaurantCollectionDao.getmData().subList(
                    0, Math.min(20, restaurantCollectionDao.getmData().size())));//ถ้าเกิดมีมากกว่า 20 เอาแค่ 20 ถ้ามีน้อยกว่า 20 ก็เอาเท่าที่มี
        }
        String resjson = new Gson().toJson(resCacheDao);// แปลง obj dao 20 ตัวนั้น เป็น ่json string
        Log.e("saveresjson", "resjson: " + resjson);

        SharedPreferences prefs = mContext.getSharedPreferences("resShared",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Add/Edit/Delete
        editor.putString("resjson", resjson);// เก็บสตริง json ลง เครื่อง
        editor.apply();
    }

    private void loadCache() {
        SharedPreferences resPrefs = mContext.getSharedPreferences("resShared",
                Context.MODE_PRIVATE);
        SharedPreferences proPrefs = mContext.getSharedPreferences("proShared",
                Context.MODE_PRIVATE);

        String resjson = resPrefs.getString("resjson", null);
        String projson = proPrefs.getString("projson", null);

        Log.e("getresjson", "resjson: " + resjson);
        Log.e("getprojson", "projson: " + projson);

        if (resjson == null || projson == null) {
            this.promotionCollectionDao = new PromotionCollectionDao();
            this.restaurantCollectionDao = new RestaurantCollectionDao();
        } else {
            restaurantCollectionDao = new Gson().fromJson(resjson, RestaurantCollectionDao.class);//get json คืนมาแล้วแปลงเป็น obj แล้วใส่่ให้ dao
            promotionCollectionDao = new Gson().fromJson(projson, PromotionCollectionDao.class);
        }
    }
}
