package com.example.fyp_application.Model;

public class AssetManufacturerModel {



    private int manufacturerID;

    private String manufacturerName;


    public AssetManufacturerModel(int manufacturerID, String manufacturerName) {
        this.manufacturerID = manufacturerID;
        this.manufacturerName = manufacturerName;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    @Override
    public String toString() {
        return manufacturerName;
    }

}
