package com.thingnoy.thingnoy500v3.event_ordering;

import com.thingnoy.thingnoy500v3.api.request.Address;
import com.thingnoy.thingnoy500v3.api.result.userAddress.DataUserAddress;


public class SelectAddressEvent {


    private DataUserAddress item;

    public SelectAddressEvent(DataUserAddress item) {
        this.item = item;
    }

    public DataUserAddress getItem() {
        return item;
    }
}
