
package com.thingnoy.thingnoy500v3.api.result.foodMenu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class FoodMenuResultGroup implements Parcelable {

    @SerializedName("data")
    private Data mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public FoodMenuResultGroup() {
    }

    protected FoodMenuResultGroup(Parcel in) {
        byte tmpMSuccess = in.readByte();
        mSuccess = tmpMSuccess == 0 ? null : tmpMSuccess == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mSuccess == null ? 0 : mSuccess ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodMenuResultGroup> CREATOR = new Creator<FoodMenuResultGroup>() {
        @Override
        public FoodMenuResultGroup createFromParcel(Parcel in) {
            return new FoodMenuResultGroup(in);
        }

        @Override
        public FoodMenuResultGroup[] newArray(int size) {
            return new FoodMenuResultGroup[size];
        }
    };

    public Data getmData() {
        return mData;
    }

    public void setmData(Data mData) {
        this.mData = mData;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
