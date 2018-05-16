
package com.thingnoy.thingnoy500v3.api.result.emporder;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EmpOrder {

    @SerializedName("food")
    private List<Food> mFood;
    @SerializedName("order")
    private Order mOrder;

    public List<Food> getFood() {
        return mFood;
    }

    public void setFood(List<Food> food) {
        mFood = food;
    }

    public Order getOrder() {
        return mOrder;
    }

    public void setOrder(Order order) {
        mOrder = order;
    }

}
