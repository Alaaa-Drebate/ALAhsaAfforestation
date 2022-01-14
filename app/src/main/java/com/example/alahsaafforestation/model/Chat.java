package com.example.alahsaafforestation.model;

public class Chat {

    String chatId;
    String fromId;
    String userName;
    String userImageUrl;

    public Chat(String usrName, String fromId, String userImageUrl) {
        this.userName = usrName;
        this.fromId = fromId;
        this.userImageUrl = userImageUrl;
    }

    public Chat(String userId, String userName, String fromId, String userImageUrl) {
        this.chatId = userId;
        this.userName = userName;
        this.fromId = fromId;
        this.userImageUrl = userImageUrl;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
}
