package com.example.fyp_application.Controllers.Client.DashboardControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ClientHomePageController {

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private Button hello_btn;

/*


    private static final SupplierDAO SUPPLIER_DAO = new SupplierDAO();

    public void loadFlowPaneData(){
        ObservableList<SupplierModel> userModels;

        try {
            userModels = SUPPLIER_DAO.getAllSuppliers();
            for(SupplierModel userModel : userModels){
                VBox userBox = new VBox(10); // VBox with spacing of 10 pixels
                userBox.setAlignment(Pos.CENTER);
                userBox.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;");

                Circle circle = new Circle(50, 50, 50);

                Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(userModel.g())));
                circle.setFill(new ImagePattern(curPhoto));

                Label name = new Label(userModel.getSupplierName());

                Button profileButton = new Button("View Profile");

                userBox.getChildren().addAll(circle, name, profileButton);
                sampleFlowPane.getChildren().add(userBox);

                profileButton.setOnAction(e -> openProfile(userModel)); // Replace with your method to
                // open the profile


                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openProfile( SupplierModel selectedUser){
        GaussianBlur blur = new GaussianBlur(10);
        Stage currentDashboardStage = (Stage) sampleFlowPane.getScene().getWindow();
        currentDashboardStage.getScene().getRoot().setEffect(blur); // Apply blur to main dashboard stage

        try {
            //Load the supplier menu
            //modal pop-up dialogue box
            FXMLLoader modalViewLoader = new FXMLLoader(getClass().getResource(ViewHandler.ADMIN_EDIT_SUPPLIER_POP_UP));
            Parent root = modalViewLoader.load();

            ModifiedEditSupplierController editUserController = modalViewLoader.getController();
            editUserController.loadSelectedSupplierDetails(selectedUser);


                ModifiedEditSupplierController editSupplierController = modalViewLoader.getController();
                editSupplierController.loadSelectedSupplierDetails(selectedUser);


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
            sampleFlowPane.getChildren().clear();
            loadFlowPaneData();
        }

    }
*/

}