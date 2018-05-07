
package com.thingnoy.thingnoy500v3.api.result.history;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Order implements Parcelable{

    @SerializedName("CustomerAddNo")
    private String mCustomerAddNo;
    @SerializedName("CustomerAddRoad")
    private String mCustomerAddRoad;
    @SerializedName("EmpFName")
    private String mEmpFName;
    @SerializedName("EmpLname")
    private String mEmpLname;
    @SerializedName("IDOrder")
    private String mIDOrder;
    @SerializedName("OrderDate")
    private String mOrderDate;
    @SerializedName("OrderNote")
    private String mOrderNote;
    @SerializedName("OrderStatus")
    private String mOrderStatus;
    @SerializedName("OrderTotalPrice")
    private String mOrderTotalPrice;
    @SerializedName("Orderpayprice")
    private String mOrderpayprice;
    @SerializedName("PaymentName")
    private String mPaymentName;

    protected Order(Parcel in) {
        mCustomerAddNo = in.readString();
        mCustomerAddRoad = in.readString();
        mEmpFName = in.readString();
        mEmpLname = in.readString();
        mIDOrder = in.readString();
        mOrderDate = in.readString();
        mOrderNote = in.readString();
        mOrderStatus = in.readString();
        mOrderTotalPrice = in.readString();
        mOrderpayprice = in.readString();
        mPaymentName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCustomerAddNo);
        dest.writeString(mCustomerAddRoad);
        dest.writeString(mEmpFName);
        dest.writeString(mEmpLname);
        dest.writeString(mIDOrder);
        dest.writeString(mOrderDate);
        dest.writeString(mOrderNote);
        dest.writeString(mOrderStatus);
        dest.writeString(mOrderTotalPrice);
        dest.writeString(mOrderpayprice);
        dest.writeString(mPaymentName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getCustomerAddNo() {
        return mCustomerAddNo;
    }

    public void setCustomerAddNo(String CustomerAddNo) {
        mCustomerAddNo = CustomerAddNo;
    }

    public String getCustomerAddRoad() {
        return mCustomerAddRoad;
    }

    public void setCustomerAddRoad(String CustomerAddRoad) {
        mCustomerAddRoad = CustomerAddRoad;
    }

    public String getEmpFName() {
        return mEmpFName;
    }

    public void setEmpFName(String EmpFName) {
        mEmpFName = EmpFName;
    }

    public String getEmpLname() {
        return mEmpLname;
    }

    public void setEmpLname(String EmpLname) {
        mEmpLname = EmpLname;
    }

    public String getIDOrder() {
        return mIDOrder;
    }

    public void setIDOrder(String IDOrder) {
        mIDOrder = IDOrder;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(String OrderDate) {
        mOrderDate = OrderDate;
    }

    public String getOrderNote() {
        return mOrderNote;
    }

    public void setOrderNote(String OrderNote) {
        mOrderNote = OrderNote;
    }

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        mOrderStatus = OrderStatus;
    }

    public String getOrderTotalPrice() {
        return mOrderTotalPrice;
    }

    public void setOrderTotalPrice(String OrderTotalPrice) {
        mOrderTotalPrice = OrderTotalPrice;
    }

    public String getOrderpayprice() {
        return mOrderpayprice;
    }

    public void setOrderpayprice(String Orderpayprice) {
        mOrderpayprice = Orderpayprice;
    }

    public String getPaymentName() {
        return mPaymentName;
    }

    public void setPaymentName(String PaymentName) {
        mPaymentName = PaymentName;
    }

}
