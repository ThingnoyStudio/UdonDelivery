package com.thingnoy.thingnoy500v3.ui.employee.orderdetail.adapter.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.result.emporder.Food;
import com.thingnoy.thingnoy500v3.util.StringUtils;

public class OrderDetailHolder extends RecyclerView.ViewHolder {
    private static final String TAG = OrderDetailHolder.class.getSimpleName();
    private final TextView tvName;
    private final TextView tvAmount;
    private final TextView tvPrice;
    private final TextView tvAddOn;
    private final TextView tvReason;


    public OrderDetailHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvAmount = itemView.findViewById(R.id.tv_amount);
        tvPrice = itemView.findViewById(R.id.tv_price);
        tvAddOn = itemView.findViewById(R.id.tv_order_add_on);
        tvReason = itemView.findViewById(R.id.tv_order_reason);
    }

    @SuppressLint("SetTextI18n")
    public void onBind(Food item) {
        if (item != null) {
            tvName.setText(item.getFoodName());
            tvAmount.setText("x" + item.getAmountFood());
            tvPrice.setText(StringUtils.getCommaPriceWithBaht(itemView.getContext(), Double.parseDouble(item.getFoodPrice())));

            if (item.getmFoodDetailName() == null || item.getmFoodDetailName().equals("") || item.getmFoodDetailName().isEmpty()) {
                tvAddOn.setVisibility(View.GONE);
            } else {
                tvAddOn.setVisibility(View.VISIBLE);
                tvAddOn.setText("เพิ่ม " + item.getmFoodDetailName() +
                        "  +" + StringUtils.getCommaPriceWithBaht(itemView.getContext(), Double.parseDouble(item.getmFoodDetailsPrice())));
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
