package com.thingnoy.thingnoy500v3.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.restaurant.RestaurantHolder;
import com.thingnoy.thingnoy500v3.api.result.new_restaurant.DataRestaurant;
import com.thingnoy.thingnoy500v3.api.result.new_restaurant.NewRestaurantResultGroup;
import com.thingnoy.thingnoy500v3.api.result.restaurant.ResDataDao;
import com.thingnoy.thingnoy500v3.api.result.restaurant.RestaurantResultGroup;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;
import com.thingnoy.thingnoy500v3.util.ItemAnimation;

/**
 * Created by HBO on 4/3/2561.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private NewRestaurantResultGroup dao;
    private ItemClickListener itemClickListener;
    private int animation_type = 0;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RestaurantAdapter() {
        this.dao = new NewRestaurantResultGroup();
    }

    public void setItems(NewRestaurantResultGroup dao, int animation_type) {
        this.dao = dao;
        this.animation_type = animation_type;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_res_list, parent, false);
        return new RestaurantHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        DataRestaurant resDataDao = dao.getData().get(position);
        RestaurantHolder restaurantHolder = (RestaurantHolder) holder;

        restaurantHolder.setImageUrl(resDataDao.getRes().getResImg());
        restaurantHolder.tvLowPrice.setText("ราคาขั้นต่ำ " + resDataDao.getRes().getResLowPrice() + "฿");
        restaurantHolder.tvResName.setText(resDataDao.getRes().getResName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onClick(v, position, false);
            }
        });

        setAnimation(restaurantHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        if (dao.getData() == null)
            return 0;
        if (dao.getData().size() <= 0)
            return 0;
        return dao.getData().size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
//    public interface OnItemClickListener {
//        void onPositiveButtonClick();
//
//        void onNegativeButtonClick();
//    }
}
