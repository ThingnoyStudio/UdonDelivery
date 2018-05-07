package com.thingnoy.thingnoy500v3.api.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;
import com.thingnoy.thingnoy500v3.util.DateUtil;

import java.util.List;

public class AddNewOrderBody  implements Parcelable{
    private String mOrderDate = new DateUtil().getCurrentLocalDateTimeStamp();
    private int mCustomerId;
    private int mPaymentType;
    private boolean mPaymentStatus = false;
    private double mTotalPrice;
    private int mRestaurantId;
    private List<FoodProductItem> mItems;
    private Address address;
    private double mPay;
    private double mDeliveryFee;
    private int mPromotionId;
    private int mDeliveryTimeId;
    private String mDeliveryTime;

    public AddNewOrderBody() {
    }

    protected AddNewOrderBody(Parcel in) {
        mOrderDate = in.readString();
        mCustomerId = in.readInt();
        mPaymentType = in.readInt();
        mPaymentStatus = in.readByte() != 0;
        mTotalPrice = in.readDouble();
        mRestaurantId = in.readInt();
        mItems = in.createTypedArrayList(FoodProductItem.CREATOR);
        mPay = in.readDouble();
        mDeliveryFee = in.readDouble();
        mPromotionId = in.readInt();
        mDeliveryTimeId = in.readInt();
        mDeliveryTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mOrderDate);
        dest.writeInt(mCustomerId);
        dest.writeInt(mPaymentType);
        dest.writeByte((byte) (mPaymentStatus ? 1 : 0));
        dest.writeDouble(mTotalPrice);
        dest.writeInt(mRestaurantId);
        dest.writeTypedList(mItems);
        dest.writeDouble(mPay);
        dest.writeDouble(mDeliveryFee);
        dest.writeInt(mPromotionId);
        dest.writeInt(mDeliveryTimeId);
        dest.writeString(mDeliveryTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddNewOrderBody> CREATOR = new Creator<AddNewOrderBody>() {
        @Override
        public AddNewOrderBody createFromParcel(Parcel in) {
            return new AddNewOrderBody(in);
        }

        @Override
        public AddNewOrderBody[] newArray(int size) {
            return new AddNewOrderBody[size];
        }
    };

    public String getmDeliveryTime() {
        return mDeliveryTime;
    }

    public void setmDeliveryTime(String mDeliveryTime) {
        this.mDeliveryTime = mDeliveryTime;
    }

    public int getmDeliveryTimeId() {
        return mDeliveryTimeId;
    }

    public void setmDeliveryTimeId(int mDeliveryTimeId) {
        this.mDeliveryTimeId = mDeliveryTimeId;
    }

    public double getmPay() {
        return mPay;
    }

    public void setmPay(double mPay) {
        this.mPay = mPay;
    }

    public double getmDeliveryFee() {
        return mDeliveryFee;
    }

    public void setmDeliveryFee(double mDeliveryFee) {
        this.mDeliveryFee = mDeliveryFee;
    }

    public int getmPromotionId() {
        return mPromotionId;
    }

    public void setmPromotionId(int mPromotionId) {
        this.mPromotionId = mPromotionId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getmOrderDate() {
        return mOrderDate;
    }

//    public void setmOrderDate(String mOrderDate) {
//        this.mOrderDate = mOrderDate;
//    }

    public int getmCustomerId() {
        return mCustomerId;
    }

    public void setmCustomerId(int mCustomerId) {
        this.mCustomerId = mCustomerId;
    }

    public int getmPaymentType() {
        return mPaymentType;
    }

    public void setmPaymentType(int mPaymentType) {
        this.mPaymentType = mPaymentType;
    }

    public boolean ismPaymentStatus() {
        return mPaymentStatus;
    }

    public void setmPaymentStatus(boolean mPaymentStatus) {
        this.mPaymentStatus = mPaymentStatus;
    }

    public double getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(double mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public int getmRestaurantId() {
        return mRestaurantId;
    }

    public void setmRestaurantId(int mRestaurantId) {
        this.mRestaurantId = mRestaurantId;
    }

    public List<FoodProductItem> getmItems() {
        return mItems;
    }

    public void setmItems(List<FoodProductItem> mItems) {
        this.mItems = mItems;
    }

}
