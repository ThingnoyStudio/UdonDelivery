
package com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;


@SuppressWarnings("unused")
public class DetailFoodItem extends BaseItem implements Parcelable {

    @SerializedName("FoodDetailName")
    private String mFoodDetailName;
    @SerializedName("FoodDetailsPrice")
    private double mFoodDetailsPrice;
    @SerializedName("IDFoodDetails")
    private String mIDFoodDetails;

    private int selectedIndex = 0;

    public DetailFoodItem() {
    }

    protected DetailFoodItem(Parcel in) {
        mFoodDetailName = in.readString();
        mFoodDetailsPrice = in.readDouble();
        mIDFoodDetails = in.readString();
        selectedIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFoodDetailName);
        dest.writeDouble(mFoodDetailsPrice);
        dest.writeString(mIDFoodDetails);
        dest.writeInt(selectedIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetailFoodItem> CREATOR = new Creator<DetailFoodItem>() {
        @Override
        public DetailFoodItem createFromParcel(Parcel in) {
            return new DetailFoodItem(in);
        }

        @Override
        public DetailFoodItem[] newArray(int size) {
            return new DetailFoodItem[size];
        }
    };



    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String getFoodDetailName() {
        return mFoodDetailName;
    }

    public void setFoodDetailName(String FoodDetailName) {
        mFoodDetailName = FoodDetailName;
    }

    public double getFoodDetailsPrice() {
        return mFoodDetailsPrice;
    }

    public void setFoodDetailsPrice(double FoodDetailsPrice) {
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
        return "DetailFoodItem{" +
                "mFoodDetailName='" + mFoodDetailName + '\'' +
                ", mFoodDetailsPrice=" + mFoodDetailsPrice +
                ", mIDFoodDetails='" + mIDFoodDetails + '\'' +
                ", selectedIndex=" + selectedIndex +
                '}';
    }
}
