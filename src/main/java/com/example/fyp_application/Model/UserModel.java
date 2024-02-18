package com.example.fyp_application.Model;

import java.sql.Blob;

public class UserModel {

    private int userID;
    private int userRoleID;
    private int deptID;

    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String accountStatus;
    private String profilePicture;
    private String createdAt;
    private String expiresAt;



    public UserModel(int userID,
                     int userRoleID,
                     int deptID,
                     String firstName,
                     String lastName,
                     String gender,
                     String dob,
                     String email,
                     String username,
                     String password,
                     String phone,
                     String accountStatus,
                     String profilePicture,
                     String createdAt,
                     String expiresAt) {
        this.userID = userID;
        this.userRoleID = userRoleID;
        this.deptID = deptID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.accountStatus = accountStatus;
        this.profilePicture = profilePicture;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    //Getter Methods
    public int getUserID() {
        return userID;
    }

    public int getUserRoleID() {
        return userRoleID;
    }
    public int getDeptID() {
        return deptID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getGender() {
        return gender;
    }
    public String getDob() {
        return dob;
    }
    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getPhone() {
        return phone;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public String getExpiresAt() {
        return expiresAt;
    }

    //Setter Methods
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }
    public void setDeptID(int deptID) {
        this.deptID = deptID;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(String gender){this.gender = gender;}
    public void setDob(String dob){
        this.dob = dob;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {this.password = password;}
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

}