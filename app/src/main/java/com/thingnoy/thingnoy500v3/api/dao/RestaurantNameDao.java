package com.thingnoy.thingnoy500v3.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HBO on 12/2/2561.
 */

public class RestaurantNameDao implements Parcelable {
    @SerializedName("IDRestaurant")
    private int IDRestaurant;
    @SerializedName("ResImg")
    private String ResImg;
    @SerializedName("ResLowPrice")
    private int ResLowPrice;
    @SerializedName("ResName")
    private String ResName;

    public RestaurantNameDao() {
    }

    protected RestaurantNameDao(Parcel in) {
        IDRestaurant = in.readInt();
        ResImg = in.readString();
        ResLowPrice = in.readInt();
        ResName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IDRestaurant);
        dest.writeString(ResImg);
        dest.writeInt(ResLowPrice);
        dest.writeString(ResName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantNameDao> CREATOR = new Creator<RestaurantNameDao>() {
        @Override
        public RestaurantNameDao createFromParcel(Parcel in) {
            return new RestaurantNameDao(in);
        }

        @Override
        public RestaurantNameDao[] newArray(int size) {
            return new RestaurantNameDao[size];
        }
    };

    public int getIDRestaurant() {
        return IDRestaurant;
    }

    public void setIDRestaurant(int IDRestaurant) {
        this.IDRestaurant = IDRestaurant;
    }

    public String getResImg() {
        return ResImg;
    }

    public void setResImg(String resImg) {
        ResImg = resImg;
    }

    public int getResLowPrice() {
        return ResLowPrice;
    }

    public void setResLowPrice(int resLowPrice) {
        ResLowPrice = resLowPrice;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }
}
