
package com.thingnoy.thingnoy500v3.api.result.favorite;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DataFavorite {

    @SerializedName("food")
    private List<Food> mFood;
    @SerializedName("res")
    private Res mRes;

    public List<Food> getFood() {
        return mFood;
    }

    public void setFood(List<Food> food) {
        mFood = food;
    }

    public Res getRes() {
        return mRes;
    }

    public void setRes(Res res) {
        mRes = res;
    }

}
