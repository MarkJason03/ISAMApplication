package com.example.fyp_application.Model;

public class AssetCategoryModel {


    private int assetCategoryID;
    private String assetCategoryName;


    public AssetCategoryModel(int assetCategoryID, String assetCategoryName) {
        this.assetCategoryID = assetCategoryID;
        this.assetCategoryName = assetCategoryName;
    }


    //getters and setters
    public int getAssetCategoryID() {
        return assetCategoryID;
    }


    public String getAssetCategoryName() {
        return assetCategoryName;
    }

    public void setAssetCategoryName(String assetCategoryName) {
        this.assetCategoryName = assetCategoryName;
    }
    public void setAssetCategoryID(int assetCategoryID) {
        this.assetCategoryID = assetCategoryID;
    }


    @Override
    public String toString() {
        return assetCategoryName;
    }



}
