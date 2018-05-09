package com.thingnoy.thingnoy500v3.ui.favorite.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;

public class RestaurantHolder extends RecyclerView.ViewHolder {

    public TextView tvSection;

    public RestaurantHolder(View itemView) {
        super(itemView);
        tvSection = itemView.findViewById(R.id.tv_fav_restaurant_name);
    }
}
