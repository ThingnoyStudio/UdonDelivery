
package com.thingnoy.thingnoy500v3.api.result.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RestaurantResultGroup implements Parcelable {

    @SerializedName("data")
    private List<ResDataDao> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public RestaurantResultGroup() {
    }

    protected RestaurantResultGroup(Parcel in) {
        mData = in.createTypedArrayList(ResDataDao.CREATOR);
        byte tmpMSuccess = in.readByte();
        mSuccess = tmpMSuccess == 0 ? null : tmpMSuccess == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mData);
        dest.writeByte((byte) (mSuccess == null ? 0 : mSuccess ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantResultGroup> CREATOR = new Creator<RestaurantResultGroup>() {
        @Override
        public RestaurantResultGroup createFromParcel(Parcel in) {
            return new RestaurantResultGroup(in);
        }

        @Override
        public RestaurantResultGroup[] newArray(int size) {
            return new RestaurantResultGroup[size];
        }
    };

    public List<ResDataDao> getmData() {
        return mData;
    }

    public void setmData(List<ResDataDao> mData) {
        this.mData = mData;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
