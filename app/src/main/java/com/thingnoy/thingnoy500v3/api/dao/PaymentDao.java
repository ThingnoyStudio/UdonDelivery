package com.thingnoy.thingnoy500v3.api.dao;

public class PaymentDao {
    private int paymentType;
    private double cash;
    private boolean approved;

    public int getPaymentType() {
        return paymentType;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
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
