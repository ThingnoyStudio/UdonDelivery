package com.thingnoy.thingnoy500v3.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.thingnoy.thingnoy500v3.api.dao.ResProCollectionDao;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

public class ResProManager {

    private Context mContext;
    private ResProCollectionDao dao;

    public ResProManager() {
        mContext = Contextor.getInstance().getContext();

        //Load data from Persistent Storage
        loadCache();

    }

    public ResProCollectionDao getDao() {
        return dao;
    }

    public void setDao(ResProCollectionDao dao) {
        this.dao = dao;
        //Save to Persistent Storage
        saveCache();
    }

//    public int getMaximumId(){
//        if (dao==null){
//            return 0;
//        }
//        if (dao.getData()==null){
//            return 0;
//        }
//        if (dao.getData().size()==0){
//            return 0;
//        }
//
//        int maxId = dao.getData().get(0).getId();
//        for (int i=1;i<dao.getData().size();i++){
//            maxId = Math.max(maxId,dao.getData().get(i).getId());
//        }
//        return maxId;
//    }
//
//    public int getMinimumId(){
//        if (dao==null){
//            return 0;
//        }
//        if (dao.getData()==null){
//            return 0;
//        }
//        if (dao.getData().size()==0){
//            return 0;
//        }
//
//        int minId = dao.getData().get(0).getId();
//        for (int i=1;i<dao.getData().size();i++){
//            minId = Math.min(minId,dao.getData().get(i).getId());
//        }
//        return minId;
//    }

    public int getCount(){
        if (dao==null){
            return 0;
        }
        if (dao.getData()==null){
            return 0;
        }
        return dao.getData().size();
    }

//    public void insertDaoAtTopPosition(PhotoItemCollectionDao newDao){
//        if (dao==null){
//            dao = new PhotoItemCollectionDao();
//        }
//        if (dao.getData()==null){
//            dao.setData(new ArrayList<PhotoItemDao>());
//        }
//        dao.getData().addAll(0,newDao.getData());// insert newDao on oldData
//
//        //Save to Persistent Storage
//        saveCache();
//    }
//
//    public void appendDaoAtBottomPosition(PhotoItemCollectionDao newDao){
//        if (dao==null){
//            dao = new PhotoItemCollectionDao();
//        }
//        if (dao.getData()==null){
//            dao.setData(new ArrayList<PhotoItemDao>());
//        }
//        dao.getData().addAll(dao.getData().size(),newDao.getData());// insert oldDao append currentData
//
//        //Save to Persistent Storage
//        saveCache();
//    }

    public Bundle onSaveInstanceState(){

        Bundle bundle = new Bundle();
        bundle.putParcelable("dao",dao);
        return bundle ;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        dao = savedInstanceState.getParcelable("dao");
    }

    private void saveCache(){
        ResProCollectionDao cacheDao = new ResProCollectionDao();// new dao ขึ้นมาใหม่ แล้วตัดเอาแค่ 20 ตัว
        if (dao != null && dao.getData() != null){
            cacheDao.setData(dao.getData().subList(
                    0,Math.min(20,dao.getData().size())));//ถ้าเกิดมีมากกว่า 20 เอาแค่ 20 ถ้ามีน้อยกว่า 20 ก็เอาเท่าที่มี
        }
        String json = new Gson().toJson(cacheDao);// แปลง obj dao 20 ตัวนั้น เป็น ่json string

        SharedPreferences prefs = mContext.getSharedPreferences("photos",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = prefs.edit();
        //Add/Edit/Delete
        editor.putString("json",json);// เก็บสตริง json ลง เครื่อง
        editor.apply();
    }

    private void loadCache(){
        SharedPreferences prefs = mContext.getSharedPreferences("photos",
                Context.MODE_PRIVATE);
        String json = prefs.getString("json",null);
        if (json ==null){
            return;
        }else {
            dao = new Gson().fromJson(json,ResProCollectionDao.class);//get json คืนมาแล้วแปลงเป็น obj แล้วใส่่ให้ dao
        }
    }
}
