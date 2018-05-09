
package com.thingnoy.thingnoy500v3.api.result.favorite;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Res {

    @SerializedName("IDLocation")
    private String mIDLocation;
    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("latlng")
    private String mLatlng;
    @SerializedName("ResAddress")
    private String mResAddress;
    @SerializedName("ResImg")
    private String mResImg;
    @SerializedName("ResLowPrice")
    private String mResLowPrice;
    @SerializedName("ResName")
    private String mResName;
    @SerializedName("ResTel")
    private String mResTel;
    @SerializedName("ResTimeEnd")
    private String mResTimeEnd;
    @SerializedName("ResTimeStart")
    private String mResTimeStart;

    public String getIDLocation() {
        return mIDLocation;
    }

    public void setIDLocation(String IDLocation) {
        mIDLocation = IDLocation;
    }

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

    public String getResAddress() {
        return mResAddress;
    }

    public void setResAddress(String ResAddress) {
        mResAddress = ResAddress;
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

    public String getResTel() {
        return mResTel;
    }

    public void setResTel(String ResTel) {
        mResTel = ResTel;
    }

    public String getResTimeEnd() {
        return mResTimeEnd;
    }

    public void setResTimeEnd(String ResTimeEnd) {
        mResTimeEnd = ResTimeEnd;
    }

    public String getResTimeStart() {
        return mResTimeStart;
    }

    public void setResTimeStart(String ResTimeStart) {
        mResTimeStart = ResTimeStart;
    }

}
