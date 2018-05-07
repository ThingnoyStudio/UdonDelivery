package com.thingnoy.thingnoy500v3.ui.history.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.ui.history.adapter.holder.HistoryHolder;
import com.thingnoy.thingnoy500v3.ui.history.adapter.item.HistoryItem;

import java.util.ArrayList;
import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_HISTORY;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = HistoryAdapter.class.getSimpleName();
    private onClickHistoryListener listener;
    private List<BaseItem> items;

    public interface onClickHistoryListener{
        void onClickItem(HistoryItem item, int position );
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_order_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseItem i = getItem(position);
        if (getItemViewType(position) == TYPE_HISTORY){
            HistoryItem item = (HistoryItem) i;
            HistoryHolder historyHolder = (HistoryHolder) holder;
            historyHolder.onBind(item);
            historyHolder.setOnClickItemListener(onClickListener(item));
        }
    }

    private HistoryHolder.onClickItemListener onClickListener(final HistoryItem item) {
        return new HistoryHolder.onClickItemListener() {
            @Override
            public void onClickItem(HistoryHolder view, int position) {
                if (listener!=null){
                    listener.onClickItem(item,position);
                }
            }
        };
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
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onClickHistoryListener listener) {
        this.listener = listener;
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
