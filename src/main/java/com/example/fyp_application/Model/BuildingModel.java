package com.example.fyp_application.Model;

public class BuildingModel {


    private int buildingID; // Assuming categoryID is an integer
    private String buildingName;

    // Constructor
    public BuildingModel(int buildingId,String buildingName) {
        this.buildingID = buildingId;
        this.buildingName = buildingName;
    }

    // Getters and Setters
    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }


    @Override
    public String toString() {
        return buildingName;
    }
}
