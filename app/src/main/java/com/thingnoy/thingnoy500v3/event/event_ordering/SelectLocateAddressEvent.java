package com.thingnoy.thingnoy500v3.event.event_ordering;

import com.google.android.gms.maps.model.LatLng;


public class SelectLocateAddressEvent {

    private LatLng item;

    public SelectLocateAddressEvent(LatLng item) {
        this.item = item;
    }

    public LatLng getItem() {
        return item;
    }
}
