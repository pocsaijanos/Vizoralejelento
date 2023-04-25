package com.example.vizoralejelento;

public class PeriodItem {

    private String month;
    private String quantity;


    public PeriodItem(String month, String quantity) {
        this.month = month;
        this.quantity = quantity;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
