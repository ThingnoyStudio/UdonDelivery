
package com.thingnoy.thingnoy500v3.api.result.promotion;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class PromotionDao implements Parcelable {

    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("ResImg")
    private String mResImg;
    @SerializedName("ResLowPrice")
    private String mResLowPrice;
    @SerializedName("ResName")
    private String mResName;
    @SerializedName("ResPromotionEnd")
    private String mResPromotionEnd;
    @SerializedName("ResPromotionName")
    private String mResPromotionName;
    @SerializedName("ResPromotionStart")
    private String mResPromotionStart;
    @SerializedName("IDResPromotion")
    private String mIDResPromotion;

    public PromotionDao() {
    }

    protected PromotionDao(Parcel in) {
        mIDRestaurant = in.readString();
        mResImg = in.readString();
        mResLowPrice = in.readString();
        mResName = in.readString();
        mResPromotionEnd = in.readString();
        mResPromotionName = in.readString();
        mResPromotionStart = in.readString();
        mIDResPromotion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIDRestaurant);
        dest.writeString(mResImg);
        dest.writeString(mResLowPrice);
        dest.writeString(mResName);
        dest.writeString(mResPromotionEnd);
        dest.writeString(mResPromotionName);
        dest.writeString(mResPromotionStart);
        dest.writeString(mIDResPromotion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PromotionDao> CREATOR = new Creator<PromotionDao>() {
        @Override
        public PromotionDao createFromParcel(Parcel in) {
            return new PromotionDao(in);
        }

        @Override
        public PromotionDao[] newArray(int size) {
            return new PromotionDao[size];
        }
    };

    public void setmIDRestaurant(String mIDRestaurant) {
        this.mIDRestaurant = mIDRestaurant;
    }

    public void setmResImg(String mResImg) {
        this.mResImg = mResImg;
    }

    public void setmResLowPrice(String mResLowPrice) {
        this.mResLowPrice = mResLowPrice;
    }

    public void setmResName(String mResName) {
        this.mResName = mResName;
    }

    public void setmResPromotionEnd(String mResPromotionEnd) {
        this.mResPromotionEnd = mResPromotionEnd;
    }

    public void setmResPromotionName(String mResPromotionName) {
        this.mResPromotionName = mResPromotionName;
    }

    public void setmResPromotionStart(String mResPromotionStart) {
        this.mResPromotionStart = mResPromotionStart;
    }

    public void setmIDResPromotion(String mIDResPromotion) {
        this.mIDResPromotion = mIDResPromotion;
    }

    public String getmIDRestaurant() {
        return mIDRestaurant;
    }

    public String getmResImg() {
        return mResImg;
    }

    public String getmResLowPrice() {
        return mResLowPrice;
    }

    public String getmResName() {
        return mResName;
    }

    public String getmResPromotionEnd() {
        return mResPromotionEnd;
    }

    public String getmResPromotionName() {
        return mResPromotionName;
    }

    public String getmResPromotionStart() {
        return mResPromotionStart;
    }

    public String getmIDResPromotion() {
        return mIDResPromotion;
    }
}
