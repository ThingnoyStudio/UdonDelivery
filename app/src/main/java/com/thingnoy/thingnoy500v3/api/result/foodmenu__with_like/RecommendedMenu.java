
package com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like;

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

}
