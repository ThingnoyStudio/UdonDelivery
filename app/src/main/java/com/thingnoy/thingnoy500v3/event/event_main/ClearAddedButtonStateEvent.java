package com.thingnoy.thingnoy500v3.event.event_main;

import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;


public class ClearAddedButtonStateEvent {
    private FoodProductItem item;

    public ClearAddedButtonStateEvent(FoodProductItem item) {
        this.item = item;
    }

    public FoodProductItem getItem() {
        return item;
    }
}
