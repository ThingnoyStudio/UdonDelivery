package com.thingnoy.thingnoy500v3.ui.ordering.address.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.ordering.address.adapter.holder.UserAddressHolder;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;

import java.util.ArrayList;
import java.util.List;

public class UserAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataUserAddress> items;
    private onUserAddressChecked listener;

    public UserAddressAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_address, parent, false);
        return new UserAddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataUserAddress i = items.get(position);
        if (holder instanceof UserAddressHolder) {
            UserAddressHolder userAddressHolder = (UserAddressHolder) holder;
            userAddressHolder.onBind(i);
            userAddressHolder.setOnCheckedListener(onChecked(i));
        }
    }

    private UserAddressHolder.onRadCheckedListener onChecked(final DataUserAddress i) {
        return new UserAddressHolder.onRadCheckedListener() {
            @Override
            public void onChecked(UserAddressHolder view, int position) {
                if (listener != null) {
                    listener.onChecked(i, position);
                    items.get(position).setChecked(true);
                    for (int i = 0; i < items.size(); i++) {
                        if (i != position) {
                            items.get(i).setChecked(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<DataUserAddress> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnCheckedListener(onUserAddressChecked listener) {
        this.listener = listener;
    }

    public interface onUserAddressChecked {
        void onChecked(DataUserAddress item, int position);
    }

    public List<DataUserAddress> getItems() {
        return getPrivateItems();
    }

    private List<DataUserAddress> getPrivateItems() {
        if (items == null) return new ArrayList<>();
        return items;
    }
}
