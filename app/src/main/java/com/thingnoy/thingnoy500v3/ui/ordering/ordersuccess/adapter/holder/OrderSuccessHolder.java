package com.thingnoy.thingnoy500v3.ui.ordering.ordersuccess.adapter.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.StringUtils;

public class OrderSuccessHolder extends RecyclerView.ViewHolder {
    private static final String TAG = OrderSuccessHolder.class.getSimpleName();
    private final TextView tvName;
    private final TextView tvAmount;
    private final TextView tvPrice;
    private final TextView tvAddOn;
    private final TextView tvReason;


    public OrderSuccessHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvAmount = itemView.findViewById(R.id.tv_amount);
        tvPrice = itemView.findViewById(R.id.tv_price);
        tvAddOn = itemView.findViewById(R.id.tv_order_add_on);
        tvReason = itemView.findViewById(R.id.tv_order_reason);
    }

    @SuppressLint("SetTextI18n")
    public void onBind(FoodProductItem item) {
        Log.e(TAG, "onBind: " + new GetPrettyPrintJson().getJson(item));
        if (item != null) {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(item.getmFoodName());
            tvAmount.setText("x" + item.getAmount());
            tvPrice.setText(StringUtils.getCommaPriceWithBaht(itemView.getContext(), Double.parseDouble("" + item.getPrice())));

            if (item.getAddOn() != null) {
                if (item.getAddOn().getFoodDetailName() == null || item.getAddOn().getFoodDetailName().equals("") || item.getAddOn().getFoodDetailName().isEmpty()) {
                    tvAddOn.setVisibility(View.GONE);
                } else {
                    tvAddOn.setVisibility(View.VISIBLE);
                    tvAddOn.setText("เพิ่ม " + item.getAddOn().getFoodDetailName() +
                            "  +" + StringUtils.getCommaPriceWithBaht(itemView.getContext(), Double.parseDouble("" + item.getAddOn().getFoodDetailsPrice())));
                }
            } else {
                tvAddOn.setVisibility(View.GONE);
            }


            if (item.getReason() == null || item.getReason().equals("") || item.getReason().isEmpty()) {
                tvReason.setVisibility(View.GONE);
            } else {
                tvReason.setVisibility(View.VISIBLE);
                tvReason.setText("หมายเหตุ " + item.getReason());
            }
        }

    }

}
