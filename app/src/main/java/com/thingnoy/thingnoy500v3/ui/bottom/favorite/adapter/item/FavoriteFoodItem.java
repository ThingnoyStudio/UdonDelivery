
package com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_FAVORITE;

@SuppressWarnings("unused")
public class FavoriteFoodItem extends BaseItem implements Parcelable{

    @SerializedName("CustomerFName")
    private String mCustomerFName;
    @SerializedName("CustomerLName")
    private String mCustomerLName;
    @SerializedName("FoodImg")
    private String mFoodImg;
    @SerializedName("FoodName")
    private String mFoodName;
    @SerializedName("FoodPrice")
    private String mFoodPrice;
    @SerializedName("FoodTypeName")
    private String mFoodTypeName;
    @SerializedName("IDCustomer")
    private String mIDCustomer;
    @SerializedName("IDFavoriteManu")
    private String mIDFavoriteManu;
    @SerializedName("IDFood")
    private String mIDFood;
    @SerializedName("IDFoodType")
    private String mIDFoodType;
    @SerializedName("MenuTypeName")
    private String mMenuTypeName;

    private boolean isAdded = false;

    public FavoriteFoodItem() {
        super(TYPE_FAVORITE);
    }


    protected FavoriteFoodItem(Parcel in) {
        mCustomerFName = in.readString();
        mCustomerLName = in.readString();
        mFoodImg = in.readString();
        mFoodName = in.readString();
        mFoodPrice = in.readString();
        mFoodTypeName = in.readString();
        mIDCustomer = in.readString();
        mIDFavoriteManu = in.readString();
        mIDFood = in.readString();
        mIDFoodType = in.readString();
        mMenuTypeName = in.readString();
        isAdded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCustomerFName);
        dest.writeString(mCustomerLName);
        dest.writeString(mFoodImg);
        dest.writeString(mFoodName);
        dest.writeString(mFoodPrice);
        dest.writeString(mFoodTypeName);
        dest.writeString(mIDCustomer);
        dest.writeString(mIDFavoriteManu);
        dest.writeString(mIDFood);
        dest.writeString(mIDFoodType);
        dest.writeString(mMenuTypeName);
        dest.writeByte((byte) (isAdded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FavoriteFoodItem> CREATOR = new Creator<FavoriteFoodItem>() {
        @Override
        public FavoriteFoodItem createFromParcel(Parcel in) {
            return new FavoriteFoodItem(in);
        }

        @Override
        public FavoriteFoodItem[] newArray(int size) {
            return new FavoriteFoodItem[size];
        }
    };

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getCustomerFName() {
        return mCustomerFName;
    }

    public void setCustomerFName(String CustomerFName) {
        mCustomerFName = CustomerFName;
    }

    public String getCustomerLName() {
        return mCustomerLName;
    }

    public void setCustomerLName(String CustomerLName) {
        mCustomerLName = CustomerLName;
    }

    public String getFoodImg() {
        return mFoodImg;
    }

    public void setFoodImg(String FoodImg) {
        mFoodImg = FoodImg;
    }

    public String getFoodName() {
        return mFoodName;
    }

    public void setFoodName(String FoodName) {
        mFoodName = FoodName;
    }

    public String getFoodPrice() {
        return mFoodPrice;
    }

    public void setFoodPrice(String FoodPrice) {
        mFoodPrice = FoodPrice;
    }

    public String getFoodTypeName() {
        return mFoodTypeName;
    }

    public void setFoodTypeName(String FoodTypeName) {
        mFoodTypeName = FoodTypeName;
    }

    public String getIDCustomer() {
        return mIDCustomer;
    }

    public void setIDCustomer(String IDCustomer) {
        mIDCustomer = IDCustomer;
    }

    public String getIDFavoriteManu() {
        return mIDFavoriteManu;
    }

    public void setIDFavoriteManu(String IDFavoriteManu) {
        mIDFavoriteManu = IDFavoriteManu;
    }

    public String getIDFood() {
        return mIDFood;
    }

    public void setIDFood(String IDFood) {
        mIDFood = IDFood;
    }

    public String getIDFoodType() {
        return mIDFoodType;
    }

    public void setIDFoodType(String IDFoodType) {
        mIDFoodType = IDFoodType;
    }

    public String getMenuTypeName() {
        return mMenuTypeName;
    }

    public void setMenuTypeName(String MenuTypeName) {
        mMenuTypeName = MenuTypeName;
    }

}
