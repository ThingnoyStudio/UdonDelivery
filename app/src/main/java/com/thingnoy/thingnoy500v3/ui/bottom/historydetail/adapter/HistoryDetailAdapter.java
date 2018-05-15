package com.thingnoy.thingnoy500v3.ui.bottom.historydetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.history.FoodDetail;
import com.thingnoy.thingnoy500v3.ui.bottom.historydetail.adapter.holder.HistoryDetailHolder;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;

public class HistoryDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = HistoryDetailAdapter.class.getSimpleName();
    private List<BaseItem> items;

    public HistoryDetailAdapter(List<BaseItem> items) {
        setItems(items);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_order_detail, parent, false);
        return new HistoryDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseItem i = getItem(position);
//        if (getItemViewType(position) == TYPE_FOOD_ORDER_DETAIL){
            FoodDetail item = (FoodDetail) i;
            HistoryDetailHolder historyHolder = (HistoryDetailHolder) holder;
            historyHolder.onBind(item);
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

    public void setItems(List<BaseItem> items) {
        this.items = items;
        Log.e(TAG,"setItems: "+new GetPrettyPrintJson().getJson(items));
        notifyDataSetChanged();
    }

    public boolean hasItems() {
        return getItemCount() > 0;
    }

    public List<BaseItem> getItems() {
        return getPrivateItems();
    }

    public BaseItem getItem(int position) {
        return getPrivateItems().get(position);
    }

    private List<BaseItem> getPrivateItems() {
        if (items == null) return new ArrayList<>();
        return items;
    }
}
