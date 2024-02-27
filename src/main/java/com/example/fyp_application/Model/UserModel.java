

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



    //for table view
    public UserModel(int userID,
                     String firstName,
                     String lastName,
                     String gender,
                     String email,
                     String username,
                     String phone,
                     String accountStatus,
                     String createdAt,
                     String expiresAt,
                     String RoleName,
                     String DeptName) {

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.accountStatus = accountStatus;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.roleName = RoleName;
        this.deptName = DeptName;
    }


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
                     String Photo,
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
        this.photo = Photo;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }


    //For Loading and Editing User Profile on client's view
    public UserModel(Integer userID,String firstName, String lastName,
    String email, String gender, String photo, String phone,
    String dob, String password, String username, String createdAt,
    String accountStatus, String deptName){
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.photo = photo;
        this.phone = phone;
        this.dob = dob;
        this.password = password;
        this.username = username;
        this.createdAt = createdAt;
        this.accountStatus = accountStatus;
        this.deptName = deptName;
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
}
/*    public class ClientModel extends UserModel{
        private int userID;
        private String firstName;
        private String lastName;
        private String password;
        private String phone;
        private String email;
        private String photo;

    }*//*

}
*/
/*

package com.example.fyp_application.Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserModel {

    // Use SimpleIntegerProperty for observable integer fields
    private SimpleIntegerProperty userID = new SimpleIntegerProperty();
    private SimpleIntegerProperty userRoleID = new SimpleIntegerProperty();
    private SimpleIntegerProperty deptID = new SimpleIntegerProperty();

    // Use SimpleStringProperty for observable string fields
    private SimpleStringProperty roleName = new SimpleStringProperty();
    private SimpleStringProperty deptName = new SimpleStringProperty();
    private SimpleStringProperty firstName = new SimpleStringProperty();
    private SimpleStringProperty lastName = new SimpleStringProperty();
    private SimpleStringProperty gender = new SimpleStringProperty();
    private SimpleStringProperty dob = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty username = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();
    private SimpleStringProperty accountStatus = new SimpleStringProperty();
    private SimpleStringProperty photo = new SimpleStringProperty();
    private SimpleStringProperty createdAt = new SimpleStringProperty();
    private SimpleStringProperty expiresAt = new SimpleStringProperty();

    // Constructor for table view
    public UserModel(int userID, String firstName, String lastName, String gender,
                     String email, String username, String phone, String accountStatus,
                     String createdAt, String expiresAt, String roleName, String deptName) {
        this.userID.set(userID);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.gender.set(gender);
        this.email.set(email);
        this.username.set(username);
        this.phone.set(phone);
        this.accountStatus.set(accountStatus);
        this.createdAt.set(createdAt);
        this.expiresAt.set(expiresAt);
        this.roleName.set(roleName);
        this.deptName.set(deptName);
    }

    // Full constructor
    public UserModel(int userID, int userRoleID, int deptID, String firstName, String lastName,
                     String gender, String dob, String email, String username, String password,
                     String phone, String accountStatus, String photo, String createdAt, String expiresAt) {
        this(userID, firstName, lastName, gender, email, username, phone, accountStatus, createdAt, expiresAt, "", "");
        this.userRoleID.set(userRoleID);
        this.deptID.set(deptID);
        this.dob.set(dob);
        this.password.set(password);
        this.photo.set(photo);
        // RoleName and DeptName need to be set via separate method or directly if required
    }

    // Example getter for a JavaFX property
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    // Example regular getter
    public String getFirstName() {
        return firstName.get();
    }

    // Example setter
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    // Add other getters and setters for JavaFX properties and regular fields...

}*/

