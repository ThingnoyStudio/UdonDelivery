package com.thingnoy.thingnoy500v3.api.dao;

public class PaymentDao {
    private int paymentType;
    private double cash;

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }
}
