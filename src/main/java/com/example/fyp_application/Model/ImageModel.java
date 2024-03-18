package com.example.fyp_application.Model;

public class ImageModel {
    private int imageId;
    private String imagePath;

    public ImageModel(int imageId, String imagePath) {
        this.imageId = imageId;
        this.imagePath = imagePath;
    }

    // Getters
    public int getImageId() {
        return imageId;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Setters
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
