package com.thingnoy.thingnoy500v3.adapter.item;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@SuppressLint("ParcelCreator")
public class FoodProductItemsGroup implements Parcelable {
    List<FoodProductItemGroup> data;

    protected FoodProductItemsGroup(Parcel in) {
        data = in.createTypedArrayList(FoodProductItemGroup.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodProductItemsGroup> CREATOR = new Creator<FoodProductItemsGroup>() {
        @Override
        public FoodProductItemsGroup createFromParcel(Parcel in) {
            return new FoodProductItemsGroup(in);
        }

        @Override
        public FoodProductItemsGroup[] newArray(int size) {
            return new FoodProductItemsGroup[size];
        }
    };

    public List<FoodProductItemGroup> getData() {
        return data;
    }

    public void setData(List<FoodProductItemGroup> data) {
        this.data = data;
    }
}
