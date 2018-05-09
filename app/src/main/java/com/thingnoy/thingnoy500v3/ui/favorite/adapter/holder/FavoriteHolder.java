package com.thingnoy.thingnoy500v3.ui.favorite.adapter.holder;

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
import com.thingnoy.thingnoy500v3.api.result.login.LoginResultGroup;
import com.thingnoy.thingnoy500v3.manager.CacheManager;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;
import com.thingnoy.thingnoy500v3.ui.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.util.StringUtils;

import static com.thingnoy.thingnoy500v3.util.Constant.USERINFO;

/**
 * Created by HBO on 23/2/2561.
 */

public class FavoriteHolder extends RecyclerView.ViewHolder {
    private LikeButton btnLike;
    private ImageView imgFood;
    private TextView tvFoodName;
    private TextView tvFoodPrice;
    private TextView tvTypeName;
    private TextView tvIDFood;
    private Button btnAddToCart;
    private Button btnAdded;
    private OnClickFoodListener listener;

    public FavoriteHolder(View itemView) {
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
                LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);
                if (userInfo != null && userInfo.getData() != null) {
                    listener.onClickLike(FavoriteHolder.this, getAdapterPosition());
                } else {
//                    showErrorDialog("คุณยังไม่ได้เข้าสู่ระบบ");
                    listener.onClickLike(FavoriteHolder.this, getAdapterPosition());
                    btnLike.setLiked(false);
                }

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                LoginResultGroup userInfo = new CacheManager<LoginResultGroup>().loadCache(LoginResultGroup.class, USERINFO);
                if (userInfo != null && userInfo.getData() != null) {
                    listener.onClickUnLike(FavoriteHolder.this, getAdapterPosition());
                } else {
//                    showErrorDialog("คุณยังไม่ได้เข้าสู่ระบบ");
                    listener.onClickUnLike(FavoriteHolder.this, getAdapterPosition());
                    btnLike.setLiked(true);
                }

            }
        };
    }

    @SuppressLint("SetTextI18n")
    public void onBind(FavoriteFoodItem item) {
        setFoodImg(item.getFoodImg());
        tvFoodName.setText(item.getFoodName());
        tvTypeName.setText(item.getFoodTypeName());
        tvIDFood.setText(item.getIDFood());
        tvFoodPrice.setText(StringUtils.getCommaPriceWithBaht(Contextor.getInstance().getContext(), Double.parseDouble(item.getFoodPrice())));

        btnLike.setLiked(true);

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
                    listener.onClickAdded(FavoriteHolder.this, getAdapterPosition());
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
                    listener.onClickAddToCart(FavoriteHolder.this, getAdapterPosition());
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
                    listener.onClickItem(FavoriteHolder.this, getAdapterPosition());
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
        void onClickLike(FavoriteHolder view, int position);

        void onClickUnLike(FavoriteHolder view, int position);

        void onClickAddToCart(FavoriteHolder view, int position);

        void onClickAdded(FavoriteHolder view, int position);

        void onClickItem(FavoriteHolder view, int position);
    }
}
