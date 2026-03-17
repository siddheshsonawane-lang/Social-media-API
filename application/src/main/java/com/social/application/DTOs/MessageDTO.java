package com.social.application.DTOs;

import com.social.application.Models.User;

public class MessageDTO {

    private String senderUsername;
    private String content;

    public MessageDTO(String senderUsername, String content) {
        this.senderUsername = senderUsername;
        this.content = content;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getContent() {
        return content;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
