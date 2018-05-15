package com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.holder.FavoriteHolder;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.holder.RestaurantHolder;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.FavoriteFoodItem;
import com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item.RestaurantItem;
import com.thingnoy.thingnoy500v3.util.GetPrettyPrintJson;

import java.util.ArrayList;
import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_FAVORITE;
import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_RESTAURANT;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static String TAG = FavoriteAdapter.class.getSimpleName();
    private List<BaseItem> items;
    private OnClickFavoriteListener listener;

    public FavoriteAdapter() {
        items = new ArrayList<>();
    }

    public interface OnClickFavoriteListener {
        void onClickLike(FavoriteFoodItem item, int position);

        void onClickUnLike(FavoriteFoodItem item, int position);

        void onClickItem(FavoriteFoodItem item, int position);

        void onClickAddToCart(FavoriteFoodItem item, int position);

        void onClickAdded(FavoriteFoodItem item, int position);
    }

    public void setOnClickFavoriteItemListener(OnClickFavoriteListener listener) {
        this.listener = listener;
    }

    public void setItems(List<BaseItem> items) {
        this.items = items;

        Log.e("fgg","setItems: "+new GetPrettyPrintJson().getJson(items));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("favAdapter","viewtype : "+viewType);
        if (viewType == TYPE_RESTAURANT){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_fav_resturant,parent,false);
            return new RestaurantHolder(view);
        }else if (viewType == TYPE_FAVORITE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_food_product,parent,false);
            return new FavoriteHolder(view);
        }


        throw new NullPointerException("View Type " + viewType + " doesn't match with any existing order detail type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseItem item = items.get(position);
        if (holder instanceof RestaurantHolder){
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

            RestaurantHolder restaurantHolder = (RestaurantHolder) holder;
            RestaurantItem restaurantItem = (RestaurantItem) item;

            restaurantHolder.tvSection.setText(restaurantItem.getResName());

        }else if (holder instanceof  FavoriteHolder){
            FavoriteHolder favoriteHolder = (FavoriteHolder) holder;
            FavoriteFoodItem favoriteFoodItem = (FavoriteFoodItem) item;

            favoriteHolder.onBind(favoriteFoodItem);
            favoriteHolder.setOnClickFoodListener( onClickFavoriteItem(favoriteFoodItem));
        }
    }

    private FavoriteHolder.OnClickFoodListener onClickFavoriteItem(final FavoriteFoodItem item) {
        return new FavoriteHolder.OnClickFoodListener() {
            @Override
            public void onClickLike(FavoriteHolder view, int position) {
                if (listener != null) {
                    listener.onClickLike(item, position);
                }
            }

            @Override
            public void onClickUnLike(FavoriteHolder view, int position) {
                if (listener != null) {
                    listener.onClickUnLike(item, position);
                }
            }

            @Override
            public void onClickAddToCart(FavoriteHolder view, int position) {
                item.setAdded(true);
                if (listener != null) {
                    listener.onClickAddToCart(item, position);
                }
            }

            @Override
            public void onClickAdded(FavoriteHolder view, int position) {
                item.setAdded(false);
                if (listener != null) {
                    listener.onClickAdded(item, position);
                }
            }

            @Override
            public void onClickItem(FavoriteHolder view, int position) {
                if (listener != null) {
                    listener.onClickItem(item, position);
                }
            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void removeItem(FavoriteFoodItem item) {
        mRemoveItem(item);
    }
    private void mRemoveItem(FavoriteFoodItem item) {
        for (int i = 0; i < getItems().size(); i++) {
//            if (getItem(i) instanceof RestaurantItem){
//                if (((RestaurantItem) getItem(i).get))
//            }
            if (getItem(i) instanceof FavoriteFoodItem) {
                if (((FavoriteFoodItem) getItem(i)).getIDFood().equals(item.getIDFood())) {
                    remove(i);
                }
            }
        }
    }
    private void remove(int index) {
        getPrivateItems().remove(index);
        Log.e(TAG, "remove Item: " + new GetPrettyPrintJson().getJson(items));
        notifyItemRemoved(index);
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
