package com.thingnoy.thingnoy500v3.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;


public class CacheManager<T> {
    private Object myObject;
    private Class<T> cls;
    private String key;
    private String prefsName = "prefsName";
    private T t;

//    public CacheManager(String key) {
//        this.key = key;
//    }
//
//    public CacheManager(Class<T> cls, String key) {
//        this.key = key;
//        this.cls = cls;
//    }
//
//    public CacheManager(Object myObject, Class<T> cls, String key) {
//        this.myObject = myObject;
//        this.cls = cls;
//        this.key = key;
//    }

    public void saveCache(Object myObject, Class<T> cls, String key) {
        this.myObject = myObject;
        this.cls = cls;
        this.key = key;

        String objJson = new Gson().toJson(myObject);// แปลง obj dao 20 ตัวนั้น เป็น ่json string

        SharedPreferences prefs = Contextor.getInstance().getContext().getSharedPreferences("" + prefsName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Add/Edit/Delete
        editor.putString("" + key, objJson);// เก็บสตริง json ลง เครื่อง
        editor.apply();
    }

    public <t> T loadCache(Class<T> cls, String key) {
        this.key = key;
        this.cls = cls;

        T mObject = newInstance(cls);
        SharedPreferences prefs = Contextor.getInstance().getContext().getSharedPreferences("" + prefsName,
                Context.MODE_PRIVATE);

        String objJson = prefs.getString("" + key, null);

        if (objJson != null) {
            mObject = new Gson().fromJson(objJson, cls);//get json คืนมาแล้วแปลงเป็น obj แล้วใส่่ให้ dao
        }
        return mObject;
    }

    public void clearCache(String key){
        this.key = key;


        SharedPreferences prefs = Contextor.getInstance().getContext().getSharedPreferences("" + prefsName,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        //Add/Edit/Delete
//        editor.clear();
        editor.remove(key);
        editor.apply();
//        editor.putString("" + key, objJson);// เก็บสตริง json ลง เครื่อง
//        editor.apply();
    }

    private <T> T newInstance(Class<T> cls) {
        T myObject = null;
        try {
            myObject = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return myObject;
    }
}
