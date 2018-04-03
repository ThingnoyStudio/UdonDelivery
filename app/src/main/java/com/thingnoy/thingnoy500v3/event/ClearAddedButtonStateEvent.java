package com.thingnoy.thingnoy500v3.event;

import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;


public class ClearAddedButtonStateEvent {
    private FoodProductItem item;

    public ClearAddedButtonStateEvent(FoodProductItem item) {
        this.item = item;
    }

    public FoodProductItem getItem() {
        return item;
    }
}
