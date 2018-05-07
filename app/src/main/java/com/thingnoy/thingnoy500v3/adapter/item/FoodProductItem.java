package com.thingnoy.thingnoy500v3.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.api.result.foodMenu.fds.DetailFood;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.List;
import java.util.Objects;

/**
 * Created by HBO on 23/2/2561.
 */

public class FoodProductItem extends BaseItem implements Parcelable {

    // region Variable
    private String mFoodImg;
    private String mFoodName;
    private String mFoodTypeName;
    private String mIDFood;
    private List<DetailFoodItem> detailFoods;
    private String reason;
    private DetailFoodItem addOn ;

    private double price;
    private int amount = 1;
    private boolean isAdded = false;
    //endregion

    // constructor
    public FoodProductItem() {
        super(FoodProductType.TYPE_ORDER);
    }

    //region Parcelable
    protected FoodProductItem(Parcel in) {
        mFoodImg = in.readString();
        mFoodName = in.readString();
        mFoodTypeName = in.readString();
        mIDFood = in.readString();
        detailFoods = in.createTypedArrayList(DetailFoodItem.CREATOR);
        reason = in.readString();
        addOn = in.readParcelable(DetailFoodItem.class.getClassLoader());
        price = in.readDouble();
        amount = in.readInt();
        isAdded = in.readByte() != 0;
    }

    public static final Creator<FoodProductItem> CREATOR = new Creator<FoodProductItem>() {
        @Override
        public FoodProductItem createFromParcel(Parcel in) {
            return new FoodProductItem(in);
        }

        @Override
        public FoodProductItem[] newArray(int size) {
            return new FoodProductItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFoodImg);
        dest.writeString(mFoodName);
        dest.writeString(mFoodTypeName);
        dest.writeString(mIDFood);
        dest.writeTypedList(detailFoods);
        dest.writeString(reason);
        dest.writeParcelable(addOn, flags);
        dest.writeDouble(price);
        dest.writeInt(amount);
        dest.writeByte((byte) (isAdded ? 1 : 0));
    }
    //endregion

    //region Getter & Setter
    public DetailFoodItem getAddOn() {
        return addOn;
    }

    public void setAddOn(DetailFoodItem addOn) {
        this.addOn = addOn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<DetailFoodItem> getDetailFoods() {
        return detailFoods;
    }

    public void setDetailFoods(List<DetailFoodItem> detailFoods) {
        this.detailFoods = detailFoods;
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

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    // cal...
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

    public void clearAmount() {
        amount = 1;
    }
    //endregion


    @Override
    public String toString() {
        return "FoodProductItem{" +
                "mFoodImg='" + mFoodImg + '\'' +
                ", mFoodName='" + mFoodName + '\'' +
                ", mFoodTypeName='" + mFoodTypeName + '\'' +
                ", mIDFood='" + mIDFood + '\'' +
                ", detailFoods=" + detailFoods +
                ", reason='" + reason + '\'' +
                ", addOn=" + addOn +
                ", price=" + price +
                ", amount=" + amount +
                ", isAdded=" + isAdded +
                '}';
    }
}