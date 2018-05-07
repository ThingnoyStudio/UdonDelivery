
package com.thingnoy.thingnoy500v3.api.result.history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.util.FoodProductType;


@SuppressWarnings("unused")
public class FoodDetail extends BaseItem implements Parcelable {

    @SerializedName("AmountFood")
    private String mAmountFood;
    @SerializedName("FoodDetailName")
    private String mFoodDetailName;
    @SerializedName("FoodDetailsPrice")
    private String mFoodDetailsPrice;
    @SerializedName("FoodImg")
    private String mFoodImg;
    @SerializedName("FoodName")
    private String mFoodName;
    @SerializedName("FoodPrice")
    private String mFoodPrice;
    @SerializedName("IDFoodType")
    private String mIDFoodType;
    @SerializedName("IDOrderDetails")
    private String mIDOrderDetails;
    @SerializedName("reason")
    private String mReason;

    public FoodDetail() {
        super(FoodProductType.TYPE_FOOD_ORDER_DETAIL);
    }


    protected FoodDetail(Parcel in) {
        mAmountFood = in.readString();
        mFoodDetailName = in.readString();
        mFoodDetailsPrice = in.readString();
        mFoodImg = in.readString();
        mFoodName = in.readString();
        mFoodPrice = in.readString();
        mIDFoodType = in.readString();
        mIDOrderDetails = in.readString();
        mReason = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAmountFood);
        dest.writeString(mFoodDetailName);
        dest.writeString(mFoodDetailsPrice);
        dest.writeString(mFoodImg);
        dest.writeString(mFoodName);
        dest.writeString(mFoodPrice);
        dest.writeString(mIDFoodType);
        dest.writeString(mIDOrderDetails);
        dest.writeString(mReason);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodDetail> CREATOR = new Creator<FoodDetail>() {
        @Override
        public FoodDetail createFromParcel(Parcel in) {
            return new FoodDetail(in);
        }

        @Override
        public FoodDetail[] newArray(int size) {
            return new FoodDetail[size];
        }
    };

    public String getmReason() {
        return mReason;
    }

    public void setmReason(String mReason) {
        this.mReason = mReason;
    }

    public String getmAmountFood() {
        return mAmountFood;
    }

    public String getmFoodDetailName() {
        return mFoodDetailName;
    }

    public String getmFoodDetailsPrice() {
        return mFoodDetailsPrice;
    }

    public String getmFoodImg() {
        return mFoodImg;
    }

    public String getmFoodName() {
        return mFoodName;
    }

    public String getmFoodPrice() {
        return mFoodPrice;
    }

    public String getmIDFoodType() {
        return mIDFoodType;
    }

    public String getmIDOrderDetails() {
        return mIDOrderDetails;
    }

    public void setmAmountFood(String mAmountFood) {
        this.mAmountFood = mAmountFood;
    }

    public void setmFoodDetailName(String mFoodDetailName) {
        this.mFoodDetailName = mFoodDetailName;
    }

    public void setmFoodDetailsPrice(String mFoodDetailsPrice) {
        this.mFoodDetailsPrice = mFoodDetailsPrice;
    }

    public void setmFoodImg(String mFoodImg) {
        this.mFoodImg = mFoodImg;
    }

    public void setmFoodName(String mFoodName) {
        this.mFoodName = mFoodName;
    }

    public void setmFoodPrice(String mFoodPrice) {
        this.mFoodPrice = mFoodPrice;
    }

    public void setmIDFoodType(String mIDFoodType) {
        this.mIDFoodType = mIDFoodType;
    }

    public void setmIDOrderDetails(String mIDOrderDetails) {
        this.mIDOrderDetails = mIDOrderDetails;
    }
}
