package com.thingnoy.thingnoy500v3.adapter.item;

import com.thingnoy.thingnoy500v3.util.FoodProductType;

/**
 * Created by HBO on 23/2/2561.
 */

public class OrderItem extends BaseOrderFoodItem {
    private String mFoodImg;
    private String mFoodName;
    private String mFoodPrice;
    private String mFoodTypeName;
    private String mIDFood;
//    private int mIDFoodType;
//    private int mIDRestaurant;

    public OrderItem() {
        super(FoodProductType.TYPE_ORDER);
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

    public String getmFoodPrice() {
        return mFoodPrice;
    }

    public void setmFoodPrice(String mFoodPrice) {
        this.mFoodPrice = mFoodPrice;
    }

    public String getmFoodTypeName() {
        return mFoodTypeName;
    }

    public void setmFoodTypeName(String mFoodTypeName) {
        this.mFoodTypeName = mFoodTypeName;
    }

    public String getmIDFood() {
        return mIDFood;
    }

    public void setmIDFood(String mIDFood) {
        this.mIDFood = mIDFood;
    }
}
