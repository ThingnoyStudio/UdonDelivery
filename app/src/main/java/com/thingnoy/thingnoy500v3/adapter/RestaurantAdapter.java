package com.thingnoy.thingnoy500v3.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.promotion.PromotionHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.restaurant.RestaurantHolder;
import com.thingnoy.thingnoy500v3.dao.restaurant.ResDataDao;
import com.thingnoy.thingnoy500v3.dao.restaurant.RestaurantCollectionDao;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;
import com.thingnoy.thingnoy500v3.util.Constant;

/**
 * Created by HBO on 4/3/2561.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RestaurantCollectionDao dao;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RestaurantAdapter() {
        this.dao = new RestaurantCollectionDao();
    }

    public void setDao(RestaurantCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_res_list, parent, false);
        return new RestaurantHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ResDataDao resDataDao = dao.getmData().get(position);
        RestaurantHolder restaurantHolder = (RestaurantHolder) holder;

        restaurantHolder.setImageUrl(resDataDao.getmResImg());
        restaurantHolder.tvLowPrice.setText("ราคาขั้นต่ำ " + resDataDao.getmResLowPrice() + "฿");
        restaurantHolder.tvResName.setText(resDataDao.getmResName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onClick(v, position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dao.getmData() == null)
            return 0;
        if (dao.getmData().size() <= 0)
            return 0;
        return dao.getmData().size();
    }
//    public interface OnItemClickListener {
//        void onPositiveButtonClick();
//
//        void onNegativeButtonClick();
//    }
}
