
package com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Menu {

    @SerializedName("detailFood")
    private List<DetailFood> mDetailFood;
    @SerializedName("FoodImg")
    private String mFoodImg;
    @SerializedName("FoodName")
    private String mFoodName;
    @SerializedName("FoodPrice")
    private String mFoodPrice;
    @SerializedName("FoodTypeName")
    private String mFoodTypeName;
    @SerializedName("IDFood")
    private String mIDFood;
    @SerializedName("IDFoodType")
    private String mIDFoodType;
    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("MenuTypeName")
    private String mMenuTypeName;
    @SerializedName("StatusFoodFavorite")
    private Boolean mStatusFoodFavorite;

    public List<DetailFood> getDetailFood() {
        return mDetailFood;
    }

    public void setDetailFood(List<DetailFood> detailFood) {
        mDetailFood = detailFood;
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

    public String getIDRestaurant() {
        return mIDRestaurant;
    }

    public void setIDRestaurant(String IDRestaurant) {
        mIDRestaurant = IDRestaurant;
    }

    public String getMenuTypeName() {
        return mMenuTypeName;
    }

    public void setMenuTypeName(String MenuTypeName) {
        mMenuTypeName = MenuTypeName;
    }

    public Boolean getStatusFoodFavorite() {
        return mStatusFoodFavorite;
    }

    public void setStatusFoodFavorite(Boolean StatusFoodFavorite) {
        mStatusFoodFavorite = StatusFoodFavorite;
    }

}
