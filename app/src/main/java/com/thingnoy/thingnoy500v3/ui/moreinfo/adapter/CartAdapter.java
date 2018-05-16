package com.thingnoy.thingnoy500v3.ui.moreinfo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.ui.moreinfo.adapter.holder.CartHolder;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.DetailFoodItem;
import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_ORDER;

/**
 * Created by HBO on 28/3/2561.
 */

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = CartAdapter.class.getSimpleName();
    private List<BaseItem> orderFoodItemList;
    private OnClickCartItemListener listener;

    public CartAdapter() {
        orderFoodItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_cart, parent, false);
        return new CartHolder(view, parent.getContext(), new TextChangeListener());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder");
        BaseItem i = getItem(position);
        if (getItemViewType(position) == TYPE_ORDER) {
            final FoodProductItem item = (FoodProductItem) i;
            Log.e(TAG, "onBindViewHolder>> Bind: " + item.getmFoodName() + " >>" + new GetPrettyPrintJson().getJson(item));
            CartHolder cartHolder = (CartHolder) holder;

            cartHolder.textChangeListener.updatePosition(item, cartHolder.getAdapterPosition());
            cartHolder.onBind(item, cartHolder.getAdapterPosition());
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


    public void setOnClickCartItem(OnClickCartItemListener listener) {
        this.listener = listener;
    }

    public int getTotalPrice() {
        return calTotalPrice();
    }

    public void removeItem(FoodProductItem item) {
        mRemoveItem(item);
    }

    private void increaseItemAmountAt(FoodProductItem foodItem) {
        foodItem.increaseAmount();
        notifyDataSetChanged();
    }

    private void decreaseItemAmountAt(FoodProductItem foodItem) {
        foodItem.decreaseAmount();
        notifyDataSetChanged();
    }

    private void setAddOnSelectIndex(FoodProductItem foodItem, DetailFoodItem detailFoodItem) {
        foodItem.setAddOn(detailFoodItem);
//        notifyDataSetChanged();
        Log.e(TAG, "setAddOn : " + new GetPrettyPrintJson().getJson(foodItem));
    }

    private void addReason(FoodProductItem foodItem, String reason) {
        foodItem.setReason(reason);
//        Log.e(TAG, "AddReason : " + new GetPrettyPrintJson().getJson(foodItem));
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

    /*********************
     * Function
     **********/
    private int calTotalPrice() {
        int price = 0;
        for (BaseItem baseItem : getItems()) {
            if (baseItem instanceof FoodProductItem) {
                FoodProductItem foodItem = (FoodProductItem) baseItem;
                // (price * amount) + addOn
                if (foodItem.getAddOn()!= null){
                    price += (foodItem.getPrice() * foodItem.getAmount()) + foodItem.getAddOn().getFoodDetailsPrice();
                }else {
                    price += (foodItem.getPrice() * foodItem.getAmount());
                }

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
        Log.e(TAG, "remove Item: " + new GetPrettyPrintJson().getJson(orderFoodItemList));
        notifyItemRemoved(index);
    }

    private void mAddItem(BaseItem item) {
        getPrivateItems().add(item);
        Log.e(TAG, "Added Item: " + new GetPrettyPrintJson().getJson(orderFoodItemList));
        notifyItemInserted(getItemCount());
    }

    private List<BaseItem> getPrivateItems() {
        if (orderFoodItemList == null) return new ArrayList<>();
        return orderFoodItemList;
    }

    /*
     * Listener
     */
    public interface OnClickCartItemListener {
        void onClickIncrease(FoodProductItem item, int position);

        void onClickDecrease(FoodProductItem item, int position);

        void onClickDelete(FoodProductItem item, int position);

        void onAddOnSelected(FoodProductItem item, int position);
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

            @Override
            public void onSelectedAddOn(CartHolder view, int position, DetailFoodItem detailFoodItem) {
                setAddOnSelectIndex(item, detailFoodItem);
                if (listener != null) {
                    listener.onAddOnSelected(item, position);
                }
            }
        };
    }

    public class TextChangeListener implements TextWatcher {
        private int position;
        private FoodProductItem item;

        void updatePosition(FoodProductItem item, int position) {
            this.position = position;
            this.item = item;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            addReason(item, s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}
