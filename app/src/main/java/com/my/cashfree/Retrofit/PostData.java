package com.my.cashfree.Retrofit;

public class PostData {
    String orderId;
    int orderAmount;
    String orderCurrency;

    public PostData(String orderId, int orderAmount, String orderCurrency) {
        this.orderId = orderId;
        this.orderAmount = orderAmount;
        this.orderCurrency = orderCurrency;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderCurrency() {
        return orderCurrency;
    }

    public void setOrderCurrency(String orderCurrency) {
        this.orderCurrency = orderCurrency;
    }
}
