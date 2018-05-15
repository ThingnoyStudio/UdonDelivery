package com.thingnoy.thingnoy500v3.ui.ordering.address.adapter.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;

public class UserAddressHolder extends RecyclerView.ViewHolder {
    private final TextView tvAddr1;
    private final TextView tvAddr2;
    private final RadioButton radAddr;
    private onRadCheckedListener listener;

    public UserAddressHolder(View itemView) {
        super(itemView);
        radAddr = itemView.findViewById(R.id.rad_addr);
        tvAddr1 = itemView.findViewById(R.id.tv_addr_1);
        tvAddr2 = itemView.findViewById(R.id.tv_addr_2);

        radAddr.setOnClickListener(onClickListener());
    }

    @SuppressLint("SetTextI18n")
    public void onBind(DataUserAddress item) {
        tvAddr1.setText("บ้านเลขที่ " + item.getCustomerAddNo() + " ถนน " + item.getCustomerAddRoad());
        if (item.isChecked()) {
            radAddr.setChecked(true);
        }else {
            radAddr.setChecked(false);
        }
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChecked(UserAddressHolder.this, getAdapterPosition());
                }
            }
        };
    }

    public void setOnCheckedListener(onRadCheckedListener listener) {
        this.listener = listener;
    }

    public interface onRadCheckedListener {
        void onChecked(UserAddressHolder view, int position);
    }
}
