
package com.thingnoy.thingnoy500v3.adapter.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Normalmenu implements Parcelable{

    @SerializedName("FoodImg")
    private String mFoodImg;
    @SerializedName("FoodName")
    private String mFoodName;
    @SerializedName("FoodPrice")
    private double mFoodPrice;
    @SerializedName("FoodTypeName")
    private String mFoodTypeName;
    @SerializedName("IDFood")
    private int mIDFood;
    @SerializedName("IDFoodType")
    private int mIDFoodType;
    @SerializedName("IDRestaurant")
    private int mIDRestaurant;
    @SerializedName("MenuTypeName")
    private String mMenuTypeName;

    public Normalmenu() {
    }

    protected Normalmenu(Parcel in) {
        mFoodImg = in.readString();
        mFoodName = in.readString();
        mFoodPrice = in.readDouble();
        mFoodTypeName = in.readString();
        mIDFood = in.readInt();
        mIDFoodType = in.readInt();
        mIDRestaurant = in.readInt();
        mMenuTypeName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFoodImg);
        dest.writeString(mFoodName);
        dest.writeDouble(mFoodPrice);
        dest.writeString(mFoodTypeName);
        dest.writeInt(mIDFood);
        dest.writeInt(mIDFoodType);
        dest.writeInt(mIDRestaurant);
        dest.writeString(mMenuTypeName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Normalmenu> CREATOR = new Creator<Normalmenu>() {
        @Override
        public Normalmenu createFromParcel(Parcel in) {
            return new Normalmenu(in);
        }

        @Override
        public Normalmenu[] newArray(int size) {
            return new Normalmenu[size];
        }
    };

    public String getmFoodImg() {
        return mFoodImg;
    }

    public void setmFoodImg(String mFoodImg) {
        this.mFoodImg = mFoodImg;
    }

    public String getmFoodName() {
        return mFoodName;
    }

    public void setmFoodName(String mFoodName) {
        this.mFoodName = mFoodName;
    }

    public double getmFoodPrice() {
        return mFoodPrice;
    }

    public void setmFoodPrice(double mFoodPrice) {
        this.mFoodPrice = mFoodPrice;
    }

    public String getmFoodTypeName() {
        return mFoodTypeName;
    }

    public void setmFoodTypeName(String mFoodTypeName) {
        this.mFoodTypeName = mFoodTypeName;
    }

    public int getmIDFood() {
        return mIDFood;
    }

    public void setmIDFood(int mIDFood) {
        this.mIDFood = mIDFood;
    }

    public int getmIDFoodType() {
        return mIDFoodType;
    }

    public void setmIDFoodType(int mIDFoodType) {
        this.mIDFoodType = mIDFoodType;
    }

    public int getmIDRestaurant() {
        return mIDRestaurant;
    }

    public void setmIDRestaurant(int mIDRestaurant) {
        this.mIDRestaurant = mIDRestaurant;
    }

    public String getmMenuTypeName() {
        return mMenuTypeName;
    }

    public void setmMenuTypeName(String mMenuTypeName) {
        this.mMenuTypeName = mMenuTypeName;
    }
}
