package com.example.alahsaafforestation.model;

public class SellerOrder {

    private int id;
    private String productName;
    private String customerName;
    private double price;
    private int quantity;

    public SellerOrder(int id, String productName, String customerName, double price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.customerName = customerName;
        this.price = price;
        this.quantity = quantity;
    }

    public SellerOrder(String productName, String customerName, double price, int quantity) {
        this.productName = productName;
        this.customerName = customerName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getPrice() {
            return price;
        }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
