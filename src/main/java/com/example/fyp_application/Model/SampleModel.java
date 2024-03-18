package com.example.fyp_application.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Blob;

public class SampleModel {
    private final IntegerProperty sampleID;
    private final StringProperty photoPath;

    public SampleModel(int sampleID, String photoPath) {
        this.sampleID = new SimpleIntegerProperty(sampleID);
        this.photoPath = new SimpleStringProperty(photoPath);
    }

    // Getters
    public int getSampleID() { return sampleID.get(); }
    public String getPhotoPath() { return photoPath.get(); }

    // Property getters
    public IntegerProperty sampleIDProperty() { return sampleID; }
    public StringProperty photoPathProperty() { return photoPath; }
}


