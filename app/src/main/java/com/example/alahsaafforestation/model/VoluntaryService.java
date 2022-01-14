package com.example.alahsaafforestation.model;

public class VoluntaryService {

    private int id;
    private String description;
    private int volunteerId;

    public VoluntaryService(int id, String description, int volunteerId) {
        this.id = id;
        this.description = description;
        this.volunteerId = volunteerId;
    }

    public VoluntaryService(String description, int volunteerId) {
        this.description = description;
        this.volunteerId = volunteerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }
}
