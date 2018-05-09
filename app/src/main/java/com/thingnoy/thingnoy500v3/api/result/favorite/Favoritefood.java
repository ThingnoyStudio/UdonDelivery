
package com.thingnoy.thingnoy500v3.api.result.favorite;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Favoritefood {

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
