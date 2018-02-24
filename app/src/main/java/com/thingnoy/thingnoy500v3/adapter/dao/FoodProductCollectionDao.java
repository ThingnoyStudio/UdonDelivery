
package com.thingnoy.thingnoy500v3.adapter.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class FoodProductCollectionDao implements Parcelable {

    @SerializedName("data")
    private Data mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public FoodProductCollectionDao() {
    }

    protected FoodProductCollectionDao(Parcel in) {
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

    public static final Creator<FoodProductCollectionDao> CREATOR = new Creator<FoodProductCollectionDao>() {
        @Override
        public FoodProductCollectionDao createFromParcel(Parcel in) {
            return new FoodProductCollectionDao(in);
        }

        @Override
        public FoodProductCollectionDao[] newArray(int size) {
            return new FoodProductCollectionDao[size];
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
