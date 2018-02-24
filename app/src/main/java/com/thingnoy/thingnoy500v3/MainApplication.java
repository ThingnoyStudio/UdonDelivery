package com.thingnoy.thingnoy500v3;

import android.app.Application;

import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 15/4/2560.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        Initialize thing(s) here
        Contextor.getInstance().init(getApplicationContext());//โยน applicationContext()ไปเก็บไว้ที่ Contextor

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
