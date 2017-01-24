package com.example.anders.devfest;

/**
 * Created by q on 2017-01-24.
 */

public class ChatData {
    private String userName;
    private String message;

    public ChatData() { }

    public ChatData(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}