package com.thingnoy.thingnoy500v3.ui.profile.adapter;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.profile.Address;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;
import com.thingnoy.thingnoy500v3.ui.profile.adapter.item.AddressItemGroup;

import java.util.ArrayList;
import java.util.List;

public class ProfileConverter {
    public static AddressItemGroup createAddressItemFromResult(
            List<Address> result){
        AddressItemGroup itemGroup = new AddressItemGroup();
        itemGroup.setAddresses(createAddressItem(result));
        return itemGroup;
    }

    private static List<DataUserAddress> createAddressItem(
            List<Address> addressList){
        List<DataUserAddress> items = new ArrayList<>();
        for (Address address : addressList){
            DataUserAddress userAddress = new DataUserAddress();
            userAddress.setCustomerAddNo(address.getCustomerAddNo());
            userAddress.setCustomerAddRoad(address.getCustomerAddRoad());
            userAddress.setmIDCustomerAddress(Integer.parseInt(address.getIDCustomerAddress()));
            items.add(userAddress);
        }
        return items;
    }
}
