package com.thingnoy.thingnoy500v3.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.util.FoodProductType;

/**
 * Created by HBO on 23/2/2561.
 */

public class FoodItem extends BaseOrderFoodItem implements Parcelable {
    private String mFoodImg;
    private String mFoodName;
    //    private String mFoodPrice;
    private String mFoodTypeName;
    private String mIDFood;
//    private int mIDFoodType;
//    private int mIDRestaurant;

//    private String id;
//    private String alcohol;
//    private String image;
//    private String name;

    private double price;
    //    private String volume;
    private int amount = 1;
    private boolean isAdded = false;

    public FoodItem() {
        super(FoodProductType.TYPE_ORDER);
    }


    protected FoodItem(Parcel in) {
        super(in);
        mFoodImg = in.readString();
        mFoodName = in.readString();
        mFoodTypeName = in.readString();
        mIDFood = in.readString();
        price = in.readInt();
        amount = in.readInt();
        isAdded = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFoodImg);
        dest.writeString(mFoodName);
        dest.writeString(mFoodTypeName);
        dest.writeString(mIDFood);
        dest.writeDouble(price);
        dest.writeInt(amount);
        dest.writeByte((byte) (isAdded ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FoodItem> CREATOR = new Creator<FoodItem>() {
        @Override
        public FoodItem createFromParcel(Parcel in) {
            return new FoodItem(in);
        }

        @Override
        public FoodItem[] newArray(int size) {
            return new FoodItem[size];
        }
    };



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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public int getAmount() {
//        return amount;
//    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }


    public void increaseAmount() {
        amount++;
    }

    public void decreaseAmount() {
        amount--;
        if (amount < 1) amount = 1;
    }

    public int getAmount() {
        return amount;
    }

    public void clearAmount() {
        amount = 1;
    }

}
