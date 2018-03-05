
package com.thingnoy.thingnoy500v3.dao.review;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReviewCollectionDao implements Parcelable {

    @SerializedName("data")
    private List<DataReview> mData;
    @SerializedName("success")
    private Boolean mSuccess;

    public ReviewCollectionDao() {
    }

    protected ReviewCollectionDao(Parcel in) {
        mData = in.createTypedArrayList(DataReview.CREATOR);
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

    public static final Creator<ReviewCollectionDao> CREATOR = new Creator<ReviewCollectionDao>() {
        @Override
        public ReviewCollectionDao createFromParcel(Parcel in) {
            return new ReviewCollectionDao(in);
        }

        @Override
        public ReviewCollectionDao[] newArray(int size) {
            return new ReviewCollectionDao[size];
        }
    };

    public List<DataReview> getmData() {
        return mData;
    }

    public void setmData(List<DataReview> mData) {
        this.mData = mData;
    }

    public Boolean getmSuccess() {
        return mSuccess;
    }

    public void setmSuccess(Boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
