package com.example.fyp_application.Controllers.Admin.DashboardControllers;

import com.example.fyp_application.Model.SampleDAO;
import com.example.fyp_application.Model.SampleModel;
import com.example.fyp_application.Utils.AttachmentHandler;
import com.google.api.client.util.Strings;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;

import javafx.scene.image.Image;


public class ModifiedHomePageController implements Initializable {

    @FXML
    private TableColumn<SampleModel, ImageView> image_col;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Button hello_btn;

    @FXML
    private TableColumn<SampleModel, Integer> id_col;

    @FXML
    private TableColumn<SampleModel, String> path_col;

 /*   @FXML
    private TableColumn<SampleModel, ImageView> image_col;*/
    @FXML
    private  ListView<String> sampleListView;

    @FXML
    private TableView<SampleModel> sampleTable;



    private  ObservableList<String> filePaths = FXCollections.observableArrayList(

            "/AttachmentDemo/SampleMacbook.jpg",
            "/AttachmentDemo/sampleInvoice.pdf",
            "/AttachmentDemo/sampleInvoice.docx"


    );

    private void openFile(String relativePath) {
      AttachmentHandler.openAttachment(relativePath);
    }
/*
    public List<ImageModel> prepareImageData() {
        String[] filePaths = {
                "/CataloguePhotos/appleMagicKeyboard.PNG",
                "/CataloguePhotos/applePen1stGen.PNG",
                "/CataloguePhotos/applePen2ndGen.PNG",
                "/CataloguePhotos/ipad.PNG",
                "/CataloguePhotos/ipadPro.PNG",
                "/CataloguePhotos/logiverticalMouse.PNG"
        };

        List<ImageModel> imageDataList = new ArrayList<>();

        for (String filePath : filePaths) {
            imageDataList.add(new ImageModel(filePath));
        }

        return imageDataList;
    }


    public void insertAllImages() throws IOException {
        List<ImageModel> images = prepareImageData();
        int id = 1;  // Initialize ID, assuming it's managed here. Adjust based on your ID handling.

        for (ImageModel imageData : images) {
            SampleDAO.insertValues(id++, imageData.getImagePath());
        }
    }
*/



    @FXML
    private void openAttachment(){
        AttachmentHandler.addAttachments(filePaths, sampleListView);
    }

    private void loadData(){
        id_col.setCellValueFactory(new PropertyValueFactory<>("sampleID"));
        path_col.setCellValueFactory(new PropertyValueFactory<>("photoPath"));

        image_col.setCellValueFactory(cellData -> {
            String photoPath = cellData.getValue().getPhotoPath();
            ImageView imageView = null;
            if (photoPath != null && !photoPath.isEmpty()) {
                Image image = new Image(getClass().getResourceAsStream(photoPath));
                imageView = new ImageView(image);
                imageView.setFitHeight(150);
                imageView.setFitWidth(150);
            }
            return new SimpleObjectProperty<>(imageView);
        });

        ObservableList<SampleModel> sampleData = SampleDAO.getAllSamples();
        sampleTable.setItems(sampleData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //loadFlowPaneData();


        //insertAllImages();
        Platform.runLater(this::loadData);





        sampleTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                String selectedItem = sampleTable.getSelectionModel().getSelectedItem().getPhotoPath();
                openFile(selectedItem);
                if (!Strings.isNullOrEmpty(selectedItem)) {
                    System.out.println("Selected Item: " + selectedItem);
                }
            }
        });


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
