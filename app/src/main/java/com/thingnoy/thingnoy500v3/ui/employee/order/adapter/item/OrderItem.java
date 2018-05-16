
package com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.emporder.Food;
import com.thingnoy.thingnoy500v3.api.result.emporder.Order;

import java.util.ArrayList;
import java.util.List;

import static com.thingnoy.thingnoy500v3.util.FoodProductType.TYPE_EMP_ORDER;

@SuppressWarnings("unused")
public class OrderItem extends BaseItem implements Parcelable {

    private List<Food> mFood;
    private Order mOrder;

    public OrderItem() {
        super(TYPE_EMP_ORDER);
    }

    protected OrderItem(Parcel in) {
        mFood = in.createTypedArrayList(Food.CREATOR);
        mOrder = in.readParcelable(Order.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mFood);
        dest.writeParcelable(mOrder, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    public List<Food> getFood() {
        return mFood;
    }

    public OrderItem setFood(List<Food> mFood) {
        this.mFood = mFood;
        return this;
    }

    public List<BaseItem> getBaseItems(){
        List<BaseItem> baseItems = new ArrayList<>(  );
        for( Food food : mFood){
            baseItems.add(food);
        }
        return baseItems;
    }

    public Order getOrder() {
        return mOrder;
    }

    public OrderItem setOrder(Order order) {
        mOrder = order;
        return this;
    }

}
