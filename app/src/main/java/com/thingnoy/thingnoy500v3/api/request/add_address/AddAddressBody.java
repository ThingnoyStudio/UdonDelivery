
package com.thingnoy.thingnoy500v3.api.request.add_address;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class AddAddressBody {

    @SerializedName("CustomerAddNo")
    private int mCustomerAddNo;
    @SerializedName("IDCustomer")
    private int mIDCustomer;
    @SerializedName("IDLocation")
    private int mIDLocation;

    public int getCustomerAddNo() {
        return mCustomerAddNo;
    }

    public void setCustomerAddNo(int CustomerAddNo) {
        mCustomerAddNo = CustomerAddNo;
    }

    public int getIDCustomer() {
        return mIDCustomer;
    }

    public void setIDCustomer(int IDCustomer) {
        mIDCustomer = IDCustomer;
    }

    public int getIDLocation() {
        return mIDLocation;
    }

    public void setIDLocation(int IDLocation) {
        mIDLocation = IDLocation;
    }

}
