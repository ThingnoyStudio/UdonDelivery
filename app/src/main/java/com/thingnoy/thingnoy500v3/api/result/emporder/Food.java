
package com.thingnoy.thingnoy500v3.api.result.emporder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_ORDER;

@SuppressWarnings("unused")
public class Food extends BaseItem implements Parcelable {

    @SerializedName("AmountFood")
    private String mAmountFood;
    @SerializedName("FoodImg")
    private String mFoodImg;
    @SerializedName("FoodName")
    private String mFoodName;
    @SerializedName("FoodPrice")
    private String mFoodPrice;
    @SerializedName("IDFood")
    private String mIDFood;
    @SerializedName("IDFoodDetails")
    private String mIDFoodDetails;
    @SerializedName("IDFoodType")
    private String mIDFoodType;
    @SerializedName("IDOrder")
    private String mIDOrder;
    @SerializedName("IDOrderDetails")
    private String mIDOrderDetails;
    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("MenuTypeName")
    private String mMenuTypeName;
    @SerializedName("reason")
    private String mReason;
    @SerializedName("ResName")
    private String mResName;
@SerializedName("FoodDetailName")
    private String mFoodDetailName;
@SerializedName("FoodDetailsPrice")
    private String mFoodDetailsPrice;

    public Food() {
        super(TYPE_ORDER);
    }


    protected Food(Parcel in) {
        mAmountFood = in.readString();
        mFoodImg = in.readString();
        mFoodName = in.readString();
        mFoodPrice = in.readString();
        mIDFood = in.readString();
        mIDFoodDetails = in.readString();
        mIDFoodType = in.readString();
        mIDOrder = in.readString();
        mIDOrderDetails = in.readString();
        mIDRestaurant = in.readString();
        mMenuTypeName = in.readString();
        mReason = in.readString();
        mResName = in.readString();
        mFoodDetailName = in.readString();
        mFoodDetailsPrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAmountFood);
        dest.writeString(mFoodImg);
        dest.writeString(mFoodName);
        dest.writeString(mFoodPrice);
        dest.writeString(mIDFood);
        dest.writeString(mIDFoodDetails);
        dest.writeString(mIDFoodType);
        dest.writeString(mIDOrder);
        dest.writeString(mIDOrderDetails);
        dest.writeString(mIDRestaurant);
        dest.writeString(mMenuTypeName);
        dest.writeString(mReason);
        dest.writeString(mResName);
        dest.writeString(mFoodDetailName);
        dest.writeString(mFoodDetailsPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getmFoodDetailName() {
        return mFoodDetailName;
    }

    public void setmFoodDetailName(String mFoodDetailName) {
        this.mFoodDetailName = mFoodDetailName;
    }

    public String getmFoodDetailsPrice() {
        return mFoodDetailsPrice;
    }

    public void setmFoodDetailsPrice(String mFoodDetailsPrice) {
        this.mFoodDetailsPrice = mFoodDetailsPrice;
    }

    public String getAmountFood() {
        return mAmountFood;
    }

    public void setAmountFood(String amountFood) {
        mAmountFood = amountFood;
    }

    public String getFoodImg() {
        return mFoodImg;
    }

    public void setFoodImg(String foodImg) {
        mFoodImg = foodImg;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public void setFoodName(String foodName) {
        mFoodName = foodName;
    }

    public String getFoodPrice() {
        return mFoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        mFoodPrice = foodPrice;
    }

    public String getIDFood() {
        return mIDFood;
    }

    public void setIDFood(String iDFood) {
        mIDFood = iDFood;
    }

    public String getIDFoodDetails() {
        return mIDFoodDetails;
    }

    public void setIDFoodDetails(String iDFoodDetails) {
        mIDFoodDetails = iDFoodDetails;
    }

    public String getIDFoodType() {
        return mIDFoodType;
    }

    public void setIDFoodType(String iDFoodType) {
        mIDFoodType = iDFoodType;
    }

    public String getIDOrder() {
        return mIDOrder;
    }

    public void setIDOrder(String iDOrder) {
        mIDOrder = iDOrder;
    }

    public String getIDOrderDetails() {
        return mIDOrderDetails;
    }

    public void setIDOrderDetails(String iDOrderDetails) {
        mIDOrderDetails = iDOrderDetails;
    }

    public String getIDRestaurant() {
        return mIDRestaurant;
    }

    public void setIDRestaurant(String iDRestaurant) {
        mIDRestaurant = iDRestaurant;
    }

    public String getMenuTypeName() {
        return mMenuTypeName;
    }

    public void setMenuTypeName(String menuTypeName) {
        mMenuTypeName = menuTypeName;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    public String getResName() {
        return mResName;
    }

    public void setResName(String resName) {
        mResName = resName;
    }

}
