package com.thingnoy.thingnoy500v3.adapter.holder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.util.StringUtils;

/**
 * Created by HBO on 23/2/2561.
 */

public class FoodsProductHolder extends RecyclerView.ViewHolder {
    private LikeButton btnLike;
    private ImageView imgFood;
    private TextView tvFoodName;
    private TextView tvFoodPrice;
    private TextView tvTypeName;
    private TextView tvIDFood;
    private Button btnAddToCart;
    private Button btnAdded;
    private OnClickFoodListener listener;

    public FoodsProductHolder(View itemView) {
        super(itemView);
        imgFood = itemView.findViewById(R.id.img_food);
        tvFoodName = itemView.findViewById(R.id.tv_food_name);
        tvTypeName = itemView.findViewById(R.id.tv_food_percent);
        tvIDFood = itemView.findViewById(R.id.tv_food_volume);
        tvFoodPrice = itemView.findViewById(R.id.tv_food_price);
        btnAddToCart = itemView.findViewById(R.id.btn_add_to_cart);
        btnAdded = itemView.findViewById(R.id.btn_added);
        btnLike = itemView.findViewById(R.id.btn_favorite_food);

        itemView.setOnClickListener(onClickItem());
        btnAddToCart.setOnClickListener(onClickAddToCart());
        btnAdded.setOnClickListener(onClickAdded());
        btnLike.setOnLikeListener(getLikeListener());
    }

    @NonNull
    private OnLikeListener getLikeListener() {
        return new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                listener.onClickLike(FoodsProductHolder.this, getAdapterPosition());
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                listener.onClickUnLike(FoodsProductHolder.this, getAdapterPosition());
            }
        };
    }

//    private View.OnClickListener onClickFavorite() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null){
////                    if (btnLike.isLiked()){
////                        listener.onClickLike(FoodsProductHolder.this,getAdapterPosition());
////                    }else {
////                        listener.onClickUnLike(FoodsProductHolder.this,getAdapterPosition());
////                    }
//
//                }
//            }
//        };
//    }

    @SuppressLint("SetTextI18n")
    public void onBind(FoodProductItem item) {
        setFoodImg(item.getmFoodImg());
        tvFoodName.setText(item.getmFoodName());
        tvTypeName.setText(item.getmFoodTypeName());
        tvIDFood.setText(item.getmIDFood());
        tvFoodPrice.setText(StringUtils.getCommaPriceWithBaht(Contextor.getInstance().getContext(), item.getPrice()));

        if (item.isAdded()) {
            btnAdded.setVisibility(View.VISIBLE);
            btnAddToCart.setVisibility(View.GONE);
        } else {
            btnAdded.setVisibility(View.GONE);
            btnAddToCart.setVisibility(View.VISIBLE);
        }
    }

    private void toggleButton() {
        if (btnAddToCart.getVisibility() == View.VISIBLE) {
            btnAdded.setVisibility(View.VISIBLE);
            btnAddToCart.setVisibility(View.GONE);
        } else {
            btnAdded.setVisibility(View.GONE);
            btnAddToCart.setVisibility(View.VISIBLE);
        }
    }

    public void setOnClickFoodListener(OnClickFoodListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener onClickAdded() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickAdded(FoodsProductHolder.this, getAdapterPosition());
                }
                toggleButton();
            }
        };
    }

    private View.OnClickListener onClickAddToCart() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickAddToCart(FoodsProductHolder.this, getAdapterPosition());
                }
                toggleButton();
            }
        };
    }

    private View.OnClickListener onClickItem() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickItem(FoodsProductHolder.this, getAdapterPosition());
                }
            }
        };
    }

    public void setFoodImg(String url) {
        Glide.with(Contextor.getInstance().getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_pic_loading)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imgFood);

    }

    public interface OnClickFoodListener {
        void onClickLike(FoodsProductHolder view, int position);

        void onClickUnLike(FoodsProductHolder view, int position);

        void onClickAddToCart(FoodsProductHolder view, int position);

        void onClickAdded(FoodsProductHolder foodsProductHolder, int position);

        void onClickItem(FoodsProductHolder foodsProductHolder, int position);
    }
}
