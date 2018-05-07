
package com.thingnoy.thingnoy500v3.api.result.profile;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Address {

    @SerializedName("CustomerAddNo")
    private String mCustomerAddNo;
    @SerializedName("CustomerAddRoad")
    private String mCustomerAddRoad;
    @SerializedName("IDCustomer")
    private String mIDCustomer;
    @SerializedName("IDCustomerAddress")
    private String mIDCustomerAddress;
    @SerializedName("map")
    private Object mMap;

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

    public String getIDCustomer() {
        return mIDCustomer;
    }

    public void setIDCustomer(String IDCustomer) {
        mIDCustomer = IDCustomer;
    }

    public String getIDCustomerAddress() {
        return mIDCustomerAddress;
    }

    public void setIDCustomerAddress(String IDCustomerAddress) {
        mIDCustomerAddress = IDCustomerAddress;
    }

    public Object getMap() {
        return mMap;
    }

    public void setMap(Object map) {
        mMap = map;
    }

}
