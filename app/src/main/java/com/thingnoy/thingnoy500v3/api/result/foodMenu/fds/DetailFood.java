
package com.thingnoy.thingnoy500v3.api.result.foodMenu.fds;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class DetailFood {

    @SerializedName("FoodDetailName")
    private String mFoodDetailName;
    @SerializedName("FoodDetailsPrice")
    private String mFoodDetailsPrice;
    @SerializedName("IDFoodDetails")
    private String mIDFoodDetails;

    public String getFoodDetailName() {
        return mFoodDetailName;
    }

    public void setFoodDetailName(String FoodDetailName) {
        mFoodDetailName = FoodDetailName;
    }

    public String getFoodDetailsPrice() {
        return mFoodDetailsPrice;
    }

    public void setFoodDetailsPrice(String FoodDetailsPrice) {
        mFoodDetailsPrice = FoodDetailsPrice;
    }

    public String getIDFoodDetails() {
        return mIDFoodDetails;
    }

    public void setIDFoodDetails(String IDFoodDetails) {
        mIDFoodDetails = IDFoodDetails;
    }

    @Override
    public String toString() {
        return "DetailFood{" +
                "mFoodDetailName='" + mFoodDetailName + '\'' +
                ", mFoodDetailsPrice='" + mFoodDetailsPrice + '\'' +
                ", mIDFoodDetails='" + mIDFoodDetails + '\'' +
                '}';
    }
}
