package com.thingnoy.thingnoy500v3.event.event_main;

import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItem;


public class GoToOrderDetailActivityEvent {
    private HistoryItem item;

    public GoToOrderDetailActivityEvent(HistoryItem item) {
        this.item = item;
    }

    public HistoryItem getItem() {
        return item;
    }
}
