package com.thingnoy.thingnoy500v3.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.dao.PhotoItemDao;
import com.thingnoy.thingnoy500v3.manager.PhotoListManager;
import com.thingnoy.thingnoy500v3.view.PhotoListItem;

/**
 * Created by HBO on 17/9/2560.
 */

public class PhotoListAdapter extends BaseAdapter {

    PhotoItemCollectionDao dao;
    int lastPosition = -1;

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao==null)
            return 0;
        if (dao.getData()==null)
            return 0;

        return dao.getData().size();
    }

    @Override
    public Object getItem(int i) {
        return dao.getData().get(i);// ดึงข้อมูลตำแหน่งที่ i มาส่งให้ getView
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {//จะเอาดาต้า แต่ละตำแหน่งมาต้มยำทำแกงก่อนที่จะส่งไปให้ ListView โชว์
        PhotoListItem item;
        if (view != null){
            item = (PhotoListItem) view;
        }else {
           item = new PhotoListItem(viewGroup.getContext());
        }

//        int ii = i++;//edit
        PhotoItemDao dao = (PhotoItemDao) getItem(i);// cast ข้อมูลตำแหน่งที่ i ให้เป็น Object หรือ PhotoItemDao
        item.setNameText("#"+dao.getCaption());
        item.setDescriptionText(dao.getUsername()+" : "+dao.getAperture()+"\n"+dao.getCamera());
        item.setImageUrl(dao.getImageUrl());

        if (lastPosition>i){
            Animation animation = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.up_from_bottom);//create anim
            item.startAnimation(animation);// set to view
            lastPosition = i;
        }
        return item;
    }
}
