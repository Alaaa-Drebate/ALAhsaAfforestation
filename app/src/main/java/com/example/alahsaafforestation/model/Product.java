package com.example.alahsaafforestation.model;

public class Product {

    int id;
    int sellerId;
    String sellerName;
    String imageUrl;
    String name;
    String description;
    String plantingDate;
    String plantingAddress;
    double price;
    int availableQuantity;

    public Product(int id, int sellerId, String sellerName, String imageUrl, String name, String description, String plantingDate, String plantingAddress, double price, int availableQuantity) {
        this.id = id;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.plantingDate = plantingDate;
        this.plantingAddress = plantingAddress;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public Product(int id, String name, double price, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public Product(int sellerId, String imageUrl, String name, String description, String plantingDate, double price, int availableQuantity) {
        this.sellerId = sellerId;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.plantingDate = plantingDate;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public Product(int id, String imageUrl, String name) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public Product(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public Product(String name, double price, int availableQuantity) {
        this.name = name;
        this.price = price;
        this.availableQuantity = availableQuantity;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getPlantingAddress() {
        return plantingAddress;
    }

    public void setPlantingAddress(String plantingAddress) {
        this.plantingAddress = plantingAddress;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlantingDate() {
        return plantingDate;
    }

    public void setPlantingDate(String plantingDate) {
        this.plantingDate = plantingDate;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
