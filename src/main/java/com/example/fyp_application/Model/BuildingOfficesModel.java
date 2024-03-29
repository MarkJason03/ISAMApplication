package com.example.fyp_application.Model;

public class BuildingOfficesModel {


    // Properties

    private int officeID;

    private int buildingID;
    private String officeName;


    // Constructor
    public BuildingOfficesModel(
            int officeID,
            int buildingID,
            String officeName
    ) {
        this.officeID = officeID;
        this.buildingID = buildingID;
        this.officeName = officeName;
    }

    // Getters and Setters

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public int getBuildingID() {
        return buildingID;
    }
    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }
    @Override
    public String toString() {
        return officeName;
    }
}
