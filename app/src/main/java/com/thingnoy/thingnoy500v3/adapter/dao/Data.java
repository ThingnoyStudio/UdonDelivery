
package com.thingnoy.thingnoy500v3.adapter.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

    @SerializedName("Normalmenu")
    private List<Normalmenu> mNormalmenu;
    @SerializedName("RecommendedMenu")
    private List<RecommendedMenu> mRecommendedMenu;
    @SerializedName("restaurant")
    private Restaurant mRestaurant;

    public Data() {
    }

    protected Data(Parcel in) {
        mNormalmenu = in.createTypedArrayList(Normalmenu.CREATOR);
        mRecommendedMenu = in.createTypedArrayList(RecommendedMenu.CREATOR);
        mRestaurant = in.readParcelable(Restaurant.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mNormalmenu);
        dest.writeTypedList(mRecommendedMenu);
        dest.writeParcelable(mRestaurant, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public List<Normalmenu> getmNormalmenu() {
        return mNormalmenu;
    }

    public void setmNormalmenu(List<Normalmenu> mNormalmenu) {
        this.mNormalmenu = mNormalmenu;
    }

    public List<RecommendedMenu> getmRecommendedMenu() {
        return mRecommendedMenu;
    }

    public void setmRecommendedMenu(List<RecommendedMenu> mRecommendedMenu) {
        this.mRecommendedMenu = mRecommendedMenu;
    }

    public Restaurant getmRestaurant() {
        return mRestaurant;
    }

    public void setmRestaurant(Restaurant mRestaurant) {
        this.mRestaurant = mRestaurant;
    }
}
