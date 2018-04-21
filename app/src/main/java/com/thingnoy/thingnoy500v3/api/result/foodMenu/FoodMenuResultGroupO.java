
package com.thingnoy.thingnoy500v3.api.result.foodMenu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class FoodMenuResultGroupO implements Parcelable {

    @SerializedName("data")
    private Data mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public FoodMenuResultGroupO() {
    }

    protected FoodMenuResultGroupO(Parcel in) {
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

    public static final Creator<FoodMenuResultGroupO> CREATOR = new Creator<FoodMenuResultGroupO>() {
        @Override
        public FoodMenuResultGroupO createFromParcel(Parcel in) {
            return new FoodMenuResultGroupO(in);
        }

        @Override
        public FoodMenuResultGroupO[] newArray(int size) {
            return new FoodMenuResultGroupO[size];
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
