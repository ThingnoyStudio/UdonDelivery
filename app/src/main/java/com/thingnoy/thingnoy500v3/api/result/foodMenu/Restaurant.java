
package com.thingnoy.thingnoy500v3.adapter.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Restaurant implements Parcelable{

    @SerializedName("IDLocation")
    private int mIDLocation;
    @SerializedName("IDRestaurant")
    private int mIDRestaurant;
    @SerializedName("latlng")
    private String mLatlng;
    @SerializedName("ResAddress")
    private String mResAddress;
    @SerializedName("ResImg")
    private String mResImg;
    @SerializedName("ResLowPrice")
    private double mResLowPrice;
    @SerializedName("ResName")
    private String mResName;
    @SerializedName("ResTel")
    private String mResTel;
    @SerializedName("ResTimeEnd")
    private String mResTimeEnd;
    @SerializedName("ResTimeStart")
    private String mResTimeStart;

    public Restaurant() {
    }

    protected Restaurant(Parcel in) {
        mIDLocation = in.readInt();
        mIDRestaurant = in.readInt();
        mLatlng = in.readString();
        mResAddress = in.readString();
        mResImg = in.readString();
        mResLowPrice = in.readDouble();
        mResName = in.readString();
        mResTel = in.readString();
        mResTimeEnd = in.readString();
        mResTimeStart = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIDLocation);
        dest.writeInt(mIDRestaurant);
        dest.writeString(mLatlng);
        dest.writeString(mResAddress);
        dest.writeString(mResImg);
        dest.writeDouble(mResLowPrice);
        dest.writeString(mResName);
        dest.writeString(mResTel);
        dest.writeString(mResTimeEnd);
        dest.writeString(mResTimeStart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getmIDLocation() {
        return mIDLocation;
    }

    public void setmIDLocation(int mIDLocation) {
        this.mIDLocation = mIDLocation;
    }

    public int getmIDRestaurant() {
        return mIDRestaurant;
    }

    public void setmIDRestaurant(int mIDRestaurant) {
        this.mIDRestaurant = mIDRestaurant;
    }

    public String getmLatlng() {
        return mLatlng;
    }

    public void setmLatlng(String mLatlng) {
        this.mLatlng = mLatlng;
    }

    public String getmResAddress() {
        return mResAddress;
    }

    public void setmResAddress(String mResAddress) {
        this.mResAddress = mResAddress;
    }

    public String getmResImg() {
        return mResImg;
    }

    public void setmResImg(String mResImg) {
        this.mResImg = mResImg;
    }

    public double getmResLowPrice() {
        return mResLowPrice;
    }

    public void setmResLowPrice(double mResLowPrice) {
        this.mResLowPrice = mResLowPrice;
    }

    public String getmResName() {
        return mResName;
    }

    public void setmResName(String mResName) {
        this.mResName = mResName;
    }

    public String getmResTel() {
        return mResTel;
    }

    public void setmResTel(String mResTel) {
        this.mResTel = mResTel;
    }

    public String getmResTimeEnd() {
        return mResTimeEnd;
    }

    public void setmResTimeEnd(String mResTimeEnd) {
        this.mResTimeEnd = mResTimeEnd;
    }

    public String getmResTimeStart() {
        return mResTimeStart;
    }

    public void setmResTimeStart(String mResTimeStart) {
        this.mResTimeStart = mResTimeStart;
    }
}
