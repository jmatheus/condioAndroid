package com.juliovaz.condio.network;

import com.juliovaz.condio.model.BuildingLocation;
import com.juliovaz.condio.model.BuildingMessage;
import com.juliovaz.condio.model.Device;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.model.User;
import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by julio on 07/05/16.
 */
public interface ApiService {

    @POST("/users")
    void createUser(@Body JsonObject user, Callback<User> obj);

    @GET("/building_messages")
    void getAllBuildingMessages(Callback<ArrayList<BuildingMessage>> callback);

    @POST("/building_messages")
    void createBuildingMessage(@Body JsonObject buildingMessage, Callback<BuildingMessage> obj);

    @POST("/devices")
    void addDeviceToken(@Body JsonObject device, Callback<Device> obj);

    @POST("/authenticate")
    void sendLogin(@Body JsonObject jsonObject, Callback<User> cb);

    @GET("/building_locations")
    void getAllBuildingLocations(Callback<ArrayList<BuildingLocation>> callback);

    @GET("/reservations")
    void getAllReservations(Callback<ArrayList<Reservation>> callback);

    @POST("/reservations")
    void createReservation(@Body JsonObject reservation, Callback<JsonObject> callback);

    @GET("/building_locations/{id}")
    void getBuildingLocation(@Path("id") String locationId, Callback<BuildingLocation> callback);

    @GET("/building_locations/dates/{id}")
    void getBuildingLocationDates(@Path("id") String locationId, Callback<ArrayList<String>> dates);
}
