package com.thingnoy.thingnoy500v3.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HBO on 23/2/2561.
 */

public class BaseItem implements Parcelable {
    private int type;

    public BaseItem(int type) {
        this.type = type;
    }

    protected BaseItem(Parcel in) {
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

    public static final Creator<BaseItem> CREATOR = new Creator<BaseItem>() {
        @Override
        public BaseItem createFromParcel(Parcel in) {
            return new BaseItem(in);
        }

        @Override
        public BaseItem[] newArray(int size) {
            return new BaseItem[size];
        }
    };

    public int getType() {
        return type;
    }


}
