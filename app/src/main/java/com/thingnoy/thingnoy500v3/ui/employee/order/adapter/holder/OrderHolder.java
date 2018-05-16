package com.thingnoy.thingnoy500v3.ui.employee.order.adapter.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.StringUtils;

public class OrderHolder extends RecyclerView.ViewHolder {

    private static final String TAG = OrderHolder.class.getSimpleName();
    private final TextView tvDate;
    private final TextView tvAmount;
    private final TextView tvTotalPrice;
    private final TextView tvStatus;
    private final TextView tvFullName;
    private final TextView tvPaymentType;
    private onClickItemListener listener;

    public interface onClickItemListener {
        void onClickItem(OrderHolder view, int position);
    }

    public OrderHolder(View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvAmount = itemView.findViewById(R.id.tv_food_amount);

        tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
        tvStatus = itemView.findViewById(R.id.tv_order_status);

        tvFullName = itemView.findViewById(R.id.tv_full_name);
        tvPaymentType = itemView.findViewById(R.id.tv_payment_type);

        itemView.setOnClickListener(onClickItem());
    }

    public void setOnClickItemListener(onClickItemListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener onClickItem() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickItem(OrderHolder.this, getAdapterPosition());
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    public void onBind(OrderItem item) {
        if (item.getOrder() != null) {
            tvDate.setText("" + item.getOrder().getOrderDate());
            tvFullName.setText(item.getOrder().getCustomerFName()+" "+item.getOrder().getCustomerLName());
            tvPaymentType.setText(item.getOrder().getPaymentName());
            tvTotalPrice.setText(item.getOrder().getOrderTotalPrice() + StringUtils.getBahtString(itemView.getContext()));
            tvStatus.setText("สถานะ " + item.getOrder().getOrderStatus());

            if (item.getFood() != null) {
                tvAmount.setVisibility(View.VISIBLE);
                tvAmount.setText("" + item.getFood().size() + StringUtils.getListOfFoodString(itemView.getContext()));
            } else {
                tvAmount.setVisibility(View.GONE);
            }

        }
    }


}
