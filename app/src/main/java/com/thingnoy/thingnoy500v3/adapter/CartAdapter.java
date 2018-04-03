package com.thingnoy.thingnoy500v3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.FoodsProductHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.cart.CartHolder;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.ArrayList;
import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_ORDER;

/**
 * Created by HBO on 28/3/2561.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseItem> orderFoodItemList;
    private OnClickCartItemListener listener;

    public CartAdapter() {
        orderFoodItemList = new ArrayList<>();
    }

    public interface OnClickCartItemListener {
        void onClickIncrease(FoodProductItem item, int position);

        void onClickDecrease(FoodProductItem item, int position);

        void onClickDelete(FoodProductItem item, int position);
    }

    public void setOnClickCartItem(OnClickCartItemListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseItem i = getItem(position);
        if (getItemViewType(position) == TYPE_ORDER) {
            final FoodProductItem item = (FoodProductItem) i;
            CartHolder cartHolder = (CartHolder) holder;
            cartHolder.onBind(item);
            cartHolder.setOnClickCartHolderListener(onClickCartHolder(item));
        }
    }

    @Override
    public int getItemCount() {
        if (orderFoodItemList == null) {
            return 0;
        }
        if (orderFoodItemList.size() <= 0) {
            return 0;
        }

        return orderFoodItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return orderFoodItemList.get(position).getType();
    }


    public int getTotalPrice() {
        return calTotalPrice();
    }

    public void removeItem(FoodProductItem item) {
        mRemoveItem(item);
    }

    private void increaseItemAmountAt(FoodProductItem beerItem) {
        beerItem.increaseAmount();
        notifyDataSetChanged();
    }

    private void decreaseItemAmountAt(FoodProductItem beerItem) {
        beerItem.decreaseAmount();
        notifyDataSetChanged();
    }


    public void addItem(BaseItem item) {
        mAddItem(item);
    }

    public void setItems(List<BaseItem> items) {
        this.orderFoodItemList = items;
        notifyDataSetChanged();
    }

    public List<BaseItem> getItems() {
        return getPrivateItems();
    }

    public BaseItem getItem(int position) {
        return getPrivateItems().get(position);
    }

    public boolean hasItems() {
        return getItemCount() > 0;
    }


    public void removeAllItems() {
        getPrivateItems().clear();
        notifyDataSetChanged();
    }

    /**********
     * Function
     **********/
    private int calTotalPrice() {
        int price = 0;
        for (BaseItem baseItem : getItems()) {
            if (baseItem instanceof FoodProductItem) {
                FoodProductItem beerItem = (FoodProductItem) baseItem;
                price += beerItem.getPrice() * beerItem.getAmount();
            }
        }
        return price;
    }

    private void mRemoveItem(FoodProductItem item) {
        for (int i = 0; i < getItems().size(); i++) {
            if (getItem(i) instanceof FoodProductItem) {
                if (((FoodProductItem) getItem(i)).getmIDFood().equals(item.getmIDFood())) {
                    remove(i);
                }
            }
        }
    }

    private void remove(int index) {
        getPrivateItems().remove(index);
        notifyItemRemoved(index);
    }

    private void mAddItem(BaseItem item) {
        getPrivateItems().add(item);
        notifyItemInserted(getItemCount() - 1);
    }


    private List<BaseItem> getPrivateItems() {
        if (orderFoodItemList == null) return new ArrayList<>();
        return orderFoodItemList;
    }

    private CartHolder.OnClickCartHolderListener onClickCartHolder(final FoodProductItem item) {
        return new CartHolder.OnClickCartHolderListener() {
            @Override
            public void onClickIncrease(CartHolder view, int position) {
                increaseItemAmountAt(item);
                if (listener != null) {
                    listener.onClickIncrease(item, position);
                }
            }

            @Override
            public void onClickDecrease(CartHolder view, int position) {
                decreaseItemAmountAt(item);
                if (listener != null) {
                    listener.onClickDecrease(item, position);
                }
            }

            @Override
            public void onClickDelete(CartHolder view, int position) {
                if (listener != null) {
                    listener.onClickDelete(item, position);
                }
            }
        };
    }
}
