
package com.thingnoy.thingnoy500v3.api.result.userAddress;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

@SuppressWarnings("unused")
public class DataUserAddress extends BaseItem implements Parcelable{

    @SerializedName("IDCustomerAddress")
    private int mIDCustomerAddress;
    @SerializedName("CustomerAddNo")
    private String mCustomerAddNo;
    @SerializedName("CustomerAddRoad")
    private String mCustomerAddRoad;

    private boolean isChecked = false;

    public DataUserAddress() {
        super(FoodProductType.TYPE_ADDRESS);
    }

    protected DataUserAddress(Parcel in) {
        mIDCustomerAddress = in.readInt();
        mCustomerAddNo = in.readString();
        mCustomerAddRoad = in.readString();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIDCustomerAddress);
        dest.writeString(mCustomerAddNo);
        dest.writeString(mCustomerAddRoad);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataUserAddress> CREATOR = new Creator<DataUserAddress>() {
        @Override
        public DataUserAddress createFromParcel(Parcel in) {
            return new DataUserAddress(in);
        }

        @Override
        public DataUserAddress[] newArray(int size) {
            return new DataUserAddress[size];
        }
    };

    public int getmIDCustomerAddress() {
        return mIDCustomerAddress;
    }

    public void setmIDCustomerAddress(int mIDCustomerAddress) {
        this.mIDCustomerAddress = mIDCustomerAddress;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

}
