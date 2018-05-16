package com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemGroup implements Parcelable{
    List<OrderItem> itemGroup;

    public OrderItemGroup() {
    }

    protected OrderItemGroup(Parcel in) {
        itemGroup = in.createTypedArrayList(OrderItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(itemGroup);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderItemGroup> CREATOR = new Creator<OrderItemGroup>() {
        @Override
        public OrderItemGroup createFromParcel(Parcel in) {
            return new OrderItemGroup(in);
        }

        @Override
        public OrderItemGroup[] newArray(int size) {
            return new OrderItemGroup[size];
        }
    };

    public List<OrderItem> getItemGroup() {
        return itemGroup;
    }

    public List<BaseItem> getBaseItems(){
        List<BaseItem> baseItems = new ArrayList<>(  );
        for( OrderItem orderItem : itemGroup){
            baseItems.add(orderItem);
        }
        return baseItems;
    }

    public void setOrderList(List<BaseItem> orderList) {
        List<OrderItem> items = new ArrayList<>();
        for (BaseItem item : orderList){
            if (item instanceof OrderItem){
                items.add( (OrderItem) item);
            }
        }
        this.itemGroup = items;
    }
}
