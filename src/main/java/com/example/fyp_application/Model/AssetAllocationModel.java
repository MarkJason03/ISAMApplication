package com.example.fyp_application.Model;

public class AssetAllocationModel {

    private int allocationID;

    private int assetID;

    private int userID;

    private int agentID;

    private int officeID;

    private String loanType;

    private String startDate;

    private String dueDate;

    private String endDate;

    private String allocationStatus;

    private String assetConditionBefore;

    private String assetConditionAfter;


    private String additionalComments;


    //getters and setters
    public int getAllocationID() {
        return allocationID;
    }

    public void setAllocationID(int allocationID) {
        this.allocationID = allocationID;
    }

    public int getAssetID() {
        return assetID;
    }

    public void setAssetID(int assetID) {
        this.assetID = assetID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAgentID() {
        return agentID;
    }

    public void setAgentID(int agentID) {
        this.agentID = agentID;
    }

    public int getOfficeID() {
        return officeID;
    }

    public void setOfficeID(int officeID) {
        this.officeID = officeID;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAllocationStatus() {
        return allocationStatus;
    }

    public void setAllocationStatus(String allocationStatus) {
        this.allocationStatus = allocationStatus;
    }

    public String getAssetConditionBefore() {
        return assetConditionBefore;
    }

    public void setAssetConditionBefore(String assetConditionBefore) {
        this.assetConditionBefore = assetConditionBefore;
    }

    public String getAssetConditionAfter() {
        return assetConditionAfter;
    }

    public void setAssetConditionAfter(String assetConditionAfter) {
        this.assetConditionAfter = assetConditionAfter;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }


}
