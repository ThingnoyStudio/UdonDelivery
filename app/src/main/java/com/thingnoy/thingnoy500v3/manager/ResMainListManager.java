package com.thingnoy.thingnoy500v3.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 4/3/2561.
 */

public class ResMainListManager {
    private Context mContext;
    private PromotionResultGroup promotionResultGroup;
    private RestaurantResultGroup restaurantResultGroup;

    public ResMainListManager() {
        this.mContext = Contextor.getInstance().getContext();

//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(0)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
////                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
//                .tag("ResMainListManager")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();
//
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        //Load data from Persistent Storage
        loadCache();
    }

    public PromotionResultGroup getPromotionResultGroup() {
        return promotionResultGroup;
    }

    public void setPromotionResultGroup(PromotionResultGroup promotionResultGroup) {
        this.promotionResultGroup = promotionResultGroup;
        //Save to Persistent Storage
        saveProCache();
    }


    public RestaurantResultGroup getRestaurantResultGroup() {
        return restaurantResultGroup;
    }

    public void setRestaurantResultGroup(RestaurantResultGroup restaurantResultGroup) {
        this.restaurantResultGroup = restaurantResultGroup;
        //Save to Persistent Storage
        saveResCache();
    }

    public int getResCount() {
        if (restaurantResultGroup == null) {
            return 0;
        }
        if (restaurantResultGroup.getmData() == null) {
            return 0;
        }
        return restaurantResultGroup.getmData().size();
    }

    public int getProCount() {
        if (promotionResultGroup == null) {
            return 0;
        }
        if (promotionResultGroup.getmData() == null) {
            return 0;
        }
        return promotionResultGroup.getmData().size();
    }

    public Bundle onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("resdao", restaurantResultGroup);
        bundle.putParcelable("prodao", promotionResultGroup);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        restaurantResultGroup = savedInstanceState.getParcelable("resdao");
        promotionResultGroup = savedInstanceState.getParcelable("prodao");
    }

    private void saveProCache() {
        PromotionResultGroup proCacheDao = new PromotionResultGroup();
        if (promotionResultGroup != null && promotionResultGroup.getmData() != null) {
            proCacheDao.setmData(promotionResultGroup.getmData().subList(
                    0, Math.min(20, promotionResultGroup.getmData().size())));//ถ้าเกิดมีมากกว่า 20 เอาแค่ 20 ถ้ามีน้อยกว่า 20 ก็เอาเท่าที่มี
        }
        String projson = new Gson().toJson(proCacheDao);// แปลง obj dao 20 ตัวนั้น เป็น ่json string

        Log.e("saveProCache", "projson: " + projson);
        Logger.json(projson);

        SharedPreferences prefs = mContext.getSharedPreferences("proShared",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Add/Edit/Delete
        editor.putString("projson", projson);// เก็บสตริง json ลง เครื่อง
        editor.apply();
    }

    private void saveResCache() {
        RestaurantResultGroup resCacheDao = new RestaurantResultGroup();// new dao ขึ้นมาใหม่ แล้วตัดเอาแค่ 20 ตัว
        if (restaurantResultGroup != null && restaurantResultGroup.getmData() != null) {
            resCacheDao.setmData(restaurantResultGroup.getmData().subList(
                    0, Math.min(20, restaurantResultGroup.getmData().size())));//ถ้าเกิดมีมากกว่า 20 เอาแค่ 20 ถ้ามีน้อยกว่า 20 ก็เอาเท่าที่มี
        }
        String resjson = new Gson().toJson(resCacheDao);// แปลง obj dao 20 ตัวนั้น เป็น ่json string
        Log.e("saveResCache", "resjson: " + resjson);
        Logger.json(resjson);

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

        Log.e("loadCache", "resjson: " + resjson);
        Log.e("loadCache", "projson: " + projson);

        if (resjson == null || projson == null) {
            this.promotionResultGroup = new PromotionResultGroup();
            this.restaurantResultGroup = new RestaurantResultGroup();
        } else {
            restaurantResultGroup = new Gson().fromJson(resjson, RestaurantResultGroup.class);//get json คืนมาแล้วแปลงเป็น obj แล้วใส่่ให้ dao
            promotionResultGroup = new Gson().fromJson(projson, PromotionResultGroup.class);
        }
    }
}
