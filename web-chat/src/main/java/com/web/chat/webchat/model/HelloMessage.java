package com.web.chat.webchat.model;


/**
 * Client --> Server
 */
public class HelloMessage {
    private String message;

    public HelloMessage() {
    }

    public HelloMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
