
package com.thingnoy.thingnoy500v3.api.result.history;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class HistoryResult {

    @SerializedName("food")
    private List<FoodDetail> mFoodDetail;
    @SerializedName("order")
    private Order mOrder;

    public List<FoodDetail> getFood() {
        return mFoodDetail;
    }

    public void setFood(List<FoodDetail> foodDetail) {
        mFoodDetail = foodDetail;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
    }

}
