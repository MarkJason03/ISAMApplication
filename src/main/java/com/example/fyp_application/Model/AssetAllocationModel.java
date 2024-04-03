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


    private String overdueStatus;


    private String overdueDays;
    private String assetConditionBefore;

    private String assetConditionAfter;


    private String additionalComments;


    private String photoPath;

    //Additional fields from FK

    //Buildings
    private String buildingName;
    private String officeName;

    //User information

    private String firstName;
    private String lastName;
    private String department;
    private String username;
    private String email;
    private String phone;


    //Asset Information
    private String assetName;
    private String serialNumber;

    private String manufacturer;
    private String category;

    private String storageSpec;

    private String ramSpec;

    private String assetCondition;
    private String assetStatus;

    private String fullName;

    //Default Constructor

    public AssetAllocationModel(
            int allocationID,
            int assetID,
            String allocationStatus,
            String overdueStatus,
            String loanType,
            String startDate,
            String dueDate,
            String endDate,
            String additionalComments,
            String overdueDays,
            String assetName,
            String serialNumber,
            String manufacturer,
            String category,
            String storageSpec,
            String ramSpec,
            String assetCondition,
            String assetStatus,
            String firstName,
            String lastName,
            String department,
            String username,
            String email,
            String phone,
            String buildingName,
            String officeName
    )
    {

        this.allocationID = allocationID;
        this.assetID = assetID;
        this.allocationStatus = allocationStatus;
        this.overdueStatus = overdueStatus;
        this.loanType = loanType;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.endDate = endDate;
        this.additionalComments = additionalComments;
        this.overdueDays = overdueDays;
        this.assetName = assetName;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.category = category;
        this.storageSpec = storageSpec;
        this.ramSpec = ramSpec;
        this.assetCondition = assetCondition;
        this.assetStatus = assetStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.buildingName = buildingName;
        this.officeName = officeName;

    }



    public AssetAllocationModel(
            int allocationID,
            int assetID,
            String allocationStatus,
            String overdueStatus,
            String overdueDays,
            String loanType,
            String startDate,
            String dueDate,
            String endDate,
            String additionalComments,
            String assetName,
            String serialNumber,
            String manufacturer,
            String category,
            String storageSpec,
            String photoPath,
            String ramSpec,
            String assetCondition,
            String assetStatus,
            String firstName,
            String lastName,
            String department,
            String username,
            String email,
            String phone,
            String buildingName,
            String officeName
    )
    {

        this.allocationID = allocationID;
        this.assetID = assetID;
        this.allocationStatus = allocationStatus;
        this.overdueStatus = overdueStatus;
        this.overdueDays = overdueDays;
        this.loanType = loanType;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.endDate = endDate;
        this.additionalComments = additionalComments;
        this.assetName = assetName;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.category = category;
        this.storageSpec = storageSpec;
        this.photoPath = photoPath;
        this.ramSpec = ramSpec;
        this.assetCondition = assetCondition;
        this.assetStatus = assetStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.buildingName = buildingName;
        this.officeName = officeName;

    }






    //Constructor
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

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStorageSpec() {
        return storageSpec;
    }

    public void setStorageSpec(String storageSpec) {
        this.storageSpec = storageSpec;
    }

    public String getRamSpec() {
        return ramSpec;
    }

    public void setRamSpec(String ramSpec) {
        this.ramSpec = ramSpec;
    }

    public String getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(String assetCondition) {
        this.assetCondition = assetCondition;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }


    public String getOverdueStatus() {
        return overdueStatus;
    }

    public void setOverdueStatus(String overdueStatus) {
        this.overdueStatus = overdueStatus;
    }


    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }



    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
