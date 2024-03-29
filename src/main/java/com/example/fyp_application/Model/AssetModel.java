package com.example.fyp_application.Model;

public class AssetModel {

    private int assetID;
    private int assetCategoryID;

    private int manufacturerID;

    private String assetName;

    private String serialNumber;

    private int assetPrice;
    private String storageSpec;
    private String ramSpec;

    private String osSpec;

    private String purchaseDate;

    private String eolDate;

    private String warrantyDate;

    private String warrantyEndDate;

    private String assetStatus;


    private String assetCondition;

    private String assetPhotoPath;


    //Foreign keys name

    private String assetCategoryName;

    private String manufacturerName;


    //Constructor

    public AssetModel(
            int assetID,
            int assetCategoryID,
            int manufacturerID,
            String assetName,
            String serialNumber,
            int assetPrice,
            String storageSpec,
            String ramSpec,
            String osSpec,
            String purchaseDate,
            String eolDate,
            String warrantyDate,
            String warrantEndDate,
            String assetStatus,
            String assetCondition,
            String assetPhotoPath,
            String assetCategoryName,
            String manufacturerName
    ) {
        this.assetID = assetID;
        this.assetCategoryID = assetCategoryID;
        this.manufacturerID = manufacturerID;
        this.assetName = assetName;
        this.serialNumber = serialNumber;
        this.assetPrice = assetPrice;
        this.storageSpec = storageSpec;
        this.ramSpec = ramSpec;
        this.osSpec = osSpec;
        this.purchaseDate = purchaseDate;
        this.eolDate = eolDate;
        this.warrantyDate = warrantyDate;
        this.warrantyEndDate = warrantEndDate;
        this.assetStatus = assetStatus;
        this.assetCondition = assetCondition;
        this.assetPhotoPath = assetPhotoPath;
        this.assetCategoryName = assetCategoryName;
        this.manufacturerName = manufacturerName;
    }




    //Getters

    public int getAssetID() {
        return assetID;
    }

    public int getAssetCategoryID() {
        return assetCategoryID;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public int getAssetPrice() {
        return assetPrice;
    }
    public String getRamSpec() {
        return ramSpec;
    }

    public String getStorageSpec() {
        return storageSpec;
    }

    public String getOsSpec() {
        return osSpec;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getEolDate() {
        return eolDate;
    }

    public String getWarrantyDate() {
        return warrantyDate;
    }

    public String getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public String getAssetCondition() {
        return assetCondition;
    }

    public String getAssetPhotoPath() {
        return assetPhotoPath;
    }

    public String getAssetCategoryName() {
        return assetCategoryName;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    //Setters

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public void setAssetCategoryID(int assetCategoryID) {
        this.assetCategoryID = assetCategoryID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setAssetPrice(int assetPrice) {
        this.assetPrice = assetPrice;
    }
    public void setStorageSpec(String storageSpec) {
        this.storageSpec = storageSpec;
    }
    public void setRamSpec(String ramSpec) {
        this.ramSpec = ramSpec;
    }

    public void setOsSpec(String osSpec) {
        this.osSpec = osSpec;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setEolDate(String eolDate) {
        this.eolDate = eolDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }

    public void setWarrantyEndDate(String warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public void setAssetCondition(String assetCondition) {
        this.assetCondition = assetCondition;
    }

    public void setAssetPhotoPath(String assetPhotoPath) {
        this.assetPhotoPath = assetPhotoPath;
    }

    public void setAssetCategoryName(String assetCategoryName) {
        this.assetCategoryName = assetCategoryName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }


}
