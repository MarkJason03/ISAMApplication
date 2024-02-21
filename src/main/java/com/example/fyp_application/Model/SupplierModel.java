package com.example.fyp_application.Model;

public class SupplierModel {

    private int supplierID;

    private String supplierName;
    private String supplierAddress;
    private String supplierEmail;
    private String supplierContact;


    public SupplierModel(int supplierID, String supplierName, String supplierAddress, String supplierEmail, String supplierContact) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierAddress = supplierAddress;
        this.supplierEmail = supplierEmail;
        this.supplierContact = supplierContact;
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

}
