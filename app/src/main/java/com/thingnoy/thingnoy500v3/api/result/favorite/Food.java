
package com.thingnoy.thingnoy500v3.api.result.favorite;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.DetailFood;

@SuppressWarnings("unused")
public class Food {

    @SerializedName("CustomerFName")
    private String mCustomerFName;
    @SerializedName("CustomerLName")
    private String mCustomerLName;
    @SerializedName("detailfood")
    private List<DetailFood> mDetailfood;
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

    public void setCustomerFName(String customerFName) {
        mCustomerFName = customerFName;
    }

    public String getCustomerLName() {
        return mCustomerLName;
    }

    public void setCustomerLName(String customerLName) {
        mCustomerLName = customerLName;
    }

    public List<DetailFood> getDetailfood() {
        return mDetailfood;
    }

    public void setDetailfood(List<DetailFood> detailfood) {
        mDetailfood = detailfood;
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

    public String getFoodTypeName() {
        return mFoodTypeName;
    }

    public void setFoodTypeName(String foodTypeName) {
        mFoodTypeName = foodTypeName;
    }

    public String getIDCustomer() {
        return mIDCustomer;
    }

    public void setIDCustomer(String iDCustomer) {
        mIDCustomer = iDCustomer;
    }

    public String getIDFavoriteManu() {
        return mIDFavoriteManu;
    }

    public void setIDFavoriteManu(String iDFavoriteManu) {
        mIDFavoriteManu = iDFavoriteManu;
    }

    public String getIDFood() {
        return mIDFood;
    }

    public void setIDFood(String iDFood) {
        mIDFood = iDFood;
    }

    public String getIDFoodType() {
        return mIDFoodType;
    }

    public void setIDFoodType(String iDFoodType) {
        mIDFoodType = iDFoodType;
    }

    public String getMenuTypeName() {
        return mMenuTypeName;
    }

    public void setMenuTypeName(String menuTypeName) {
        mMenuTypeName = menuTypeName;
    }

}
