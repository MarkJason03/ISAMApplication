package com.example.fyp_application.Utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AttachmentHandler {


    private AttachmentHandler(){

    }
    public static void addAttachments(ObservableList<String> filePaths, ListView<String> attachmentList) {


        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Select files to attach","*.*"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null); // Let users select multiple files

        if(selectedFiles != null && !selectedFiles.isEmpty()){
            for(File file: selectedFiles){
                filePaths.add(file.getAbsolutePath());
            }
            attachmentList.setItems(filePaths);
        } else {
            AlertNotificationHandler.showInformationMessageAlert("Action Aborted", "No Files Selected");
        }
    }


    public static void deleteAttachment(ListView<String> attachmentList){

        String selectedFile = attachmentList.getSelectionModel().getSelectedItem();
        if (selectedFile != null){
            attachmentList.getItems().remove(selectedFile);
        }
    }


    public static void openAttachment(String filePath){
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(filePath);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions (file not found, no application associated with the file type, etc.)
            }
        }
    }
}
