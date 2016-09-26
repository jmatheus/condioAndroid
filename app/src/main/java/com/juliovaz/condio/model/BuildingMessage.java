package com.juliovaz.condio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    public String getCreatedAtFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, HH:mm");
        SimpleDateFormat sdfDecode = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String createdAtFormatted = this.createdAt;
        try {
            Date createdAtDate = sdfDecode.parse(this.createdAt);
            createdAtFormatted = sdf.format(createdAtDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return createdAtFormatted;
    }
}

