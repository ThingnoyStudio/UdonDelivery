package com.thingnoy.thingnoy500v3.ui.employee.order.adapter;

import com.thingnoy.thingnoy500v3.adapter.item.BaseItem;
import com.thingnoy.thingnoy500v3.api.result.emporder.EmpOrder;
import com.thingnoy.thingnoy500v3.api.result.emporder.OrderResultGroup;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResult;
import com.thingnoy.thingnoy500v3.api.result.history.HistoryResultGroup;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItem;
import com.thingnoy.thingnoy500v3.ui.bottom.history.adapter.item.HistoryItemGroup;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItem;
import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItemGroup;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {

    public static OrderItemGroup createItemFromResult(OrderResultGroup resultGroup) {
        OrderItemGroup itemGroup = new OrderItemGroup();
        itemGroup.setOrderList(createItemFromResult(resultGroup.getData()));
        return itemGroup;
    }

    private static List<BaseItem> createItemFromResult(List<EmpOrder> resultList) {
        List<BaseItem> items = new ArrayList<>();
        for (EmpOrder empOrder : resultList) {
            OrderItem item = new OrderItem()
                    .setOrder(empOrder.getOrder())
                    .setFood(empOrder.getFood());
            items.add(item);
        }
        return items;
    }
}
