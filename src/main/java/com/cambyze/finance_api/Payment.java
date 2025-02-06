package com.cambyze.finance_api;

public class Payment {

    private String date;
    private Double amount;

    public Payment() {
	super();
	this.date = null;
	this.amount = null;
    }

    public Payment(String date, Double amount) {
	super();
	this.date = date;
	this.amount = amount;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public Double getAmount() {
	return amount;
    }

    public void setAmount(Double amount) {
	this.amount = amount;
    }

}
