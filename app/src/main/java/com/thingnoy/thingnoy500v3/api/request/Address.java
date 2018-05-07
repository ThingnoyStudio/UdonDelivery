package com.thingnoy.thingnoy500v3.api.request;

public class Address {
    private String mAddressName;
    private double latitude;
    private double longitude;
    private int mAddressId = 0;

    public int getmAddressId() {
        return mAddressId;
    }

    public void setmAddressId(int mAddressId) {
        this.mAddressId = mAddressId;
    }

    public String getmAddressName() {
        return mAddressName;
    }

    public void setmAddressName(String mAddressName) {
        this.mAddressName = mAddressName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Address{" +
                "mAddressName='" + mAddressName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", mAddressId=" + mAddressId +
                '}';
    }
}
