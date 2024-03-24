

package com.example.fyp_application.Model;

public class UserModel {

    private int userID;
    private int userRoleID;
    private int deptID;

    //Foreign Keys
    private String roleName;
    private String deptName;

    private String firstName;
    private String lastName;
    private String gender;
    private String dob;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String accountStatus;
    private String photo;
    private String createdAt;
    private String expiresAt;

    private String lastLogin;
    private String lastUpdated;

    private String userFullName;


    //for admin table view
    public UserModel(int userID,
                     int userRoleID,
                     int deptID,
                     String firstName,
                     String lastName,
                     String gender,
                     String dob,
                     String email,
                     String photo,
                     String username,
                     String phone,
                     String accountStatus,
                     String createdAt,
                     String expiresAt,
                     String lastLogin,
                     String RoleName,
                     String DeptName) {

        this.userID = userID;
        this.userRoleID = userRoleID;
        this.deptID = deptID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.photo = photo;
        this.username = username;
        this.phone = phone;
        this.accountStatus = accountStatus;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.lastLogin = lastLogin;
        this.roleName = RoleName;
        this.deptName = DeptName;
    }

    //For Loading and Editing User Profile on client's view
    public UserModel(Integer userID,String firstName, String lastName,
    String email, String gender, String photo, String phone,
    String dob, String username, String createdAt,
    String accountStatus, String deptName, String lastUpdated){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.photo = photo;
        this.phone = phone;
        this.dob = dob;
        this.username = username;
        this.createdAt = createdAt;
        this.accountStatus = accountStatus;
        this.deptName = deptName;
        this.lastUpdated = lastUpdated;

    }


    public UserModel (Integer userID, String userFullName, String roleName, String photo){
        this.userID = userID;
        this.userFullName = userFullName;
        this.roleName = roleName;
        this.photo = photo;
    }


    public UserModel(Integer userID, String userFullName, String photoPath){
        this.userID = userID;
        this.userFullName = userFullName;
        this.photo = photoPath;
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

    public String getDOB() {
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

    public String getPhoto() {
        return photo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }


    public String getUserFullName() {
        return userFullName;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setPhoto(String Photo) {
        this.photo = Photo;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setFullName(String userFullName) {
        this.userFullName =userFullName;
    }

}

