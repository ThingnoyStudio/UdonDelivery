package com.thingnoy.thingnoy500v3.ui.bottom.profile.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.util.FoodProductType;

import java.util.List;

public class AddressItemGroup extends BaseItem implements Parcelable {
    private List<DataUserAddress> addresses;

    public AddressItemGroup() {
        super(FoodProductType.TYPE_ADDRESS);
    }

    protected AddressItemGroup(Parcel in) {
        addresses = in.createTypedArrayList(DataUserAddress.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(addresses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddressItemGroup> CREATOR = new Creator<AddressItemGroup>() {
        @Override
        public AddressItemGroup createFromParcel(Parcel in) {
            return new AddressItemGroup(in);
        }

        @Override
        public AddressItemGroup[] newArray(int size) {
            return new AddressItemGroup[size];
        }
    };

    public List<DataUserAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DataUserAddress> addresses) {
        this.addresses = addresses;
    }
}
