
package com.thingnoy.thingnoy500v3.api.result.foodMenu.fds;

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
    private double mFoodPrice;
    @SerializedName("FoodTypeName")
    private String mFoodTypeName;
    @SerializedName("IDFood")
    private int mIDFood;
    @SerializedName("IDFoodType")
    private int mIDFoodType;
    @SerializedName("IDRestaurant")
    private int mIDRestaurant;
    @SerializedName("MenuTypeName")
    private String mMenuTypeName;

    public List<DetailFood> getmDetailFood() {
        return mDetailFood;
    }

    public void setmDetailFood(List<DetailFood> mDetailFood) {
        this.mDetailFood = mDetailFood;
    }

    public String getmFoodImg() {
        return mFoodImg;
    }

    public void setmFoodImg(String mFoodImg) {
        this.mFoodImg = mFoodImg;
    }

    public String getmFoodName() {
        return mFoodName;
    }

    public void setmFoodName(String mFoodName) {
        this.mFoodName = mFoodName;
    }

    public double getmFoodPrice() {
        return mFoodPrice;
    }

    public void setmFoodPrice(double mFoodPrice) {
        this.mFoodPrice = mFoodPrice;
    }

    public String getmFoodTypeName() {
        return mFoodTypeName;
    }

    public void setmFoodTypeName(String mFoodTypeName) {
        this.mFoodTypeName = mFoodTypeName;
    }

    public int getmIDFood() {
        return mIDFood;
    }

    public void setmIDFood(int mIDFood) {
        this.mIDFood = mIDFood;
    }

    public int getmIDFoodType() {
        return mIDFoodType;
    }

    public void setmIDFoodType(int mIDFoodType) {
        this.mIDFoodType = mIDFoodType;
    }

    public int getmIDRestaurant() {
        return mIDRestaurant;
    }

    public void setmIDRestaurant(int mIDRestaurant) {
        this.mIDRestaurant = mIDRestaurant;
    }

    public String getmMenuTypeName() {
        return mMenuTypeName;
    }

    public void setmMenuTypeName(String mMenuTypeName) {
        this.mMenuTypeName = mMenuTypeName;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "mDetailFood=" + mDetailFood +
                ", mFoodImg='" + mFoodImg + '\'' +
                ", mFoodName='" + mFoodName + '\'' +
                ", mFoodPrice='" + mFoodPrice + '\'' +
                ", mFoodTypeName='" + mFoodTypeName + '\'' +
                ", mIDFood='" + mIDFood + '\'' +
                ", mIDFoodType='" + mIDFoodType + '\'' +
                ", mIDRestaurant='" + mIDRestaurant + '\'' +
                ", mMenuTypeName='" + mMenuTypeName + '\'' +
                '}';
    }
}
