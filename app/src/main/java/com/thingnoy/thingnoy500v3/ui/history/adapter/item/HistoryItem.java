package com.thingnoy.thingnoy500v3.ui.history.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.history.FoodDetail;
import com.thingnoy.thingnoy500v3.api.result.history.Order;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 23/2/2561.
 */

public class HistoryItem extends BaseItem implements Parcelable {

    private Order order;
    private List<FoodDetail> foodDetails;

    public HistoryItem() {
        super(FoodProductType.TYPE_HISTORY);
    }

    protected HistoryItem(Parcel in) {
        order = in.readParcelable(Order.class.getClassLoader());
        foodDetails = in.createTypedArrayList(FoodDetail.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(order, flags);
        dest.writeTypedList(foodDetails);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HistoryItem> CREATOR = new Creator<HistoryItem>() {
        @Override
        public HistoryItem createFromParcel(Parcel in) {
            return new HistoryItem(in);
        }

        @Override
        public HistoryItem[] newArray(int size) {
            return new HistoryItem[size];
        }
    };



    public Order getOrder() {
        return order;
    }

    public HistoryItem setOrder(Order order) {
        this.order = order;
        return this;
    }

    public List<FoodDetail> getFoodDetails() {
        return foodDetails;
    }

    public HistoryItem setFoodDetails(List<FoodDetail> foodDetails) {
        this.foodDetails = foodDetails;
        return this;
    }

    public List<BaseItem> getBaseItems(){
        List<BaseItem> baseItems = new ArrayList<>(  );
        for( FoodDetail foodDetail : foodDetails){
            baseItems.add(foodDetail);
        }
        return baseItems;
    }
}
