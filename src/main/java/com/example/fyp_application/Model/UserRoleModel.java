package com.example.fyp_application.Model;

public class UserRoleModel {

    private int userRoleID;
    private String roleName;

    public UserRoleModel(int userRoleID, String roleName) {
        this.userRoleID = userRoleID;
        this.roleName = roleName;
    }

    public int getUserRoleID() {
        return userRoleID;
    }

    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }

}
