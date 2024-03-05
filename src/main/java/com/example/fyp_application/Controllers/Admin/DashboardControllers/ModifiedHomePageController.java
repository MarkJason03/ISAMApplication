package com.example.fyp_application.Controllers.Admin.DashboardControllers;

import com.google.api.client.util.Strings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifiedHomePageController implements Initializable {

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Button hello_btn;

    @FXML
    private FlowPane sampleFlowPane;


    @FXML
    private ListView<String> sampleListView;


    private void openFile(String path) {
        if (Desktop.isDesktopSupported()) {
            try {
                File file = new File(path);
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions (file not found, no application associated with the file type, etc.)
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadFlowPaneData();

        ObservableList<String> filePaths = FXCollections.observableArrayList(

                "D:\\FYP_Application\\src\\main\\resources\\AttachmentDemo\\SampleMacbook.jpg",
                "D:\\FYP_Application\\src\\main\\resources\\AttachmentDemo\\sampleInvoice.pdf",
                "D:\\FYP_Application\\src\\main\\resources\\AttachmentDemo\\sampleInvoice.docx"


        );


        sampleListView.setItems(filePaths);
        sampleListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = sampleListView.getSelectionModel().getSelectedItem();
                openFile(selectedItem);
                if (!Strings.isNullOrEmpty(selectedItem)) {
                    System.out.println("Selected Item: " + selectedItem);
                }
            }
        });
    }
}
