package com.thingnoy.thingnoy500v3.event_ordering;

import com.thingnoy.thingnoy500v3.api.dao.PaymentDao;


public class SelectPaymentEvent {


    private PaymentDao item;

    public SelectPaymentEvent(PaymentDao item) {
        this.item = item;
    }

    public PaymentDao getItem() {
        return item;
    }
}
