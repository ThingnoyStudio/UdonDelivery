
package com.thingnoy.thingnoy500v3.api.result.foodMenu.fds;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class RecommendedMenu {

    @SerializedName("menu")
    private Menu mMenu;

    public Menu getMenu() {
        return mMenu;
    }

    public void setMenu(Menu menu) {
        mMenu = menu;
    }

    @Override
    public String toString() {
        return "RecommendedMenu{" +
                "mMenu=" + mMenu +
                '}';
    }
}
