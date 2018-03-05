
package com.thingnoy.thingnoy500v3.dao.restaurant;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class ResDataDao implements Parcelable {

    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("latlng")
    private String mLatlng;
    @SerializedName("ResImg")
    private String mResImg;
    @SerializedName("ResLowPrice")
    private String mResLowPrice;
    @SerializedName("ResName")
    private String mResName;

    public ResDataDao() {
    }

    protected ResDataDao(Parcel in) {
        mIDRestaurant = in.readString();
        mLatlng = in.readString();
        mResImg = in.readString();
        mResLowPrice = in.readString();
        mResName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIDRestaurant);
        dest.writeString(mLatlng);
        dest.writeString(mResImg);
        dest.writeString(mResLowPrice);
        dest.writeString(mResName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ResDataDao> CREATOR = new Creator<ResDataDao>() {
        @Override
        public ResDataDao createFromParcel(Parcel in) {
            return new ResDataDao(in);
        }

        @Override
        public ResDataDao[] newArray(int size) {
            return new ResDataDao[size];
        }
    };

    public String getmIDRestaurant() {
        return mIDRestaurant;
    }

    public void setmIDRestaurant(String mIDRestaurant) {
        this.mIDRestaurant = mIDRestaurant;
    }

    public String getmLatlng() {
        return mLatlng;
    }

    public void setmLatlng(String mLatlng) {
        this.mLatlng = mLatlng;
    }

    public String getmResImg() {
        return mResImg;
    }

    public void setmResImg(String mResImg) {
        this.mResImg = mResImg;
    }

    public String getmResLowPrice() {
        return mResLowPrice;
    }

    public void setmResLowPrice(String mResLowPrice) {
        this.mResLowPrice = mResLowPrice;
    }

    public String getmResName() {
        return mResName;
    }

    public void setmResName(String mResName) {
        this.mResName = mResName;
    }
}
