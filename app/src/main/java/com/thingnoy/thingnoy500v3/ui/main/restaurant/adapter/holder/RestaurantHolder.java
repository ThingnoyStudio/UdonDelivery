package com.thingnoy.thingnoy500v3.ui.main.restaurant.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 4/3/2561.
 */

public class RestaurantHolder extends RecyclerView.ViewHolder {
    public TextView tvResName;
    public TextView tvLowPrice;
    private ImageView ivRes;

    public RestaurantHolder(View itemView) {
        super(itemView);

        tvLowPrice = itemView.findViewById(R.id.tv_price);
        tvResName = itemView.findViewById(R.id.tv_res_name);
        ivRes = itemView.findViewById(R.id.iv_res_all);
    }

    public void setImageUrl(String url) {
        Glide.with(Contextor.getInstance().getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_pic_loading)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivRes);

    }
}
