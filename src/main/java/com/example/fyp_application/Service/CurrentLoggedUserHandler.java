package com.example.fyp_application.Service;

public class CurrentLoggedUserHandler {
    private static Integer userID;
    private static String userName;
    private static String imagePath;




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


}
