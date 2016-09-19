package com.juliovaz.condio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by julio on 07/05/16.
 */
public class BuildingMessage implements Serializable {

    private User user;

    @SerializedName("message_description")
    private String messageDescription;

    @SerializedName("message_title")
    private String messageTitle;

    @SerializedName("created_at")
    private String createdAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    public void setMessageDescription(String messageDescription) {
        this.messageDescription = messageDescription;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}

