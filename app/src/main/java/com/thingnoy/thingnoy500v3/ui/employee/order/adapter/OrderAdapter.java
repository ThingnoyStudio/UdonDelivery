package com.thingnoy.thingnoy500v3.ui.employee.order.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.holder.HistoryHolder;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.holder.OrderHolder;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItem;

import java.util.ArrayList;
import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_EMP_ORDER;
import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_HISTORY;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = OrderAdapter.class.getSimpleName();
    private onClickEmpOrderListener listener;
    private List<BaseItem> items;

    public interface onClickEmpOrderListener {
        void onClickItem(OrderItem item, int position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_emp_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseItem i = getItem(position);
        if (getItemViewType(position) == TYPE_EMP_ORDER) {
            OrderItem item = (OrderItem) i;
            OrderHolder orderHolder = (OrderHolder) holder;
            orderHolder.onBind(item);
            orderHolder.setOnClickItemListener(onClickListener(item));
        }
    }

    private OrderHolder.onClickItemListener onClickListener(final OrderItem item) {
        return new OrderHolder.onClickItemListener() {
            @Override
            public void onClickItem(OrderHolder view, int position) {
                if (listener != null) {
                    listener.onClickItem(item, position);
                }
            }
        };
    }


    @Override
    public int getItemCount() {
        if (items == null) {
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

    public void setOnItemClickListener(onClickEmpOrderListener listener) {
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
