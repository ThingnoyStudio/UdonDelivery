
package com.thingnoy.thingnoy500v3.api.result.new_restaurant;


import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class DataRestaurant {

    @SerializedName("pro")
    private Pro mPro;
    @SerializedName("res")
    private Res mRes;

    public Pro getPro() {
        return mPro;
    }

    public void setPro(Pro pro) {
        mPro = pro;
    }

    public Res getRes() {
        return mRes;
    }

    public void setRes(Res res) {
        mRes = res;
    }

}
