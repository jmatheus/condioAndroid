package com.juliovaz.condio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by juliovaz on 8/15/16.
 */
public class Reservation implements Serializable {

    private int id;

    @SerializedName("user")
    private User user;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("event_date")
    private String eventDate;

    @SerializedName("building_location")
    private BuildingLocation buildingLocation;

    @SerializedName("reservation_name")
    private String reservationName;

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BuildingLocation getBuildingLocation() {
        return buildingLocation;
    }

    public void setBuildingLocation(BuildingLocation buildingLocation) {
        this.buildingLocation = buildingLocation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
