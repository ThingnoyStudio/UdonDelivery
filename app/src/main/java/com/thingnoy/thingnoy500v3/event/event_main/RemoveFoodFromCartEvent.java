package com.thingnoy.thingnoy500v3.event.event_main;

import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;


public class RemoveFoodFromCartEvent {


    private FoodProductItem item;

    public RemoveFoodFromCartEvent(FoodProductItem item) {
        this.item = item;
    }

    public FoodProductItem getItem() {
        return item;
    }

}
