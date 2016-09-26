package com.juliovaz.condio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by juliovaz on 9/24/16.
 */
public class Bill implements Serializable {

    private int id;

    @SerializedName("due_month")
    private String dueMonth;

    private User user;

    private String status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDueMonth() {
        return dueMonth;
    }

    public void setDueMonth(String dueMonth) {
        this.dueMonth = dueMonth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
