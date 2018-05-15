package com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;
import com.thingnoy.thingnoy500v3.util.StringUtils;

public class HistoryHolder extends RecyclerView.ViewHolder {

    private static final String TAG = HistoryHolder.class.getSimpleName();
    private final TextView tvDate;
    private final TextView tvAmount;
    private final TextView tvTotalPrice;
    private final TextView tvStatus;
    private onClickItemListener listener;

    public interface onClickItemListener {
        void onClickItem(HistoryHolder view, int position);
    }

    public HistoryHolder(View itemView) {
        super(itemView);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvAmount = itemView.findViewById(R.id.tv_beer_amount);
        tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
        tvStatus = itemView.findViewById(R.id.tv_history_status);
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
                    listener.onClickItem(HistoryHolder.this, getAdapterPosition());
                }
            }
        };
    }

    @SuppressLint("SetTextI18n")
    public void onBind(HistoryItem item) {
        Log.e(TAG, "onBind: " + new GetPrettyPrintJson().getJson(item));
        if (item.getOrder() != null) {
            tvDate.setText("" + item.getOrder().getOrderDate());
            tvTotalPrice.setText(item.getOrder().getOrderTotalPrice() + StringUtils.getBahtString(itemView.getContext()));
            tvStatus.setText("สถานะ " + item.getOrder().getOrderStatus());

            if (item.getFoodDetails() != null) {
                tvAmount.setVisibility(View.VISIBLE);
                tvAmount.setText("" + item.getFoodDetails().size() + StringUtils.getListOfFoodString(itemView.getContext()));
            } else {
                tvAmount.setVisibility(View.GONE);
            }

        }
    }


}
