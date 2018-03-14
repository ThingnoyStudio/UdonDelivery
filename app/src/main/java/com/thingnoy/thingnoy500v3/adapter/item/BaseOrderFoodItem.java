package com.thingnoy.thingnoy500v3.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HBO on 23/2/2561.
 */

public class BaseOrderFoodItem implements Parcelable {
    private int type;

    public BaseOrderFoodItem(int type) {
        this.type = type;
    }

    protected BaseOrderFoodItem(Parcel in) {
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseOrderFoodItem> CREATOR = new Creator<BaseOrderFoodItem>() {
        @Override
        public BaseOrderFoodItem createFromParcel(Parcel in) {
            return new BaseOrderFoodItem(in);
        }

        @Override
        public BaseOrderFoodItem[] newArray(int size) {
            return new BaseOrderFoodItem[size];
        }
    };

    public int getType() {
        return type;
    }


}
