package com.example.fyp_application.Controllers.Client;

import com.example.fyp_application.Model.UserModel;
import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ClientDashboardController implements Initializable {

    @FXML
    private JFXDrawer drawerContainer;

    @FXML
    private Button menu_btn;

    @FXML
    private Button menu_btn1;

    @FXML
    private Circle loggedUserImage;

    @FXML
    private Label username_lbl;

    private UserModel currentUser;

    @FXML
    private AnchorPane contentAP;

    @FXML
    private AnchorPane dashboardStatAP;

    @FXML
    private BorderPane clientDashboardBorderPane;

    public static int userId;
    public static String name;

    private String photo = "/Assets/myself.png";

/*    @FXML
    private void logout() throws IOException {
        logout_btn.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/LoginPage.fxml")));
        Stage stage  = new Stage();
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();

    }*/

    @FXML
    private void initializeSideMenu(){
        try {

            VBox sideMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/ClientView/ClientSideBar.fxml")));
            drawerContainer.setSidePane(sideMenu);
            menu_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> openMenu() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



/*
    @FXML
    private void initializeSideMenu(){
        try {
            VBox sideMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/ClientView/ClientSideBar.fxml")));
            drawerContainer.setSidePane(sideMenu);
            menu_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> toggleMenu() );

            // Listen for changes in the drawer's translation to adjust the center content
            drawerContainer.translateXProperty().addListener((observable, oldValue, newValue) -> {
                adjustCenterContent();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void adjustCenterContent() {
        // Assuming the initial width of the drawer is known or can be dynamically obtained
        //100 is the width of the drawer
        double drawerWidth = drawerContainer.getDefaultDrawerSize();

        // TranslateX is 0 or near 0 when the drawer is open, otherwise, it's negative
        if (drawerContainer.getTranslateX() < -drawerWidth ) { // Drawer is closed or almost closed
            // Adjust the center content for a closed drawer
            // This could be setting a margin, padding, or resizing the content
            BorderPane.setMargin(clientDashboardBorderPane.getCenter(), new Insets(0, 0, 0, 0));
        } else {
            // Adjust the center content for an open drawer
            // Here you might add padding or margin equal to the drawer's width to the left side
            BorderPane.setMargin(clientDashboardBorderPane.getCenter(), new Insets(0, 0, 0, drawerWidth));
        }
    }
*/


  /*  @FXML
    private void openMenu(){
        if (drawerContainer.isOpened()) {
            drawerContainer.close();
        } else {
            drawerContainer.open();
        }
    }

    @FXML
    private void closeMenu(){
        drawerContainer.setDefaultDrawerSize(230); // Example width in pixels


        if (drawerContainer.isOpened()) {
            drawerContainer.close();}
    }*/

    @FXML
    private void openMenu() {
        drawerContainer.setVisible(true); // Make the drawer visible
        drawerContainer.open(); // Open the drawer

    }

    @FXML
    private void closeMenu() {
        drawerContainer.close(); // Close the drawer
        drawerContainer.setVisible(false); // Hide the drawer to allow the center to expand

    }



    public void loadCurrentUser(String id) {
        this.name = id;
        username_lbl.setText(String.valueOf(name));
        System.out.println(name);

        /*        username_lbl.setText(currentUser.getUsername());*/

    }


    public void setCurrentUserImage() {

        Image curPhoto = new Image(Objects.requireNonNull(getClass().getResourceAsStream(photo)));
        loggedUserImage.setFill(new ImagePattern(curPhoto));
    }

    public void initialize(URL location, ResourceBundle resources) {
/*
        initializeSideMenu();
*/
        /*setCurrentUserImage("src/main/resources/Assets/myself.png");*/
        /*Image image = new Image("src/main/resources/Assets/myself.png");*/
        setCurrentUserImage();
        /*initializeSideMenu();*/
        // Initial adjustment in case the drawer starts in an open state
        /*adjustCenterContent();*/
    }
}
