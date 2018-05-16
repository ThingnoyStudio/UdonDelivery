package com.thingnoy.thingnoy500v3.event.event_main;

import com.thingnoy.thingnoy500v3.ui.employee.order.adapter.item.OrderItem;


public class GoToEmpOrderDetailEvent {
    private OrderItem item;

    public GoToEmpOrderDetailEvent(OrderItem item) {
        this.item = item;
    }

    public OrderItem getItem() {
        return item;
    }
}
