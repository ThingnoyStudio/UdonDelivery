package com.thingnoy.thingnoy500v3.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.EmptyHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.FoodsProductHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.SectionHolder;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.adapter.item.EmptyItem;
import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.adapter.item.SectionItem;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 22/2/2561.
 */

public class FoodProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseItem> orderFoodItemList;

    private OnClickFoodProductListener listener;


    public interface OnClickFoodProductListener {
        void onClickLike(FoodProductItem item, int position);

        void onClickUnLike(FoodProductItem item, int position);

        void onClickItem(FoodProductItem item, int position);

        void onClickAddToCart(FoodProductItem item, int position);

        void onClickAdded(FoodProductItem item, int position);
    }

    public FoodProductAdapter() {
        orderFoodItemList = new ArrayList<>();
    }

    public void setItems(List<BaseItem> orderFoodItemList) {
        this.orderFoodItemList = orderFoodItemList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FoodProductType.TYPE_ORDER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_food_product, parent, false);
            return new FoodsProductHolder(view);

        } else if (viewType == FoodProductType.TYPE_EMPTY) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_empty, parent, false);
            return new EmptyHolder(view);

        } else if (viewType == FoodProductType.TYPE_SECTION) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_section, parent, false);
            return new SectionHolder(view);

        }
//        else if (viewType==FoodProductType.TYPE_PROGRESSBAR){
//            View view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.holder_food_progress, parent,false);
//            return new(view);
//        }
        throw new NullPointerException("View Type " + viewType + " doesn't match with any existing order detail type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        BaseItem orderFoodItem = orderFoodItemList.get(position);
        if (holder instanceof FoodsProductHolder) {
            FoodsProductHolder foodProductHolder = (FoodsProductHolder) holder;
            FoodProductItem foodProductItem = (FoodProductItem) orderFoodItem;
            setupFoodProduct(foodProductHolder, foodProductItem);

        } else if (holder instanceof EmptyHolder) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyItem emptyItem = (EmptyItem) orderFoodItem;
            setupEmpty(emptyHolder, emptyItem);

        } else if (holder instanceof SectionHolder) {

            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);

            SectionHolder sectionHolder = (SectionHolder) holder;
            SectionItem sectionItem = (SectionItem) orderFoodItem;
            setupSection(sectionHolder, sectionItem);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return orderFoodItemList.get(position).getType();
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

    private void setupSection(SectionHolder sectionHolder, SectionItem sectionItem) {
        sectionHolder.tvSection.setText(sectionItem.getSectionName());
    }

    private void setupEmpty(EmptyHolder emptyHolder, EmptyItem emptyItem) {
    }

    private void setupFoodProduct(FoodsProductHolder foodProductHolder, FoodProductItem foodProductItem) {
//        foodProductHolder.setFoodImg(foodProductItem.getmFoodImg());
//        foodProductHolder.tvFoodName.setText(foodProductItem.getmFoodName());
//        foodProductHolder.tvFoodPrice.setText(foodProductItem.getmFoodPrice());
//        foodProductHolder.tvTypeName.setText(foodProductItem.getmFoodTypeName());
//        foodProductHolder.tvIDFood.setText(foodProductItem.getmIDFood());

        foodProductHolder.onBind(foodProductItem);
        foodProductHolder.setOnClickFoodListener(onClickFood(foodProductItem));
    }

    public void setOnClickProductItem(OnClickFoodProductListener listener) {
        this.listener = listener;
    }

    public boolean hasItems() {
        return getItemCount() > 0;
    }


    public void clearAddedState(FoodProductItem item) {
//        getPresenter().clearAddState(item);
        for (BaseItem baseItem : getItems()) {
            if (baseItem instanceof FoodProductItem) {
                FoodProductItem foodProductItem = (FoodProductItem) baseItem;
                if (item != null && item.getmIDFood().equals(foodProductItem.getmIDFood())) {
                    foodProductItem.setAdded(false);
                    break;
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clearAddedStateAll() {
//        getPresenter().clearAddStateAll();

        for (BaseItem baseItem : getItems()) {
            if (baseItem instanceof FoodProductItem) {
                FoodProductItem beerProductItem = (FoodProductItem) baseItem;
                beerProductItem.setAdded(false);
            }
        }
        notifyDataSetChanged();
    }

    public List<BaseItem> getItems() {
        return getPrivateItems();
    }

    public BaseItem getItem(int position) {
        return getPrivateItems().get(position);
    }

    private List<BaseItem> getPrivateItems() {
        if (orderFoodItemList == null) return new ArrayList<>();
        return orderFoodItemList;
    }

    private FoodsProductHolder.OnClickFoodListener onClickFood(final FoodProductItem item) {
        return new FoodsProductHolder.OnClickFoodListener() {
            @Override
            public void onClickItem(FoodsProductHolder beerProductHolder, int position) {
                if (listener != null) {
                    listener.onClickItem(item, position);
                }
            }

            @Override
            public void onClickLike(FoodsProductHolder view, int position) {
                if (listener != null) {
                    listener.onClickLike(item, position);
                }
            }

            @Override
            public void onClickUnLike(FoodsProductHolder view, int position) {
                if (listener != null) {
                    listener.onClickUnLike(item, position);
                }
            }

            @Override
            public void onClickAddToCart(FoodsProductHolder view, int position) {
                item.setAdded(true);
                if (listener != null) {
                    listener.onClickAddToCart(item, position);
                }
            }

            @Override
            public void onClickAdded(FoodsProductHolder beerProductHolder, int position) {
                item.setAdded(false);
                if (listener != null) {
                    listener.onClickAdded(item, position);
                }
            }
        };
    }


}
