package com.thingnoy.thingnoy500v3.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HBO on 23/2/2561.
 */

public class HistoryItem extends BaseItem implements Parcelable {
    private String date;
    private String time;
    private int totalAmount;
    private int totalPrice;
    private List<FoodProductItem> foods;

    public HistoryItem() {
        super(FoodProductType.TYPE_HISTORY);
    }

    protected HistoryItem(Parcel in) {
        super(in);
        date = in.readString();
        time = in.readString();
        totalAmount = in.readInt();
        totalPrice = in.readInt();
        foods = in.createTypedArrayList(FoodProductItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(totalAmount);
        dest.writeInt(totalPrice);
        dest.writeTypedList(foods);
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

    public List<BaseItem> getBaseItems() {
        List<BaseItem> baseItems = new ArrayList<>();
        for (FoodProductItem food : foods) {
            baseItems.add(food);
        }
        return baseItems;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<FoodProductItem> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodProductItem> foods) {
        this.foods = foods;
    }
}
