package com.thingnoy.thingnoy500v3.event_ordering;


public class GetAddressFromMapEvent {


    private boolean status;

    public GetAddressFromMapEvent(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }
}
