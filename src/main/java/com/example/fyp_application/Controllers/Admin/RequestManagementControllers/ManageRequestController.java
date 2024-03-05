package com.example.fyp_application.Controllers.Admin.RequestManagementControllers;

import com.example.fyp_application.Controllers.Admin.SupplierManagementControllers.ModifiedEditSupplierController;
import com.example.fyp_application.Model.SupplierModel;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ManageRequestController {

    @FXML
    private AnchorPane contentAP;

    @FXML
    private Label dateTimeHolder;

    @FXML
    private Button deleteUser_btn;

    @FXML
    private Label deptHolder_lbl;

    @FXML
    private Button editProfile_btn1;

    @FXML
    private TextField email_TF;

    @FXML
    private TextField firstName_TF;

    @FXML
    private Label fullNameHolder_lbl;

    @FXML
    private TextField lastName_TF;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Button newRequest;

    @FXML
    private Button reload_btn;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userCounter_lbl1;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TableView<?> requestTableView;

    @FXML
    private TableColumn<?, ?> userTable_col_Dept;

    @FXML
    private TableColumn<?, ?> userTable_col_Dept1;

    @FXML
    private TableColumn<?, ?> userTable_col_Email;

    @FXML
    private TableColumn<?, ?> userTable_col_ExpiresOn;

    @FXML
    private TableColumn<?, ?> userTable_col_FName;

    @FXML
    private TableColumn<?, ?> userTable_col_LName;

    @FXML
    private TableColumn<?, ?> userTable_col_createdAt;

    @FXML
    private TableColumn<?, ?> userTable_col_userID;

    @FXML
    private TextField username_TF;

    @FXML
    private Button viewRequest;





    @FXML
    private void raiseNewTicket() {

        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) requestTableView.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage


        //SupplierModel selectedSupplier = requestTableView.getSelectionModel().getSelectedItem();

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewConstants.ADMIN_RAISE_TICKET_POP_UP));
            Parent root = modalViewLoader.load();




            // New window setup as modal
            Stage supplierPopUpStage = new Stage();
            supplierPopUpStage.initOwner(currentDashboardStage);
            supplierPopUpStage.initModality(Modality.WINDOW_MODAL);
            supplierPopUpStage.initStyle(StageStyle.TRANSPARENT);


            Scene scene = new Scene(root);
            supplierPopUpStage.setScene(scene);

            supplierPopUpStage.showAndWait(); // Blocks interaction with the main stage

        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            currentDashboardStage.getScene().getRoot().setEffect(null); // Remove blur effect and reload data on close



            Platform.runLater(this::countCreatedRequests);
            Platform.runLater(this::countOnProgressRequests);
            Platform.runLater(this::countClosedCalls);

        }





    }

    @FXML
    private void reloadTable() {

    }


    @FXML
    private void countCreatedRequests() {
        // TODO counts the number of created requests
    }


    @FXML
    private void countOnProgressRequests() {
        // TODO counts the number of in progress requests
    }

    @FXML
    private void countClosedCalls() {
        // TODO counts the number of closed requests
    }

}
