package com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter.holder;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;

public class AddressHolder extends RecyclerView.ViewHolder {
    private final TextView tvAddr1;
    private final TextView tvAddr2;
    private final RadioButton radAddr;
    private final MaterialRippleLayout mrDelete;
    private onRadCheckedListener listener;

    public AddressHolder(View itemView) {
        super(itemView);
        radAddr = itemView.findViewById(R.id.rad_addr);
        tvAddr1 = itemView.findViewById(R.id.tv_addr_1);
        tvAddr2 = itemView.findViewById(R.id.tv_addr_2);
        mrDelete = itemView.findViewById(R.id.mr_del_address);

        radAddr.setOnClickListener(onClickListener());
        mrDelete.setOnClickListener(onClickDelListener());
    }


    @SuppressLint("SetTextI18n")
    public void onBind(DataUserAddress item) {
        tvAddr1.setText("บ้านเลขที่ " + item.getCustomerAddNo());
        tvAddr2.setText(""+ item.getCustomerAddRoad());
        if (item.isChecked()) {
            radAddr.setChecked(true);
        } else {
            radAddr.setChecked(false);
        }
    }

    private View.OnClickListener onClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChecked(AddressHolder.this, getAdapterPosition());
                }
            }
        };
    }

    private View.OnClickListener onClickDelListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickDelete(AddressHolder.this, getAdapterPosition());
                }
            }
        };
    }

    public void setOnCheckedListener(onRadCheckedListener listener) {
        this.listener = listener;
    }

    public interface onRadCheckedListener {
        void onChecked(AddressHolder view, int position);

        void onClickDelete(AddressHolder view, int position);
    }
}
