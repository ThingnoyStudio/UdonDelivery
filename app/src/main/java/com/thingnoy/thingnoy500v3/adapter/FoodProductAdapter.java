package com.thingnoy.thingnoy500v3.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thingnoy.thingnoy500v3.R;
import com.thingnoy.thingnoy500v3.adapter.holder.EmptyHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.FoodProductHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.FoodsProductHolder;
import com.thingnoy.thingnoy500v3.adapter.holder.SectionHolder;
import com.thingnoy.thingnoy500v3.adapter.item.BaseOrderFoodItem;
import com.thingnoy.thingnoy500v3.adapter.item.EmptyItem;
import com.thingnoy.thingnoy500v3.adapter.item.OrderItem;
import com.thingnoy.thingnoy500v3.adapter.item.SectionItem;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 22/2/2561.
 */

public class FoodProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BaseOrderFoodItem> orderFoodItemList;

    public FoodProductAdapter() {
        orderFoodItemList = new ArrayList<>();
    }

    public void setOrderFoodItemList(List<BaseOrderFoodItem> orderFoodItemList){
        this.orderFoodItemList = orderFoodItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FoodProductType.TYPE_ORDER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_food_product, parent,false);
            return new FoodsProductHolder(view);

        }else if (viewType== FoodProductType.TYPE_EMPTY){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_empty, parent,false);
            return new EmptyHolder(view);

        }else if (viewType == FoodProductType.TYPE_SECTION){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.holder_section, parent,false);
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

        BaseOrderFoodItem orderFoodItem = orderFoodItemList.get(position);
        if (holder instanceof FoodsProductHolder){
            FoodsProductHolder foodProductHolder = (FoodsProductHolder) holder;
            OrderItem orderItem = (OrderItem) orderFoodItem;
            setupFoodProduct(foodProductHolder,orderItem);

        }else if (holder instanceof EmptyHolder){
            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyItem emptyItem = (EmptyItem) orderFoodItem;
            setupEmpty(emptyHolder,emptyItem);

        }else if (holder instanceof SectionHolder){
            SectionHolder sectionHolder = (SectionHolder) holder;
            SectionItem sectionItem = (SectionItem) orderFoodItem;
            setupSection(sectionHolder,sectionItem);
        }
    }

    private void setupSection(SectionHolder sectionHolder, SectionItem sectionItem) {
        sectionHolder.tvSection.setText(sectionItem.getSectionName());
    }

    private void setupEmpty(EmptyHolder emptyHolder, EmptyItem emptyItem) {
    }

    private void setupFoodProduct(FoodsProductHolder foodProductHolder, OrderItem orderItem) {
//        foodProductHolder.imgFood.set orderItem.getmFoodImg();
//        foodProductHolder.imgUrl = orderItem.getmFoodImg();
        foodProductHolder.setImageUrl(orderItem.getmFoodImg());
        Log.e("adapter","imgUrl: "+orderItem.getmFoodImg());
        foodProductHolder.tvFoodName.setText(orderItem.getmFoodName());
        foodProductHolder.tvFoodPrice.setText(orderItem.getmFoodPrice());
        foodProductHolder.tvTypeName.setText(orderItem.getmFoodTypeName());
        foodProductHolder.tvIDFood.setText(orderItem.getmIDFood());
    }

    @Override
    public int getItemViewType(int position) {
        return orderFoodItemList.get(position).getType();
    }

    
    @Override
    public int getItemCount() {
        if (orderFoodItemList == null)
            return 0;
        return orderFoodItemList.size();
    }
}
