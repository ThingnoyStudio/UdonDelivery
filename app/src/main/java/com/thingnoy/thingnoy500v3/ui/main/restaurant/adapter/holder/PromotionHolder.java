package com.thingnoy.thingnoy500v3.ui.main.restaurant.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.manager.ItemClickListener;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 2/3/2561.
 */

public class PromotionHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView tvResProName, tvProName, tvEndPro, tvProDescription;
    private ImageView ivResImg;
    private ItemClickListener itemClickListener;

    public PromotionHolder(View itemView) {
        super(itemView);
        ivResImg = itemView.findViewById(R.id.iv_res_promotion);
        tvResProName = itemView.findViewById(R.id.tv_res_pro_name);
        tvProName = itemView.findViewById(R.id.tv_proName);
        tvEndPro = itemView.findViewById(R.id.tv_end_time);
        tvProDescription = itemView.findViewById(R.id.tv_pro_description);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public void setImageUrl(String url) {
        Glide.with(Contextor.getInstance().getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_pic_loading)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivResImg);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return true;
    }
}
