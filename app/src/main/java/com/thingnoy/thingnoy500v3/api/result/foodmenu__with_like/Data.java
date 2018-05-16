
package com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Data {

    @SerializedName("Normalmenu")
    private List<com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.Normalmenu> mNormalmenu;
    @SerializedName("RecommendedMenu")
    private List<com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.RecommendedMenu> mRecommendedMenu;
    @SerializedName("restaurant")
    private Restaurant mRestaurant;

    public List<com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.Normalmenu> getNormalmenu() {
        return mNormalmenu;
    }

    public void setNormalmenu(List<com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.Normalmenu> Normalmenu) {
        mNormalmenu = Normalmenu;
    }

    public List<com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.RecommendedMenu> getRecommendedMenu() {
        return mRecommendedMenu;
    }

    public void setRecommendedMenu(List<com.thingnoy.thingnoy500v3.api.result.foodmenu__with_like.RecommendedMenu> RecommendedMenu) {
        mRecommendedMenu = RecommendedMenu;
    }

    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;
    }

}
