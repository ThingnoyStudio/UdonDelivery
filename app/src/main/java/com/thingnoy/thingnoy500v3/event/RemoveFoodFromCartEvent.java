package com.thingnoy.thingnoy500v3.event;

import com.thingnoy.thingnoy500v3.adapter.item.FoodProductItem;


public class RemoveFoodFromCartEvent {


    private FoodProductItem item;

    public RemoveFoodFromCartEvent(FoodProductItem item) {
        this.item = item;
    }

    public FoodProductItem getItem() {
        return item;
    }

}
