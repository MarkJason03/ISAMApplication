package com.example.fyp_application.Service;

public class CurrentLoggedUserHandler {
    private static Integer userID;
    private static String userFirstName;
    private static String imagePath;


    private static Integer adminID;
    private static String adminFirstName;
    private static String adminImagePath;


    public static void setCurrentUser(Integer userID, String firstName, String imagePath) {
        CurrentLoggedUserHandler.userID = userID;
        CurrentLoggedUserHandler.userFirstName = firstName;
        CurrentLoggedUserHandler.imagePath = imagePath;
    }

    public static void setNewPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.imagePath = newPhotoPath;
    }

    public static void setNewName(String newFirstName) {
        CurrentLoggedUserHandler.userFirstName = newFirstName;
    }

    public static Integer getCurrentLoggedUserID() {
        return userID;
    }

    public static String getCurrentLoggedUserFirstName() {
        return userFirstName;
    }

    public static String getCurrentLoggedUserImagePath() {
        return imagePath;
    }


    public static void setCurrentAdmin(Integer adminID, String adminName, String adminImagePath) {
        CurrentLoggedUserHandler.adminID = adminID;
        CurrentLoggedUserHandler.adminFirstName = adminName;
        CurrentLoggedUserHandler.adminImagePath = adminImagePath;
    }

    public static void setNewAdminPhoto(String newPhotoPath) {
        CurrentLoggedUserHandler.adminImagePath = newPhotoPath;
    }

    public static void setNewAdminName(String newFirstName) {
        CurrentLoggedUserHandler.adminFirstName = newFirstName;
    }

    public static Integer getCurrentLoggedAdminID() {
        return adminID;
    }

    public static String getCurrentLoggedAdminName() {
        return adminFirstName;
    }

    public static String getCurrentLoggedAdminImagePath() {
        return adminImagePath;
    }

}
