package com.example.alahsaafforestation.model;

public class Pharmacy {

    String id;
    String name;
    String pharmacistName;
    String imageUrl;
    String address;
    String phone;
    String opensAt;
    String closeAt;
    String creationDate;

    public Pharmacy(String id, String name, String pharmacistName, String imageUrl, String address, String phone, String opensAt, String closeAt, String creationDate) {
        this.id = id;
        this.name = name;
        this.pharmacistName = pharmacistName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.phone = phone;
        this.opensAt = opensAt;
        this.closeAt = closeAt;
        this.creationDate = creationDate;
    }

    public Pharmacy(String name, String pharmacistName, String imageUrl, String address, String phone, String opensAt, String closeAt, String creationDate) {
        this.name = name;
        this.pharmacistName = pharmacistName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.phone = phone;
        this.opensAt = opensAt;
        this.closeAt = closeAt;
        this.creationDate = creationDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpensAt() {
        return opensAt;
    }

    public void setOpensAt(String opensAt) {
        this.opensAt = opensAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
