package com.example.alahsaafforestation.model;

public class BaseMessage {

    String  id;
    boolean fromMe;
    String message;

    public BaseMessage(String id, boolean fromMe, String message) {
        this.id = id;
        this.fromMe = fromMe;
        this.message = message;
    }


    public BaseMessage(boolean byMeOrByOthers, String message) {
        this.fromMe = byMeOrByOthers;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
