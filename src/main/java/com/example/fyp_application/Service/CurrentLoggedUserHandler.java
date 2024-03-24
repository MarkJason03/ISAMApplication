package com.example.fyp_application.Service;

public class CurrentLoggedUserHandler {
    private static Integer userID;
    private static String userFullName;
    private static String imagePath;


    private static Integer adminID;
    private static String adminFullName;
    private static String adminImagePath;


    public static void setCurrentUser(Integer userID, String userFullName, String imagePath) {
        CurrentLoggedUserHandler.userID = userID;
        CurrentLoggedUserHandler.userFullName = userFullName;
        CurrentLoggedUserHandler.imagePath = imagePath;
    }

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


    public static void setCurrentAdmin(Integer adminID, String adminName, String adminImagePath) {
        CurrentLoggedUserHandler.adminID = adminID;
        CurrentLoggedUserHandler.adminFullName = adminName;
        CurrentLoggedUserHandler.adminImagePath = adminImagePath;
    }

    public static void setNewAdminPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.adminImagePath = newPhotoPath;
    }

    public static void setNewAdminName(String newFullName) {
        CurrentLoggedUserHandler.adminFullName = newFullName;
    }

    public static void setAdminID(Integer adminID) {
        CurrentLoggedUserHandler.adminID = adminID;
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

}
