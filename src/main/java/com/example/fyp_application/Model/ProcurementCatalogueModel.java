package com.example.fyp_application.Model;

public class ProcurementCatalogueModel {



    private int catalogID;

    private int supplierID;

    private int manufacturerID;

    private int assetCategoryID;

    private String assetName;

    private String storageSpecs;

    private String ramSpecs;

    private double assetPrice;

    private String assetPicture;

    private String assetCategory;

    private String manufacturerName;

    private String supplierName;

    private int quantity;


    public ProcurementCatalogueModel(
            int catalogID,
            String assetName,
            String storageSpecs,
            String ramSpecs,
            String manufacturerName,
            String assetCategory,
            int assetPrice,
            String assetPicture
    ){
        this.catalogID = catalogID;
        this.assetName = assetName;
        this.storageSpecs = storageSpecs;
        this.ramSpecs = ramSpecs;
        this.manufacturerName = manufacturerName;
        this.assetCategory = assetCategory;
        this.assetPrice = assetPrice;
        this.assetPicture = assetPicture;
    }

    public ProcurementCatalogueModel(
            int catalogID,
            String assetName,
            double assetPrice,
            int quantity
    ){
        this.catalogID = catalogID;
        this.assetName = assetName;
        this.assetPrice = assetPrice;
        this.quantity = quantity;
    }

    public int getCatalogID() {
        return catalogID;
    }

    public void setCatalogID(int catalogID) {
        this.catalogID = catalogID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getAssetCategoryID() {
        return assetCategoryID;
    }

    public void setAssetCategoryID(int assetCategoryID) {
        this.assetCategoryID = assetCategoryID;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getStorageSpecs() {
        return storageSpecs;
    }

    public void setStorageSpecs(String storageSpecs) {
        this.storageSpecs = storageSpecs;
    }

    public String getRamSpecs() {
        return ramSpecs;
    }

    public void setRamSpecs(String ramSpecs) {
        this.ramSpecs = ramSpecs;
    }

    public double getAssetPrice() {
        return assetPrice;
    }

    public void setAssetPrice(double assetPrice) {
        this.assetPrice = assetPrice;
    }

    public String getAssetPicture() {
        return assetPicture;
    }

    public void setAssetPicture(String assetPicture) {
        this.assetPicture = assetPicture;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
