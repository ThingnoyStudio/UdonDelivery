
package com.thingnoy.thingnoy500v3.api.result.emporder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Order implements Parcelable {

    @SerializedName("CustomerAddNo")
    private String mCustomerAddNo;
    @SerializedName("CustomerAddRoad")
    private String mCustomerAddRoad;
    @SerializedName("CustomerFName")
    private String mCustomerFName;
    @SerializedName("CustomerLName")
    private String mCustomerLName;
    @SerializedName("CustomerPhone")
    private String mCustomerPhone;
    @SerializedName("EmpFName")
    private String mEmpFName;
    @SerializedName("EmpLname")
    private String mEmpLname;
    @SerializedName("IDCustomer")
    private String mIDCustomer;
    @SerializedName("IDCustomerAddress")
    private String mIDCustomerAddress;
    @SerializedName("IDEmp")
    private String mIDEmp;
    @SerializedName("IDOrder")
    private String mIDOrder;
    @SerializedName("IDPaymant")
    private String mIDPaymant;
    @SerializedName("map")
    private String mMap;
    @SerializedName("OrderDate")
    private String mOrderDate;
    @SerializedName("OrderNote")
    private Object mOrderNote;
    @SerializedName("OrderStatus")
    private String mOrderStatus;
    @SerializedName("OrderTotalPrice")
    private String mOrderTotalPrice;
    @SerializedName("Orderpayprice")
    private String mOrderpayprice;
    @SerializedName("PaymentName")
    private String mPaymentName;

    public Order() {
    }


    protected Order(Parcel in) {
        mCustomerAddNo = in.readString();
        mCustomerAddRoad = in.readString();
        mCustomerFName = in.readString();
        mCustomerLName = in.readString();
        mCustomerPhone = in.readString();
        mEmpFName = in.readString();
        mEmpLname = in.readString();
        mIDCustomer = in.readString();
        mIDCustomerAddress = in.readString();
        mIDEmp = in.readString();
        mIDOrder = in.readString();
        mIDPaymant = in.readString();
        mMap = in.readString();
        mOrderDate = in.readString();
        mOrderStatus = in.readString();
        mOrderTotalPrice = in.readString();
        mOrderpayprice = in.readString();
        mPaymentName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCustomerAddNo);
        dest.writeString(mCustomerAddRoad);
        dest.writeString(mCustomerFName);
        dest.writeString(mCustomerLName);
        dest.writeString(mCustomerPhone);
        dest.writeString(mEmpFName);
        dest.writeString(mEmpLname);
        dest.writeString(mIDCustomer);
        dest.writeString(mIDCustomerAddress);
        dest.writeString(mIDEmp);
        dest.writeString(mIDOrder);
        dest.writeString(mIDPaymant);
        dest.writeString(mMap);
        dest.writeString(mOrderDate);
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

    public void setCustomerAddNo(String customerAddNo) {
        mCustomerAddNo = customerAddNo;
    }

    public String getCustomerAddRoad() {
        return mCustomerAddRoad;
    }

    public void setCustomerAddRoad(String customerAddRoad) {
        mCustomerAddRoad = customerAddRoad;
    }

    public String getCustomerFName() {
        return mCustomerFName;
    }

    public void setCustomerFName(String customerFName) {
        mCustomerFName = customerFName;
    }

    public String getCustomerLName() {
        return mCustomerLName;
    }

    public void setCustomerLName(String customerLName) {
        mCustomerLName = customerLName;
    }

    public String getCustomerPhone() {
        return mCustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        mCustomerPhone = customerPhone;
    }

    public String getEmpFName() {
        return mEmpFName;
    }

    public void setEmpFName(String empFName) {
        mEmpFName = empFName;
    }

    public String getEmpLname() {
        return mEmpLname;
    }

    public void setEmpLname(String empLname) {
        mEmpLname = empLname;
    }

    public String getIDCustomer() {
        return mIDCustomer;
    }

    public void setIDCustomer(String iDCustomer) {
        mIDCustomer = iDCustomer;
    }

    public String getIDCustomerAddress() {
        return mIDCustomerAddress;
    }

    public void setIDCustomerAddress(String iDCustomerAddress) {
        mIDCustomerAddress = iDCustomerAddress;
    }

    public String getIDEmp() {
        return mIDEmp;
    }

    public void setIDEmp(String iDEmp) {
        mIDEmp = iDEmp;
    }

    public String getIDOrder() {
        return mIDOrder;
    }

    public void setIDOrder(String iDOrder) {
        mIDOrder = iDOrder;
    }

    public String getIDPaymant() {
        return mIDPaymant;
    }

    public void setIDPaymant(String iDPaymant) {
        mIDPaymant = iDPaymant;
    }

    public String getMap() {
        return mMap;
    }

    public void setMap(String map) {
        mMap = map;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(String orderDate) {
        mOrderDate = orderDate;
    }

    public Object getOrderNote() {
        return mOrderNote;
    }

    public void setOrderNote(Object orderNote) {
        mOrderNote = orderNote;
    }

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        mOrderStatus = orderStatus;
    }

    public String getOrderTotalPrice() {
        return mOrderTotalPrice;
    }

    public void setOrderTotalPrice(String orderTotalPrice) {
        mOrderTotalPrice = orderTotalPrice;
    }

    public String getOrderpayprice() {
        return mOrderpayprice;
    }

    public void setOrderpayprice(String orderpayprice) {
        mOrderpayprice = orderpayprice;
    }

    public String getPaymentName() {
        return mPaymentName;
    }

    public void setPaymentName(String paymentName) {
        mPaymentName = paymentName;
    }

}
