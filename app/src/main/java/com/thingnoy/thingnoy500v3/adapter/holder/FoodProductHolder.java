package com.thingnoy.thingnoy500v3.adapter.holder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.BaseViewHolder;

/**
 * Created by thekhaeng on 4/6/2017 AD.
 */

public class FoodProductHolder
//        extends BaseViewHolder
{
//
//    private ImageView imgFood;
//    private TextView tvFoodName;
//    private TextView tvFoodPrice;
//    private TextView tvTypeName;
//    private TextView tvIDFood;
//    private Button btnAddToCart;
//    private Button btnAdded;
//    private OnClickBeerListener listener;
//
//    public FoodProductHolder(ViewGroup viewGroup) {
//        super(viewGroup, R.layout.holder_food_product);
//    }
//
//    public interface OnClickBeerListener {
//        void onClickAddToCart(FoodProductHolder view, int position);
//
//        void onClickAdded(FoodProductHolder foodProductHolder, int position);
//
//        void onClickItem(FoodProductHolder foodProductHolder, int position);
//    }
//
//    @Override
//    public void bindView(View view) {
//        imgFood = view.findViewById(R.id.img_beer);
//        tvFoodName = view.findViewById(R.id.tv_beer_name);
//        tvTypeName = view.findViewById(R.id.tv_beer_percent);
//        tvIDFood = view.findViewById(R.id.tv_beer_volume);
//        tvFoodPrice = view.findViewById(R.id.tv_beer_price);
//        btnAddToCart = view.findViewById(R.id.btn_add_to_cart);
//        btnAdded = view.findViewById(R.id.btn_added);
//
//        itemView.setOnClickListener(onClickItem());
//        btnAddToCart.setOnClickListener(onClickAddToCart());
//        btnAdded.setOnClickListener(onClickAdded());
//    }
//
//
//    public void setOnClickBeerListener(OnClickBeerListener listener) {
//        this.listener = listener;
//    }
//
//    @SuppressLint("SetTextI18n")
//    public void onBind(BeerProductItem item) {
//
//        setFoodImage(item.getImage());
//        tvFoodName.setText(item.getName());
//        tvTypeName.setText(item.getAlcohol());
//        tvIDFood.setText(item.getVolume());
//        tvFoodPrice.setText(StringUtils.getCommaPriceWithBaht(getContext(), item.getPrice()));
//
//        if (item.isAdded()) {
//            btnAdded.setVisibility(View.VISIBLE);
//            btnAddToCart.setVisibility(View.GONE);
//        } else {
//            btnAdded.setVisibility(View.GONE);
//            btnAddToCart.setVisibility(View.VISIBLE);
//        }
//    }
//
//
//    private void setFoodImage(String url) {
//        Glide.with(getContext())
//                .load(url)
//                .into(imgFood);
//    }
//
//    private void toggleButton() {
//        if (btnAddToCart.getVisibility() == View.VISIBLE) {
//            btnAdded.setVisibility(View.VISIBLE);
//            btnAddToCart.setVisibility(View.GONE);
//        } else {
//            btnAdded.setVisibility(View.GONE);
//            btnAddToCart.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @NonNull
//    private View.OnClickListener onClickAdded() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClickAdded(FoodProductHolder.this,
//                            getAdapterPosition());
//                }
//                toggleButton();
//            }
//        };
//    }
//
//    @NonNull
//    private View.OnClickListener onClickAddToCart() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClickAddToCart(FoodProductHolder.this,
//                            getAdapterPosition());
//                }
//                toggleButton();
//            }
//        };
//    }
//
//    @NonNull
//    private View.OnClickListener onClickItem() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClickItem(FoodProductHolder.this,
//                            getAdapterPosition());
//                }
//            }
//        };
//    }
}
