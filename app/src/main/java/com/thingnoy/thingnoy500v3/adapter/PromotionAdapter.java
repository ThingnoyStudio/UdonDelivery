package com.thingnoy.thingnoy500v3.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.promotion.PromotionHolder;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionResultGroup;
import com.thingnoy.thingnoy500v3.api.result.promotion.PromotionDao;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;
import com.thingnoy.thingnoy500v3.util.ItemAnimation;

/**
 * Created by HBO on 2/3/2561.
 */

public class PromotionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private PromotionResultGroup dao;
    private ItemClickListener itemClickListener;
    private int animation_type = 0;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PromotionAdapter() {
        this.dao = new PromotionResultGroup();
    }

    public void setItems(PromotionResultGroup dao, int animation_type) {
        this.dao = dao;
        this.animation_type = animation_type;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Date currentTime = Calendar.getInstance().getTime();
//        Log.e("PromotionAdapter", "onCreateViewHolder |" + currentTime);

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_res_promotion, parent, false);
        return new PromotionHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        Date currentTime = Calendar.getInstance().getTime();
//        Log.e("PromotionAdapter", "onBindViewHolder | " + currentTime);

        PromotionDao dataResProDao = dao.getmData().get(position);
        PromotionHolder promotionHolder = (PromotionHolder) holder;

        promotionHolder.setImageUrl(dataResProDao.getmResImg());
        promotionHolder.tvProName.setText(dataResProDao.getmResPromotionName());
        promotionHolder.tvResProName.setText(dataResProDao.getmResName());
        promotionHolder.tvEndPro.setText("สิ้นสุดโปรโมชั่น: " + dataResProDao.getmResPromotionEnd());
        promotionHolder.tvProDescription.setText("เมื่อสั่งอาหาร " + dataResProDao.getmResLowPrice() + "฿ ชึ้นไป");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onClick(v, position, true);
            }
        });

        setAnimation(promotionHolder.itemView, position);

    }

    @Override
    public int getItemCount() {
//        Date currentTime = Calendar.getInstance().getTime();
        if (dao.getmData() == null) {
//            Log.e("PromotionAdapter", "getItemCount return null |" + currentTime);
            return 0;
        }
        if (dao.getmData().size() <= 0) {
//            Log.e("PromotionAdapter", "getItemCount return 0 |" + currentTime);
            return 0;
        }


//        Log.e("PromotionAdapter", "getItemCount return " + dao.getmData().size() + " |" + currentTime);
        return dao.getmData().size();
    }

    private int lastPosition = -1;
    private boolean on_attach = true;

    private void setAnimation(View view, int position) {
        if (position > lastPosition) {
            ItemAnimation.animate(view, on_attach ? position : -1, animation_type);
            lastPosition = position;
        }
    }
}
