
package com.thingnoy.thingnoy500v3.api.result.promotion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PromotionResultGroup implements Parcelable {

    @SerializedName("data")
    private List<PromotionDao> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public PromotionResultGroup() {
    }

    protected PromotionResultGroup(Parcel in) {
        mData = in.createTypedArrayList(PromotionDao.CREATOR);
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

    public static final Creator<PromotionResultGroup> CREATOR = new Creator<PromotionResultGroup>() {
        @Override
        public PromotionResultGroup createFromParcel(Parcel in) {
            return new PromotionResultGroup(in);
        }

        @Override
        public PromotionResultGroup[] newArray(int size) {
            return new PromotionResultGroup[size];
        }
    };

    public List<PromotionDao> getmData() {
        return mData;
    }

    public void setmData(List<PromotionDao> mData) {
        this.mData = mData;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
