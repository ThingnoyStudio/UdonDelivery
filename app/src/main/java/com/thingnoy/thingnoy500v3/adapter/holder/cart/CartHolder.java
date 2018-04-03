package com.thingnoy.thingnoy500v3.adapter.holder.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.manager.http.bus.Contextor;

import java.text.DecimalFormat;

public class CartHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private TextView tvPrice;
    private TextView tvAmount;
    private ImageView btnDecrease;
    private ImageView btnIncrease;
    private ImageView btnDelete;
    private OnClickCartHolderListener listener;

    public CartHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_food_name);
        tvPrice = itemView.findViewById(R.id.tv_food_price);
        tvAmount = itemView.findViewById(R.id.tv_beer_amount);
        btnDecrease = itemView.findViewById(R.id.btn_decrease);
        btnIncrease = itemView.findViewById(R.id.btn_increase);
        btnDelete = itemView.findViewById(R.id.btn_delete_item);
        btnDecrease.setOnClickListener(onClickDecrease());
        btnIncrease.setOnClickListener(onClickIncrease());
        btnDelete.setOnClickListener(onClickDelete());
    }

    public interface OnClickCartHolderListener {
        void onClickIncrease(CartHolder view, int position);

        void onClickDecrease(CartHolder view, int position);

        void onClickDelete(CartHolder view, int position);
    }

//    public CartHolder(ViewGroup viewGroup ){
//        super( viewGroup, R.layout.holder_cart );
//    }

    private void setupView() {

    }

    @SuppressLint("SetTextI18n")
    public void onBind(FoodProductItem item) {
        tvName.setText(item.getmFoodName());
        tvPrice.setText(getCommaPriceWithBaht(Contextor.getInstance().getContext(), item.getPrice()));
        tvAmount.setText(item.getAmount() + "");
    }

    private static String getCommaPriceWithBaht(Context context, double price) {
        DecimalFormat formatter = new DecimalFormat("#,###,###.00");
        return formatter.format(price) + "฿";
    }

    public void setOnClickCartHolderListener(OnClickCartHolderListener listener) {
        this.listener = listener;
    }

    @NonNull
    private View.OnClickListener onClickDecrease() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickDecrease(CartHolder.this, getAdapterPosition());
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener onClickIncrease() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickIncrease(CartHolder.this, getAdapterPosition());
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener onClickDelete() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickDelete(CartHolder.this, getAdapterPosition());
                }
            }
        };
    }


}
