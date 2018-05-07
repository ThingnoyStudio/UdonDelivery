
package com.thingnoy.thingnoy500v3.api.result.foodMenu.fds;

import java.util.List;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Data {

    @SerializedName("Normalmenu")
    private List<Normalmenu> mNormalmenu;
    @SerializedName("RecommendedMenu")
    private List<RecommendedMenu> mRecommendedMenu;
    @SerializedName("restaurant")
    private Restaurant mRestaurant;

    public List<Normalmenu> getNormalmenu() {
        return mNormalmenu;
    }

    public void setNormalmenu(List<Normalmenu> Normalmenu) {
        mNormalmenu = Normalmenu;
    }

    public List<RecommendedMenu> getRecommendedMenu() {
        return mRecommendedMenu;
    }

    public void setRecommendedMenu(List<RecommendedMenu> RecommendedMenu) {
        mRecommendedMenu = RecommendedMenu;
    }

    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Name{" +
                "mNormalmenu=" + mNormalmenu +
                ", mRecommendedMenu=" + mRecommendedMenu +
                ", mRestaurant=" + mRestaurant +
                '}';
    }
}
