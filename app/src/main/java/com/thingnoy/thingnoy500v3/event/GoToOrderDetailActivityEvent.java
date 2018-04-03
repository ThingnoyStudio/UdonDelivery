package com.thingnoy.thingnoy500v3.event;

import com.thingnoy.thingnoy500v3.adapter.item.HistoryItem;


public class GoToOrderDetailActivityEvent {


    private HistoryItem item;

    public GoToOrderDetailActivityEvent(HistoryItem item) {
        this.item = item;
    }

    public HistoryItem getItem() {
        return item;
    }
}
