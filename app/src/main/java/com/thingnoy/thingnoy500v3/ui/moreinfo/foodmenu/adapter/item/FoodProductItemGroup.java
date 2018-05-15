package com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import java.util.List;

/**
 * Created by HBO on 2/4/2561.
 */

public class FoodProductItemGroup implements Parcelable {
    private List<BaseItem> foods;

    public FoodProductItemGroup() {
    }

    protected FoodProductItemGroup(Parcel in) {
        foods = in.createTypedArrayList(BaseItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(foods);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodProductItemGroup> CREATOR = new Creator<FoodProductItemGroup>() {
        @Override
        public FoodProductItemGroup createFromParcel(Parcel in) {
            return new FoodProductItemGroup(in);
        }

        @Override
        public FoodProductItemGroup[] newArray(int size) {
            return new FoodProductItemGroup[size];
        }
    };

    public List<BaseItem> getFoods() {
        return foods;
    }

    public void setFoods(List<BaseItem> foods) {
        this.foods = foods;
    }
}
