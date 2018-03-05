package com.thingnoy.thingnoy500v3.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.promotion.PromotionHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.review.ReviewHolder;
import com.thingnoy.thingnoy500v3.dao.DataResProDao;
import com.thingnoy.thingnoy500v3.dao.promotion.PromotionCollectionDao;
import com.thingnoy.thingnoy500v3.dao.promotion.PromotionDao;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;

/**
 * Created by HBO on 2/3/2561.
 */

public class PromotionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private PromotionCollectionDao dao;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PromotionAdapter() {
        this.dao = new PromotionCollectionDao();
    }

    public void setDao(PromotionCollectionDao dao) {
        this.dao = dao;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_res_promotion, parent, false);
        return new PromotionHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

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

    }

    @Override
    public int getItemCount() {
        if (dao.getmData() == null)
            return 0;
        if (dao.getmData().size() <= 0)
            return 0;
        return dao.getmData().size();
    }
}
