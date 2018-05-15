package com.thingnoy.thingnoy500v3.event.event_ordering;

import com.thingnoy.thingnoy500v3.api.result.derivery_time.DataDeliveryTime;


public class SelectDeliveryTimeEvent {


    private DataDeliveryTime item;

    public SelectDeliveryTimeEvent(DataDeliveryTime item) {
        this.item = item;
    }

    public DataDeliveryTime getItem() {
        return item;
    }
}
