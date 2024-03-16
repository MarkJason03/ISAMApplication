package com.example.fyp_application.Utils;

import com.example.fyp_application.Model.MessageHistoryModel;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AttachmentHandler {


    private AttachmentHandler(){

    }

    // Add the attachment
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


    // Delete the attachment
    public static void deleteAttachments(ListView<String> attachmentList){

        String selectedFile = attachmentList.getSelectionModel().getSelectedItem();
        if (selectedFile != null){
            attachmentList.getItems().remove(selectedFile);
        }
    }


    // Open the attachment
    public static void openAttachment(String filePath){
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(filePath);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //

    public static void setAttachmentListState(CheckBox attachmentCheckbox, TitledPane attachmentTitlePane) {
        attachmentCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                attachmentTitlePane.setDisable(false);
            } else {
                attachmentTitlePane.setExpanded(false);
                attachmentTitlePane.setDisable(true);
            }
        });
    }


    public static void setAttachmentListListener(ListView<String> attachmentList) {
        attachmentList.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = attachmentList.getSelectionModel().getSelectedItem();
                openAttachment(selectedItem);
            }
        });
    }

}
