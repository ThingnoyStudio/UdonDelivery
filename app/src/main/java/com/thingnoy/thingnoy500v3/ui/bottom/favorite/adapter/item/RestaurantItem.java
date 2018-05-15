
package com.thingnoy.thingnoy500v3.ui.bottom.favorite.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_RESTAURANT;

@SuppressWarnings("unused")
public class RestaurantItem extends BaseItem implements Parcelable{

    @SerializedName("IDLocation")
    private String mIDLocation;
    @SerializedName("IDRestaurant")
    private String mIDRestaurant;
    @SerializedName("latlng")
    private String mLatlng;
    @SerializedName("ResAddress")
    private String mResAddress;
    @SerializedName("ResImg")
    private String mResImg;
    @SerializedName("ResLowPrice")
    private String mResLowPrice;
    @SerializedName("ResName")
    private String mResName;
    @SerializedName("ResTel")
    private String mResTel;
    @SerializedName("ResTimeEnd")
    private String mResTimeEnd;
    @SerializedName("ResTimeStart")
    private String mResTimeStart;

    public RestaurantItem() {
        super(TYPE_RESTAURANT);
    }

    protected RestaurantItem(Parcel in) {
        mIDLocation = in.readString();
        mIDRestaurant = in.readString();
        mLatlng = in.readString();
        mResAddress = in.readString();
        mResImg = in.readString();
        mResLowPrice = in.readString();
        mResName = in.readString();
        mResTel = in.readString();
        mResTimeEnd = in.readString();
        mResTimeStart = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mIDLocation);
        dest.writeString(mIDRestaurant);
        dest.writeString(mLatlng);
        dest.writeString(mResAddress);
        dest.writeString(mResImg);
        dest.writeString(mResLowPrice);
        dest.writeString(mResName);
        dest.writeString(mResTel);
        dest.writeString(mResTimeEnd);
        dest.writeString(mResTimeStart);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RestaurantItem> CREATOR = new Creator<RestaurantItem>() {
        @Override
        public RestaurantItem createFromParcel(Parcel in) {
            return new RestaurantItem(in);
        }

        @Override
        public RestaurantItem[] newArray(int size) {
            return new RestaurantItem[size];
        }
    };

    public String getIDLocation() {
        return mIDLocation;
    }

    public void setIDLocation(String IDLocation) {
        mIDLocation = IDLocation;
    }

    public String getIDRestaurant() {
        return mIDRestaurant;
    }

    public void setIDRestaurant(String IDRestaurant) {
        mIDRestaurant = IDRestaurant;
    }

    public String getLatlng() {
        return mLatlng;
    }

    public void setLatlng(String latlng) {
        mLatlng = latlng;
    }

    public String getResAddress() {
        return mResAddress;
    }

    public void setResAddress(String ResAddress) {
        mResAddress = ResAddress;
    }

    public String getResImg() {
        return mResImg;
    }

    public void setResImg(String ResImg) {
        mResImg = ResImg;
    }

    public String getResLowPrice() {
        return mResLowPrice;
    }

    public void setResLowPrice(String ResLowPrice) {
        mResLowPrice = ResLowPrice;
    }

    public String getResName() {
        return mResName;
    }

    public void setResName(String ResName) {
        mResName = ResName;
    }

    public String getResTel() {
        return mResTel;
    }

    public void setResTel(String ResTel) {
        mResTel = ResTel;
    }

    public String getResTimeEnd() {
        return mResTimeEnd;
    }

    public void setResTimeEnd(String ResTimeEnd) {
        mResTimeEnd = ResTimeEnd;
    }

    public String getResTimeStart() {
        return mResTimeStart;
    }

    public void setResTimeStart(String ResTimeStart) {
        mResTimeStart = ResTimeStart;
    }

}
