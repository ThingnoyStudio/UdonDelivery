
package com.thingnoy.thingnoy500v3.api.result.favorite;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("ResStatus")
    private String mResStatus;
    @SerializedName("ResTel")
    private String mResTel;
    @SerializedName("ResTimeEnd")
    private String mResTimeEnd;
    @SerializedName("ResTimeStart")
    private String mResTimeStart;
    @SerializedName("Respro")
    private List<ResPro> mRespro;

    public String getIDLocation() {
        return mIDLocation;
    }

    public void setIDLocation(String iDLocation) {
        mIDLocation = iDLocation;
    }

    public String getIDRestaurant() {
        return mIDRestaurant;
    }

    public void setIDRestaurant(String iDRestaurant) {
        mIDRestaurant = iDRestaurant;
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

    public void setResAddress(String resAddress) {
        mResAddress = resAddress;
    }

    public String getResImg() {
        return mResImg;
    }

    public void setResImg(String resImg) {
        mResImg = resImg;
    }

    public String getResLowPrice() {
        return mResLowPrice;
    }

    public void setResLowPrice(String resLowPrice) {
        mResLowPrice = resLowPrice;
    }

    public String getResName() {
        return mResName;
    }

    public void setResName(String resName) {
        mResName = resName;
    }

    public String getResStatus() {
        return mResStatus;
    }

    public void setResStatus(String resStatus) {
        mResStatus = resStatus;
    }

    public String getResTel() {
        return mResTel;
    }

    public void setResTel(String resTel) {
        mResTel = resTel;
    }

    public String getResTimeEnd() {
        return mResTimeEnd;
    }

    public void setResTimeEnd(String resTimeEnd) {
        mResTimeEnd = resTimeEnd;
    }

    public String getResTimeStart() {
        return mResTimeStart;
    }

    public void setResTimeStart(String resTimeStart) {
        mResTimeStart = resTimeStart;
    }

    public List<ResPro> getmRespro() {
        return mRespro;
    }

    public void setmRespro(List<ResPro> mRespro) {
        this.mRespro = mRespro;
    }
}
