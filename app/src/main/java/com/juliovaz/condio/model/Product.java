package com.juliovaz.condio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by juliovaz on 10/3/16.
 */
public class Product implements Serializable {

    private int id;

    @SerializedName("maintenance_date")
    private String maintenanceDate;

    private BuildingLocation buildingLocation;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaintenanceDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat sdfDecode = new SimpleDateFormat("yyyy-MM-dd");
        String maintenanceDateFormatted = this.maintenanceDate;
        try {
            Date maintenaceDateDecoded = sdfDecode.parse(this.maintenanceDate);
            maintenanceDateFormatted = sdf.format(maintenaceDateDecoded);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return maintenanceDateFormatted;
    }

    public String getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public BuildingLocation getBuildingLocation() {
        return buildingLocation;
    }

    public void setBuildingLocation(BuildingLocation buildingLocation) {
        this.buildingLocation = buildingLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
