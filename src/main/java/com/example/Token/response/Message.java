package com.example.Token.response;

import lombok.Data;

@Data
public class Message {
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
