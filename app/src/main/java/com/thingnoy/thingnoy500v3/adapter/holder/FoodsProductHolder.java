package com.thingnoy.thingnoy500v3.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

/**
 * Created by HBO on 23/2/2561.
 */

public class FoodsProductHolder extends RecyclerView.ViewHolder {
    public ImageView imgFood;
    public TextView tvFoodName;
    public TextView tvFoodPrice;
    public TextView tvTypeName;
    public TextView tvIDFood;
    public Button btnAddToCart;
    public Button btnAdded;
//    private OnClickFoodListener listener;

    public FoodsProductHolder(View itemView) {
        super(itemView);
        imgFood = itemView.findViewById(R.id.img_beer);
        tvFoodName = itemView.findViewById(R.id.tv_beer_name);
        tvTypeName = itemView.findViewById(R.id.tv_beer_percent);
        tvIDFood = itemView.findViewById(R.id.tv_beer_volume);
        tvFoodPrice = itemView.findViewById(R.id.tv_beer_price);
        btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        btnAdded = itemView.findViewById(R.id.btn_added);
    }
    public void setImageUrl(String url) {
        Glide.with(Contextor.getInstance().getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imgFood);

    }
}
