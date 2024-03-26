package com.example.fyp_application.Model;

public class ProcurementRequestDAO {


    private int procurementRequestID;
    private int basketID;

    private  int userID;

    private int procurementManagerID;


    private String procurementRequestStatus;

    private String procurementRequestDate;

    private String procurementCompletionDate;

    private String procurementRequestDescription;


    public int getProcurementRequestID() {
        return procurementRequestID;
    }

    public void setProcurementRequestID(int procurementRequestID) {
        this.procurementRequestID = procurementRequestID;
    }

    public int getBasketID() {
        return basketID;
    }

    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProcurementManagerID() {
        return procurementManagerID;
    }

    public void setProcurementManagerID(int procurementManagerID) {
        this.procurementManagerID = procurementManagerID;
    }

    public String getProcurementRequestStatus() {
        return procurementRequestStatus;
    }

    public void setProcurementRequestStatus(String procurementRequestStatus) {
        this.procurementRequestStatus = procurementRequestStatus;
    }

    public String getProcurementRequestDate() {
        return procurementRequestDate;
    }

    public void setProcurementRequestDate(String procurementRequestDate) {
        this.procurementRequestDate = procurementRequestDate;
    }

    public String getProcurementCompletionDate() {
        return procurementCompletionDate;
    }

    public void setProcurementCompletionDate(String procurementCompletionDate) {
        this.procurementCompletionDate = procurementCompletionDate;
    }

    public String getProcurementRequestDescription() {
        return procurementRequestDescription;
    }

    public void setProcurementRequestDescription(String procurementRequestDescription) {
        this.procurementRequestDescription = procurementRequestDescription;
    }
}
