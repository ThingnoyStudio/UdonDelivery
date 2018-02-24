package com.thingnoy.thingnoy500v3.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.dao.PhotoItemCollectionDao;
import com.thingnoy.thingnoy500v3.dao.PhotoItemDao;
import com.thingnoy.thingnoy500v3.dao.PromotionDao;
import com.thingnoy.thingnoy500v3.dao.ResProCollectionDao;
import com.thingnoy.thingnoy500v3.datatype.MutableInteger;
import com.thingnoy.thingnoy500v3.util.Constant;
import com.thingnoy.thingnoy500v3.view.PhotoListItem;

/**
 * Created by HBO on 17/9/2560.
 */

public class ResProAdapter extends BaseAdapter {

    ResProCollectionDao dao;
    MutableInteger lastPositionInteger;
    int lastPosition = -1;

    public ResProAdapter(MutableInteger lastPositionInteger) {
        this.lastPositionInteger = lastPositionInteger;
    }

    public void setDao(ResProCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null)
            return 1;
        if (dao.getData() == null)
            return 1;

        return dao.getData().size() + 1;//เผื่อไว้ให้ progress bar
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        //        return position % 2 == 0 ? 0 : 1;// ถ้าเป็นเลขคู่ photolistitem return 0 จะถือว่า 0 เป็นเลขกำกับ photolistitem
        return position == getCount() - 1 ? 1 : 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {//จะเอาดาต้า แต่ละตำแหน่งมาต้มยำทำแกงก่อนที่จะส่งไปให้ ListView โชว์

        //cast view ใฟ้เป็น progressbar
        if (i == getCount() - 1) {
            //Progress bar
            ProgressBar item;
            if (view != null) {
                item = (ProgressBar) view;
            } else {
                item = new ProgressBar(viewGroup.getContext());
            }
            return item;
        }

        // cast view ให้เป็น Photolistitem
        PhotoListItem item;
        if (view != null) {
            item = (PhotoListItem) view;
        } else {
            item = new PhotoListItem(viewGroup.getContext());
        }


//        int ii = i++;//edit
//        PhotoItemDao dao = (PhotoItemDao) getItem(i);// cast ข้อมูลตำแหน่งที่ i ให้เป็น Object หรือ PhotoItemDao
        DataResProDao dataResProDao = (DataResProDao) getItem(i);

        item.setNameText("#" + dataResProDao.getRestaurantNameDao().getResName());
//        item.setDescriptionText("Promotion : "+dataResProDao.getPromotionDao().get(0).getResPromotionName()+
//                "\n PromotionEnd"+dataResProDao.getPromotionDao().get(0).getResPromotionEnd());
        if (dataResProDao.getPromotionDao() != null) {
            item.setDescriptionText("โปรโมชั่น : " + dataResProDao.getPromotionDao().get(0).getResPromotionName() +
                    "\n สิ้นสุดโปรโมชั่น : " + dataResProDao.getPromotionDao().get(0).getResPromotionEnd());
        }else {
            item.setDescriptionText("ร้านนี้ไม่มีโปรโมชั่น "+ Constant.FACESAD);
        }
        item.setImageUrl(dataResProDao.getRestaurantNameDao().getResImg());

        // Animation
        if (i > lastPositionInteger.getValue()) {
            Animation anim = AnimationUtils.loadAnimation(viewGroup.getContext(),
                    R.anim.up_from_bottom);
            item.startAnimation(anim);
            lastPositionInteger.setValue(i);
        }
        return item;
    }

    public void increaseLastPosition(int amount) {
        lastPositionInteger.setValue(lastPositionInteger.getValue() + amount);
    }
}
