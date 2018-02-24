package com.thingnoy.thingnoy500v3.manager;

import android.content.Context;

import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

public class SingletonTemplate {

    private static SingletonTemplate instance;

    public static SingletonTemplate getInstance() {
        if (instance == null)
            instance = new SingletonTemplate();
        return instance;
    }

    private Context mContext;

    private SingletonTemplate() {
        mContext = Contextor.getInstance().getContext();
    }

}
