package com.example.fyp_application.Controllers.Admin.ProcurementManagementControllers;

import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RaiseProcurementRequestController implements Initializable {

    @FXML
    private TableColumn<ProcurementCatalogueModel, Void> actions_col;

    @FXML
    private Button addToBasket_btn;

    @FXML
    private TableColumn<?, ?> assetName_col;

    @FXML
    private TableColumn<ProcurementCatalogueModel, Integer> basketID_col;

    @FXML
    private TableColumn<?, ?> basketModelName_col;

    @FXML
    private TableColumn<?, ?> basketQty_col;

    @FXML
    private TableView<ProcurementCatalogueModel> basketTable;

    @FXML
    private TableColumn<ProcurementCatalogueModel, Double> basketTotal_col;

    @FXML
    private TableView<ProcurementCatalogueModel> catalogueTable;

    @FXML
    private TableColumn<?, ?> category_col;

    @FXML
    private Button closeMenu_btn;

    @FXML
    private TextArea comments_TA;

    @FXML
    private TextField department_TF;

    @FXML
    private TextField email_TF;

    @FXML
    private Button exitApp_btn;

    @FXML
    private TextField firstName_TF;

    @FXML
    private AnchorPane headerAP;

    @FXML
    private TextField lastName_TF;

    @FXML
    private Label lastUpdateTime_lbl;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private AnchorPane mainContentAnchorPane;

    @FXML
    private TableColumn<?, ?> manufacturer_col;

    @FXML
    private TextField phone_TF;

    @FXML
    private TableColumn<ProcurementCatalogueModel,ImageView> photo_col;

    @FXML
    private TextField quantity_TF;

    @FXML
    private TableColumn<?, ?> ramSpec_col;

    @FXML
    private Button refreshHeader_btn;

    @FXML
    private Button saveProcurementRequest_btn;

    @FXML
    private TableColumn<?, ?> storageSpec_col;

    @FXML
    private TableColumn<?, ?> unitPrice_col;

    @FXML
    private TextField username_TF;

    @FXML
    private ComboBox<AssetManufacturerModel> manufacturer_CB;

    @FXML
    private TextField searchBasket_TF;
    
    @FXML
    private Label username_lbl;
    private ObservableList<ProcurementCatalogueModel> procurementList;
    private ObservableList<ProcurementCatalogueModel> basketList = FXCollections.observableArrayList(); // The ObservableList for the basket

    private String REQUEST_STATUS = "Awaiting Approval";



    @FXML
    private void loadProcurementRequestTable(){

    }

    @FXML
    private void loadCatalogueTable(){

        String filter = "Expired";
        procurementList = ProcurementCatalogueDAO.getFilteredSupplierContractCatalogue(filter);

        photo_col.setCellValueFactory(cellData -> {
            ProcurementCatalogueModel asset = cellData.getValue();
            String photoPath = asset.getAssetPicture();
            ImageView imageView = new ImageView();
            imageView.setFitHeight(150); // Set the desired height
            imageView.setFitWidth(150); // Set the desired width

            if (photoPath != null && !photoPath.isEmpty()) {
                try {
                    // Load the image from the photo path -Relative Folder:  CataloguePhotos
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(photoPath)));
                    imageView.setImage(image);
                } catch (NullPointerException e) {

                    System.err.println("Image not found: " + photoPath);
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                    imageView.setImage(image);
                }
            } else {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                imageView.setImage(image);
            }
            return new SimpleObjectProperty<>(imageView);
        });

        assetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        manufacturer_col.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("assetCategory"));
        storageSpec_col.setCellValueFactory(new PropertyValueFactory<>("storageSpecs"));
        ramSpec_col.setCellValueFactory(new PropertyValueFactory<>("ramSpecs"));
        unitPrice_col.setCellValueFactory(new PropertyValueFactory<>("assetPrice"));

        catalogueTable.setItems(procurementList);
    }


    @FXML
    private void loadFilteredCatalogueTable(String filter){

        procurementList = ProcurementCatalogueDAO.getFilteredActiveProcurementCatalogue(filter);

        photo_col.setCellValueFactory(cellData -> {
            ProcurementCatalogueModel asset = cellData.getValue();
            String photoPath = asset.getAssetPicture();
            ImageView imageView = new ImageView();
            imageView.setFitHeight(150); // Set the desired height
            imageView.setFitWidth(150); // Set the desired width

            if (photoPath != null && !photoPath.isEmpty()) {
                try {
                    // Load the image from the photo path -Relative Folder:  CataloguePhotos
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(photoPath)));
                    imageView.setImage(image);
                } catch (NullPointerException e) {

                    System.err.println("Image not found: " + photoPath);
                    Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                    imageView.setImage(image);
                }
            } else {
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                imageView.setImage(image);
            }
            return new SimpleObjectProperty<>(imageView);
        });

        assetName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        manufacturer_col.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("assetCategory"));
        storageSpec_col.setCellValueFactory(new PropertyValueFactory<>("storageSpecs"));
        ramSpec_col.setCellValueFactory(new PropertyValueFactory<>("ramSpecs"));
        unitPrice_col.setCellValueFactory(new PropertyValueFactory<>("assetPrice"));

        catalogueTable.setItems(procurementList);
    }

    @FXML
    private void addToBasket(){
        // Get the selected item from the catalogueTable
        ProcurementCatalogueModel selectedItem = catalogueTable.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Get the quantity from the TextField
            int quantity = Integer.parseInt(quantity_TF.getText());

            // Create a new object with the catalogID, assetName, assetPrice, and quantity
            ProcurementCatalogueModel basketItem = new ProcurementCatalogueModel(selectedItem.getCatalogID(), selectedItem.getAssetName(), selectedItem.getAssetPrice(), quantity);

            // Add the new object to the basketTable
            basketList.add(basketItem);
        } else {
            // Handle the case where no item is selected
            AlertNotificationUtils.showErrorMessageAlert("No item selected", "Please select an item to add to the basket.");
        }
    }

    @FXML
    private void closeWindow(){
        SharedButtonUtils.closeMenu(closeMenu_btn);
    }

    @FXML
    private void setupRequesterDetails() {

        try {
            UserModel userModel = UserDAO.loadCurrentLoggedUser(CurrentLoggedUserHandler.getCurrentLoggedAdminID());
            if (userModel != null) {
                // Set the user's details
                username_TF.setText(userModel.getUsername());
                firstName_TF.setText(userModel.getFirstName());
                lastName_TF.setText(userModel.getLastName());
                email_TF.setText(userModel.getEmail());
                phone_TF.setText(userModel.getPhone());
                department_TF.setText(userModel.getDeptName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void setupManufacturerFilter(){
        // Set the items of the catalogueTable
        List<AssetManufacturerModel> assetManufacturerList = AssetManufacturerDAO.getAllAssetManufacturers();
        assetManufacturerList.add(0, new AssetManufacturerModel(0, "All"));
        manufacturer_CB.getItems().addAll(assetManufacturerList);

        manufacturer_CB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getManufacturerName().contains("All")) {
                loadCatalogueTable();
            } else {
                loadFilteredCatalogueTable(newValue.getManufacturerName());

            }
        });
    }


    @FXML
    private void startRaiseProcurementThread() {
        if (isFormValid()) {
            Task<Void> procurementTask = new Task<Void>() {
                @Override
                public Void call() {
                    try {
                        System.out.println("Running the insertion of procurement thread");
                        insertProcurementRecord();
                        System.out.println("inserted successfully");
                    } catch (Exception e) {
                        System.err.println("Error in procurementTask: " + e.getMessage());
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            new Thread(procurementTask).start();
        } else {
            AlertNotificationUtils.showErrorMessageAlert("Invalid Entry", "Add items on the basket!");
        }
    }


    @FXML
    private void insertProcurementRecord() {
        int procurementID = ProcurementRequestDAO.insertProcurementRequest(
                CurrentLoggedUserHandler.getCurrentLoggedAdminID(),
                REQUEST_STATUS,
                DateTimeUtils.getYearMonthDayFormat(),
                comments_TA.getText());

        if (procurementID > 0) {
            insertBasketRecord(procurementID);
            Platform.runLater(() -> {
                AlertNotificationUtils.showInformationMessageAlert("Success", "Procurement request raised successfully.");
                closeWindow();
            });
        } else {
            Platform.runLater(() -> AlertNotificationUtils.showErrorMessageAlert("Error", "An error occurred while inserting the procurement record."));
        }
    }

    @FXML
    private void insertBasketRecord(int procurementID){
        // Insert the basket record into the database
        for (ProcurementCatalogueModel item : basketList) {
            ProcurementBasketDAO.insertBasketDetails(
                    procurementID,
                    item.getCatalogID(),
                    item.getQuantity(),
                    (int) (item.getQuantity() * item.getAssetPrice()));
        }
    }


    @FXML
    private boolean isFormValid(){
        //return false if basket is empty
        return !basketList.isEmpty();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCatalogueTable();
        quantity_TF.setText("1");
        // Set up the TableColumn's of the basketTable
        setupRequesterDetails();

        setupManufacturerFilter();




        //Setup searchbar
        TableListenerUtils.searchTableDetails(searchBasket_TF, catalogueTable, procurementList, (asset, search) ->
                asset.getAssetName().toLowerCase().contains(search.toLowerCase()) ||
                        String.valueOf(asset.getManufacturerName()).contains(search));

        basketID_col.setCellValueFactory(new PropertyValueFactory<>("catalogID"));
        basketModelName_col.setCellValueFactory(new PropertyValueFactory<>("assetName"));
        basketQty_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        basketTotal_col.setCellValueFactory(param -> {
            ProcurementCatalogueModel item = param.getValue();

           // Calculate the total price
            double totalPrice = item.getAssetPrice() * item.getQuantity();

            // Format the total price to 2 decimal places
            String formattedTotalPrice = String.format("%.2f", totalPrice);

            // Convert the formatted total price back to a double
            double totalPriceWithTwoDecimals = Double.parseDouble(formattedTotalPrice);

            // Return the total price as an ObservableValue
            return new SimpleObjectProperty<>(totalPriceWithTwoDecimals);
        });

        Callback<TableColumn<ProcurementCatalogueModel, Void>, TableCell<ProcurementCatalogueModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<ProcurementCatalogueModel, Void> call(final TableColumn<ProcurementCatalogueModel, Void> param) {
                return new TableCell<>() {
                    private final Button remove_Btn = new Button("Remove");

                    {
                        FontIcon icon = new FontIcon("mdi2c-cart-remove");
                        remove_Btn.setGraphic(icon);
                        remove_Btn.setStyle("-fx-background-color: #C97064; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 12px; " +
                                "-fx-font-weight: bold; " +
                                "-fx-padding: 5px 10px; " +
                                "-fx-border-radius: 5px;");

                        remove_Btn.setOnAction((ActionEvent event) -> {
                            ProcurementCatalogueModel data = getTableView().getItems().get(getIndex());
                            basketList.remove(data);
                            System.out.println("Removed: " + data.getAssetName());
                            System.out.println(basketList.size());
                            System.out.println(basketList.isEmpty()? "Basket is empty": "Basket is not empty");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(remove_Btn);
                        }
                    }
                };
            }
        };

        actions_col.setCellFactory(cellFactory);

        // Set the items of the basketTable
        basketTable.setItems(basketList);

        //Quantity TextField listener

        quantity_TF.textProperty().addListener((observable, oldValue, newValue) -> {
            // Check if the new value is a number
            if (!newValue.matches("\\d+")) {
                // Remove all non-numeric characters
                quantity_TF.setText(newValue.replaceAll("\\D", ""));
            }

            try {
                // Parse the quantity to an integer
                int quantity = Integer.parseInt(newValue);
                // Check if the quantity is less than 1
                if (newValue.isEmpty() || quantity < 1) {
                    quantity_TF.setText("1");
                } else if (quantity > 99) {
                    // Check if the quantity is greater than 99
                    quantity_TF.setText("99");
                }
            } catch (NumberFormatException e) {
                // Handle the case where the quantity is not a number assuming the check above failed
                quantity_TF.setText("1");
            }
        });


    }
}
