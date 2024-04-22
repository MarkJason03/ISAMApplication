package com.example.fyp_application.Service;

public class CurrentLoggedUserHandler {
    private static Integer userID;
    private static String userFullName;
    private static String imagePath;


    private static Integer adminID;
    private static String adminFullName;
    private static String adminImagePath;

    private static String roleName;

    // Tracks the current logged in user
    public static void setCurrentUser(Integer userID, String userFullName, String imagePath, String roleName) {
        CurrentLoggedUserHandler.userID = userID;
        CurrentLoggedUserHandler.userFullName = userFullName;
        CurrentLoggedUserHandler.imagePath = imagePath;
        CurrentLoggedUserHandler.roleName = roleName;
    }

    public static void setCurrentAdmin(Integer adminID, String adminName, String adminImagePath, String roleName) {
        CurrentLoggedUserHandler.adminID = adminID;
        CurrentLoggedUserHandler.adminFullName = adminName;
        CurrentLoggedUserHandler.adminImagePath = adminImagePath;
        CurrentLoggedUserHandler.roleName = roleName;
    }

    public static void setNewAdminPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.adminImagePath = newPhotoPath;
    }

    public static void setNewAdminName(String newFullName) {
        CurrentLoggedUserHandler.adminFullName = newFullName;
    }

    public static Integer getCurrentLoggedAdminID() {
        return adminID;
    }

    public static String getCurrentLoggedAdminFullName() {
        return adminFullName;
    }

    public static String getCurrentLoggedAdminImagePath() {
        return adminImagePath;
    }

    public static String getCurrentUserRoleName() {
        return roleName;
    }

    // Getters and Setters for the current logged in user - in case the user changes their profile picture or name
    public static void setNewPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.imagePath = newPhotoPath;
    }

    public static void setUserFullName(String userFullName) {
        CurrentLoggedUserHandler.userFullName = userFullName;
    }

    public static void setUserID(Integer userID) {
        CurrentLoggedUserHandler.userID = userID;
    }
    public static Integer getCurrentLoggedUserID() {
        return userID;
    }

    public static String getCurrentLoggedUserFullName() {
        return userFullName;
    }

    public static String getCurrentLoggedUserImagePath() {
        return imagePath;
    }

}
