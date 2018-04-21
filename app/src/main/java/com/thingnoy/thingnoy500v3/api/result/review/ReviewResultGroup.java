package com.thingnoy.thingnoy500v3.api.result.review;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HBO on 4/4/2561.
 */

public class ReviewResultGroup implements Parcelable {
    @SerializedName("success")
    private Boolean mSuccess;
    @SerializedName("data")
    private List<DataReview> mData;

    public ReviewResultGroup() {
    }

    protected ReviewResultGroup(Parcel in) {
        byte tmpMSuccess = in.readByte();
        mSuccess = tmpMSuccess == 0 ? null : tmpMSuccess == 1;
        mData = in.createTypedArrayList(DataReview.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mSuccess == null ? 0 : mSuccess ? 1 : 2));
        dest.writeTypedList(mData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewResultGroup> CREATOR = new Creator<ReviewResultGroup>() {
        @Override
        public ReviewResultGroup createFromParcel(Parcel in) {
            return new ReviewResultGroup(in);
        }

        @Override
        public ReviewResultGroup[] newArray(int size) {
            return new ReviewResultGroup[size];
        }
    };

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public List<DataReview> getmData() {
        return mData;
    }

    public void setmData(List<DataReview> mData) {
        this.mData = mData;
    }
}
