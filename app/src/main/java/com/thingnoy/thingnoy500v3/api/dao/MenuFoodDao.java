package com.thingnoy.thingnoy500v3.api.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HBO on 12/2/2561.
 */

public class MenuFoodDao implements Parcelable {

    @SerializedName("IDRestaurant")
    private int IDRestaurant;
    @SerializedName("MenuTypeName")
    private String MenuTypeName;
    @SerializedName("IDFood")
    private int IDFood;
    @SerializedName("IDFoodType")
    private int IDFoodType;
    @SerializedName("FoodPrice")
    private double FoodPrice;
    @SerializedName("FoodImg")
    private String FoodImg;
    @SerializedName("FoodName")
    private String FoodName;

    public MenuFoodDao() {
    }

    protected MenuFoodDao(Parcel in) {
        IDRestaurant = in.readInt();
        MenuTypeName = in.readString();
        IDFood = in.readInt();
        IDFoodType = in.readInt();
        FoodPrice = in.readDouble();
        FoodImg = in.readString();
        FoodName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(IDRestaurant);
        dest.writeString(MenuTypeName);
        dest.writeInt(IDFood);
        dest.writeInt(IDFoodType);
        dest.writeDouble(FoodPrice);
        dest.writeString(FoodImg);
        dest.writeString(FoodName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MenuFoodDao> CREATOR = new Creator<MenuFoodDao>() {
        @Override
        public MenuFoodDao createFromParcel(Parcel in) {
            return new MenuFoodDao(in);
        }

        @Override
        public MenuFoodDao[] newArray(int size) {
            return new MenuFoodDao[size];
        }
    };

    public int getIDRestaurant() {
        return IDRestaurant;
    }

    public void setIDRestaurant(int IDRestaurant) {
        this.IDRestaurant = IDRestaurant;
    }

    public String getMenuTypeName() {
        return MenuTypeName;
    }

    public void setMenuTypeName(String menuTypeName) {
        MenuTypeName = menuTypeName;
    }

    public int getIDFood() {
        return IDFood;
    }

    public void setIDFood(int IDFood) {
        this.IDFood = IDFood;
    }

    public int getIDFoodType() {
        return IDFoodType;
    }

    public void setIDFoodType(int IDFoodType) {
        this.IDFoodType = IDFoodType;
    }

    public double getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getFoodImg() {
        return FoodImg;
    }

    public void setFoodImg(String foodImg) {
        FoodImg = foodImg;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }
}
