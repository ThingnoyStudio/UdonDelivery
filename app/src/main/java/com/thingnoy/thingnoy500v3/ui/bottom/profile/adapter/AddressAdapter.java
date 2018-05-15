package com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter.holder.AddressHolder;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DataUserAddress> items;
    private onUserAddressChecked listener;

    public AddressAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_address_with_del, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DataUserAddress i = items.get(position);
        if (holder instanceof AddressHolder) {
            AddressHolder userAddressHolder = (AddressHolder) holder;
            userAddressHolder.onBind(i);
            userAddressHolder.setOnCheckedListener(onChecked(i));
        }
    }

    private AddressHolder.onRadCheckedListener onChecked(final DataUserAddress i) {
        return new AddressHolder.onRadCheckedListener() {
            @Override
            public void onChecked(AddressHolder view, int position) {
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

            @Override
            public void onClickDelete(AddressHolder view, int position) {
                if (listener != null) {
                    listener.onClickDeleteAddress(i, position);
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

        void onClickDeleteAddress(DataUserAddress item, int position);
    }

    public List<DataUserAddress> getItems() {
        return getPrivateItems();
    }

    public DataUserAddress getItem(int position) {
        return getPrivateItems().get(position);
    }

    private List<DataUserAddress> getPrivateItems() {
        if (items == null) return new ArrayList<>();
        return items;
    }

    public void removeItem(DataUserAddress item) {
        mRemoveItem(item);
    }
    private void mRemoveItem(DataUserAddress item) {
        for (int i = 0; i < getItems().size(); i++) {
//            if (getItem(i) instanceof RestaurantItem){
//                if (((RestaurantItem) getItem(i).get))
//            }
            if (getItem(i) instanceof DataUserAddress) {
                if (((DataUserAddress) getItem(i)).getmIDCustomerAddress() == (item.getmIDCustomerAddress())) {
                    remove(i);
                }
            }
        }
    }
    private void remove(int index) {
        getPrivateItems().remove(index);
        notifyItemRemoved(index);
    }
    public boolean hasItems() {
        return getItemCount() > 0;
    }
}
