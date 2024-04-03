package com.example.fyp_application.Model;

public class SupplierModel {

    private int supplierID;
    private String supplierName;
    private String supplierAddress;
    private String supplierEmail;
    private String supplierContact;

    private String supplierContractStatus;

    private String contractStartDate;

    private String contractEndDate;

    //Constructor for supplier table
    public SupplierModel(int supplierID, String supplierName, String supplierAddress, String supplierEmail, String supplierContact, String contractStatus, String contractStartDate, String contractEndDate) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierEmail = supplierEmail;
        this.supplierContact = supplierContact;
        this.supplierContractStatus = contractStatus;
        this.contractStartDate = contractStartDate;
        this.contractEndDate = contractEndDate;

    }

    public SupplierModel(int supplierID,
                         String supplierName){
        this.supplierID = supplierID;
        this.supplierName = supplierName;
    }

    //getter methods



    public int getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public String getSupplierContractStatus() {
        return supplierContractStatus;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    //setter methods

    public  void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public void setSupplierContractStatus(String supplierContractStatus) {
        this.supplierContractStatus = supplierContractStatus;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }


    @Override
    public String toString() {
        return supplierName;
    }

}
