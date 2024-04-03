package com.example.fyp_application.Model;

public class ProcurementRequestModel {

    private int procurementRequestID;
    private int basketID;

    private  int userID;

    private int procurementManagerID;


    private String procurementRequestStatus;

    private String procurementRequestDate;

    private String procurementCompletionDate;


    private String requesterComment;


    private String procurementManagerComment;

    private String procurementApproverName;

    private int totalCost;







    public ProcurementRequestModel(int procurementRequestID,
                                   String procurementRequestStatus,
                                   String procurementManagerComment,
                                   String procurementRequestDate,
                                  String procurementCompletionDate

    ){
        this.procurementRequestID = procurementRequestID;
        this.procurementRequestStatus = procurementRequestStatus;
        this.procurementManagerComment = procurementManagerComment;
        this.procurementRequestDate = procurementRequestDate;
        this.procurementCompletionDate = procurementCompletionDate;
    }


    public ProcurementRequestModel(int userID,
                                   int procurementManagerID,
                                   String procurementApproverName,
                                   String procurementRequestStatus,
                                   String procurementRequestDate,
                                   String procurementCompletionDate,
                                   String requesterComment,
                                   String procurementManagerComment){
        this.userID = userID;
        this.procurementManagerID = procurementManagerID;
        this.procurementApproverName = procurementApproverName;
        this.procurementRequestStatus = procurementRequestStatus;
        this.procurementRequestDate = procurementRequestDate;
        this.procurementCompletionDate = procurementCompletionDate;
        this.requesterComment = requesterComment;
        this.procurementManagerComment = procurementManagerComment;
    }


    public ProcurementRequestModel(
            String procurementRequestDate,
            int totalCost
    ){
        this.procurementRequestDate = procurementRequestDate;
        this.totalCost = totalCost;
    }

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

    public String getRequesterComment() {
        return requesterComment;
    }

    public void setRequesterComment(String requesterComment) {
        this.requesterComment = requesterComment;
    }

    public String getProcurementManagerComment() {
        return procurementManagerComment;
    }

    public void setProcurementManagerComment(String procurementManagerComment) {
        this.procurementManagerComment = procurementManagerComment;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getProcurementApproverName() {
        return procurementApproverName;
    }

    public void setProcurementApproverName(String procurementApproverName) {
        this.procurementApproverName = procurementApproverName;
    }
}
