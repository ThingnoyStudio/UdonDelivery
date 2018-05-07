
package com.thingnoy.thingnoy500v3.api.result.new_restaurant;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Res {

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

    public String getIDRestaurant() {
        return mIDRestaurant;
    }

    public void setIDRestaurant(String IDRestaurant) {
        mIDRestaurant = IDRestaurant;
    }

    public String getLatlng() {
        return mLatlng;
    }

    public void setLatlng(String latlng) {
        mLatlng = latlng;
    }

    public String getResImg() {
        return mResImg;
    }

    public void setResImg(String ResImg) {
        mResImg = ResImg;
    }

    public String getResLowPrice() {
        return mResLowPrice;
    }

    public void setResLowPrice(String ResLowPrice) {
        mResLowPrice = ResLowPrice;
    }

    public String getResName() {
        return mResName;
    }

    public void setResName(String ResName) {
        mResName = ResName;
    }

}
