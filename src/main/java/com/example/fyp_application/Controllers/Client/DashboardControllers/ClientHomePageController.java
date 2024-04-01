package com.example.fyp_application.Controllers.Client.DashboardControllers;

import com.example.fyp_application.Controllers.Admin.AssetManagementControllers.ReturnAllocatedAssetController;
import com.example.fyp_application.Controllers.Client.ClientRequestControllers.ClientRequestDashboardController;
import com.example.fyp_application.Model.*;
import com.example.fyp_application.Service.CurrentLoggedUserHandler;
import com.example.fyp_application.Utils.AlertNotificationUtils;
import com.example.fyp_application.Utils.ConfigPropertiesUtils;
import com.example.fyp_application.Utils.UserDetailsUtils;
import com.example.fyp_application.Views.ViewConstants;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ClientHomePageController implements Initializable {


    @FXML
    private FlowPane assetFlowPane;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Label deparment_lbl;

    @FXML
    private Label email_lbl;

    @FXML
    private Label expiresDate_lbl;

    @FXML
    private Label fullName_lbl;

    @FXML
    private Circle imageHolder_shape;

    @FXML
    private Circle imageHolder_shape1;

    @FXML
    private Label phone_lbl;

    @FXML
    private TableView<TicketModel> requestTable;

    @FXML
    private TableColumn<TicketModel, FontIcon> sample;

    @FXML
    private TableColumn<?, ?> ticketID_col;

    @FXML
    private TableColumn<?, ?> ticketStatus_col;

    @FXML
    private TableColumn<?, ?> ticketTitle_col;

    @FXML
    private Label totalAssetCostHolder_lbl;

    @FXML
    private Label totalAssetsHolder_lbl;

    @FXML
    private Label totalInProgressTicket_lbl;

    public void loadAssetListFlowPane() {
        ObservableList<AssetAllocationModel> assetListByUsers;

        try {
            assetListByUsers = AssetAllocationDAO.getAllocationDetailsByUser(CurrentLoggedUserHandler.getCurrentLoggedUserID());
            for (AssetAllocationModel assetAllocationModel : assetListByUsers) {
                VBox userBox = createAssetHistoryVBox(assetAllocationModel);
                assetFlowPane.getChildren().add(userBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createAssetHistoryVBox(AssetAllocationModel assetAllocationModel) {
        if (assetAllocationModel == null) {
            throw new IllegalArgumentException("AssetAllocationModel cannot be null");
        }

        VBox allocationHistoryVBox = new VBox(10); // VBox with spacing of 10 pixels
        allocationHistoryVBox.setAlignment(Pos.CENTER);
        allocationHistoryVBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;");

        Rectangle square = createAssetVBoxPhoto(assetAllocationModel);
        Label name = new Label(assetAllocationModel.getAssetName());
        Label startDate = new Label("Date issued: " + assetAllocationModel.getStartDate());
        Label dueDate = new Label("Return Date: " + assetAllocationModel.getDueDate());
        Label overdueDays = new Label();
        Button profileButton = viewAllocationHistoryForm(assetAllocationModel);

        int overdueDaysValue = parseOverdueDays(assetAllocationModel.getOverdueDays());

        if (overdueDaysValue <= 0) {
            overdueDays.setText("No of Overdue Days: " + overdueDaysValue);
            overdueDays.setStyle("-fx-text-fill: green;");
        } else {
            overdueDays.setText("No of Overdue Days: " + overdueDaysValue);
            overdueDays.setStyle("-fx-text-fill: red;");
        }
        profileButton.getStyleClass().add("refreshButton");
        allocationHistoryVBox.getChildren().addAll(square, name, startDate, dueDate, overdueDays, profileButton);

        return allocationHistoryVBox;
    }

private int parseOverdueDays(String overdueDaysString) {
    try {
        return Integer.parseInt(overdueDaysString);
    } catch (NumberFormatException e) {
        return 0; // default value
    }
}

    private Rectangle createAssetVBoxPhoto(AssetAllocationModel assetAllocationModel) {
        Rectangle squareAssetPhoto = new Rectangle(100, 100);

        if (assetAllocationModel.getPhotoPath() != null && !assetAllocationModel.getPhotoPath().isEmpty()) {
            try {
                // Load the image from the photo path -Relative Folder:  CataloguePhotos
                Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(assetAllocationModel.getPhotoPath())));
                squareAssetPhoto.setFill(new ImagePattern(curPhoto));
            } catch (NullPointerException e) {
                System.err.println("No image assigned, Using Placeholder" + ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"));
                Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
                squareAssetPhoto.setFill(new ImagePattern(curPhoto));
            }
        } else {
            System.err.println("No image assigned, Using Placeholder" + ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"));
            Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ConfigPropertiesUtils.getPropertyValue("DEFAULT_ASSET_PHOTO"))));
            squareAssetPhoto.setFill(new ImagePattern(curPhoto));
        }

        return squareAssetPhoto;
    }

    private Button viewAllocationHistoryForm(AssetAllocationModel assetAllocationModel) {
        Button allocHistory_btn = new Button("View More");

        // Set the default background color
        allocHistory_btn.setStyle("-fx-background-color: transparent;");

        // Set the hover background color
        allocHistory_btn.setOnMouseEntered(e -> allocHistory_btn.setStyle("-fx-background-color: #7CEA9C;"));

        // Set the pressed background color
        allocHistory_btn.setOnMousePressed(e -> allocHistory_btn.setStyle("-fx-background-color: #7CEA9Cc8;"));

        allocHistory_btn.setOnAction(e -> {
            // Get the selected item from the list
            AssetAllocationModel selectedItem = assetAllocationModel;
            // Open the allocation history of the selected asset by user
            openAllocationForm(selectedItem);
        });

        // Reset the background color when the mouse is released or exited
        allocHistory_btn.setOnMouseReleased(e -> allocHistory_btn.setStyle("-fx-background-color: #7CEA9C;"));
        allocHistory_btn.setOnMouseExited(e -> allocHistory_btn.setStyle("-fx-background-color: transparent;"));

        return allocHistory_btn;
    }
    private void openAllocationForm(AssetAllocationModel assetList) {
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) assetFlowPane.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        // Get the selected item from the passed list

        if (assetList == null) {
            AlertNotificationUtils.showErrorMessageAlert("Error Loading Allocation Information", "Please select an allocation history to view");
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect
        } else {
            try {
                //Load the supplier menu
                //modal pop-up dialogue box
                FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_VIEW_ALLOCATION_POP_UP));
                Parent root = modalViewLoader.load();

                ReturnAllocatedAssetController returnAllocatedAssetController = modalViewLoader.getController();
                returnAllocatedAssetController.loadAllocationInformation(assetList, true);

                // New window setup as modal
                Stage viewAllocationForm = new Stage();
                viewAllocationForm.initOwner(currentDashboardStage);
                viewAllocationForm.initModality(Modality.WINDOW_MODAL);
                viewAllocationForm.initStyle(StageStyle.TRANSPARENT);

                Scene scene = new Scene(root);
                viewAllocationForm.setScene(scene);

                viewAllocationForm.showAndWait(); // Blocks interaction with the main stage

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close
            }
        }
    }

    @FXML
    private void setupDashboardCounters(){
        totalInProgressTicket_lbl.setText("Requests: " + TicketDAO.countOngoingTicketByUserID(CurrentLoggedUserHandler.getCurrentLoggedUserID()));
        totalAssetsHolder_lbl.setText("Assets: " + AssetAllocationDAO.countAssetsByUserID(CurrentLoggedUserHandler.getCurrentLoggedUserID()));
        totalAssetCostHolder_lbl.setText("$"+ AssetAllocationDAO.calculateTotalAssetCostByUserID(CurrentLoggedUserHandler.getCurrentLoggedUserID()));
    }
    @FXML
    private void loadRequests(){
        TicketDAO ticketDAO = new TicketDAO();

        ObservableList<TicketModel> requestList = ticketDAO.getTicketsByUserID(CurrentLoggedUserHandler.getCurrentLoggedUserID());

        sample.setCellValueFactory(cellData -> {
            FontIcon icon;
            if (cellData.getValue().getTicketStatus().equals("Created")) {
                icon = new FontIcon("mdi2b-book-alert");
            } else if (cellData.getValue().getTicketStatus().equals("Awaiting Response") || cellData.getValue().getTicketStatus().equals("In Progress")) {
                icon = new FontIcon("mdi2b-book-clock");
            } else {
                icon = new FontIcon("mdi2b-book-check");
            }
            icon.setIconSize(40);
            return new SimpleObjectProperty<>(icon);
        });

        ticketID_col.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        ticketTitle_col.setCellValueFactory(new PropertyValueFactory<>("ticketTitle"));
        ticketStatus_col.setCellValueFactory(new PropertyValueFactory<>("ticketStatus"));

        requestTable.setItems(requestList);
    }

    @FXML
    private void setupIDCard() throws SQLException {

        try {
            UserModel userModel = UserDAO.loadCurrentLoggedUser(CurrentLoggedUserHandler.getCurrentLoggedUserID());

            UserDetailsUtils.setupIDCard(userModel, fullName_lbl, deparment_lbl, expiresDate_lbl, email_lbl, phone_lbl, imageHolder_shape);
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRequests();
        loadAssetListFlowPane();

        try {
            setupIDCard();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupDashboardCounters();

        requestTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ClientRequestDashboardController clientRequestDashboardController = new ClientRequestDashboardController();
                Stage modalStage = clientRequestDashboardController.viewRequestDetails(requestTable, true);
                if (modalStage != null) {
                    modalStage.setOnHidden(e -> loadRequests());
                }
            }
        });

    }


}