package com.juliovaz.condio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by juliovaz on 8/2/16.
 */
public class BuildingLocation implements Serializable{

    private int id;

    private User user;

    private String name;

    private String description;

    @SerializedName("created_at")
    private String createdAt;

    private ArrayList<Reservation> listReservations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Reservation> getListReservations() {
        return listReservations;
    }

    public void setListReservations(ArrayList<Reservation> listReservations) {
        this.listReservations = listReservations;
    }
}
