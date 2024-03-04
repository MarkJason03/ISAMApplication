package com.example.fyp_application.Service;

public class CurrentLoggedUserHandler {
    private static Integer userID;
    private static String userName;
    private static String imagePath;


    private static Integer adminID;
    private static String adminName;
    private static String adminImagePath;


    public static void setCurrentUser(Integer userID, String username, String imagePath) {
        CurrentLoggedUserHandler.userID = userID;
        CurrentLoggedUserHandler.userName = username;
        CurrentLoggedUserHandler.imagePath = imagePath;
    }

    public static void setNewPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.imagePath = newPhotoPath;
    }

    public static void setNewName(String newFirstName) {
        CurrentLoggedUserHandler.userName = newFirstName;
    }

    public static Integer getUserID() {
        return userID;
    }

    public static String getFirstName() {
        return userName;
    }

    public static String getImagePath() {
        return imagePath;
    }


    public static void setCurrentAdmin(Integer adminID, String adminName, String adminImagePath) {
        CurrentLoggedUserHandler.adminID = adminID;
        CurrentLoggedUserHandler.adminName = adminName;
        CurrentLoggedUserHandler.adminImagePath = adminImagePath;
    }

    public static void setNewAdminPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.adminImagePath = newPhotoPath;
    }

    public static void setNewAdminName(String newFirstName) {
        CurrentLoggedUserHandler.adminName = newFirstName;
    }

    public static Integer getAdminID() {
        return adminID;
    }

    public static String getAdminName() {
        return adminName;
    }

    public static String getAdminImagePath() {
        return adminImagePath;
    }

}
