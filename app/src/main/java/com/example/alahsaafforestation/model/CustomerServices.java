package com.example.alahsaafforestation.model;

public class CustomerServices {

    private int id;
    private String customerName;
    private VoluntaryService service;
    private String createdAt;

    public CustomerServices(int id, String customerName, VoluntaryService service, String createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.service = service;
        this.createdAt = createdAt;
    }

    public CustomerServices(String customerName, VoluntaryService service, String createdAt) {
        this.customerName = customerName;
        this.service = service;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public VoluntaryService getService() {
        return service;
    }

    public void setService(VoluntaryService service) {
        this.service = service;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
