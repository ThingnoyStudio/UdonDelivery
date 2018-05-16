package com.thingnoy.thingnoy500v3.event.event_main;

import com.thingnoy.thingnoy500v3.ui.moreinfo.foodmenu.adapter.item.FoodProductItem;


public class AddFoodFavoriteToCartEvent {


    private FoodProductItem item;

    public AddFoodFavoriteToCartEvent(FoodProductItem item) {
        this.item = item;
    }

    public FoodProductItem getItem() {
        return item;
    }
}
