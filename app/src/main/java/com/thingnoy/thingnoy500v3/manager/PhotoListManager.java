package com.thingnoy.thingnoy500v3.manager;

import android.content.Context;

import com.thingnoy.thingnoy500v3.api.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

public class PhotoListManager {

    private static PhotoListManager instance;

    public static PhotoListManager getInstance() {
        if (instance == null)
            instance = new PhotoListManager();
        return instance;
    }

    private Context mContext;
    private PhotoListManager() {
        mContext = Contextor.getInstance().getContext();
    }


    private PhotoItemCollectionDao dao;

    public PhotoItemCollectionDao getDao() {
        return dao;
    }
    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }
}
