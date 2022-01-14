package com.example.alahsaafforestation.model;

public class Volunteer {

    int id;
    String imageUrl;
    String personName;
    String description;
    String publishDate;
    int availability;
    String phoneNumber;
    String address;

    public Volunteer(int id, String imageUrl, String personName) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.personName = personName;
    }

    public Volunteer(String imageUrl, String personName) {
        this.imageUrl = imageUrl;
        this.personName = personName;
    }

    public Volunteer(int id, String personName, String description, int availability, String imageUrl, String phoneNumber, String address) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.personName = personName;
        this.description = description;
        this.availability = availability;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Volunteer(int id, String imageUrl, String personName, String description, String publishDate, int availability, String phoneNumber, String address) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.personName = personName;
        this.description = description;
        this.publishDate = publishDate;
        this.availability = availability;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    public Volunteer(String imageUrl, String personName, String description, String publishDate, int availability, String phoneNumber, String address) {
        this.imageUrl = imageUrl;
        this.personName = personName;
        this.description = description;
        this.publishDate = publishDate;
        this.availability = availability;
        this.phoneNumber = phoneNumber;
        this.address = address;
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


    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

