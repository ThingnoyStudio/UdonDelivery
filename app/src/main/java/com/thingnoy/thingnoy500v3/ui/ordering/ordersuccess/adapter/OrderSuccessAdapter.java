package com.thingnoy.thingnoy500v3.ui.ordering.ordersuccess.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.ui.ordering.ordersuccess.adapter.holder.OrderSuccessHolder;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;

public class OrderSuccessAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = OrderSuccessAdapter.class.getSimpleName();
    private List<FoodProductItem> items;

    public OrderSuccessAdapter(List<FoodProductItem> items) {
        setItems(items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_order_detail, parent, false);
        return new OrderSuccessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FoodProductItem i = getItem(position);
//        if (getItemViewType(position) == TYPE_FOOD_ORDER_DETAIL){
//            FoodDetail item = (FoodDetail) i;
            OrderSuccessHolder successHolder = (OrderSuccessHolder) holder;
            successHolder.onBind(i);
//        }
    }

    @Override
    public int getItemCount() {
        if (items==null){
            return 0;
        }
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public void setItems(List<FoodProductItem> items) {
        this.items = items;
        Log.e(TAG,"setItems: "+new GetPrettyPrintJson().getJson(items));
        notifyDataSetChanged();
    }

    public boolean hasItems() {
        return getItemCount() > 0;
    }

    public List<FoodProductItem> getItems() {
        return getPrivateItems();
    }

    public FoodProductItem getItem(int position) {
        return getPrivateItems().get(position);
    }

    private List<FoodProductItem> getPrivateItems() {
        if (items == null) return new ArrayList<>();
        return items;
    }
}
