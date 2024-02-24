package com.example.fyp_application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class App extends Application{

    private double x = 0;
    private double y = 0;


    @Override
    public void start(Stage stage) throws Exception {


        //FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/FXML/LoginPage.fxml")));
        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/FXML/ClientView/ClientDashboard.fxml")));
        Scene scene = new Scene(fxmlLoader. load());

        scene.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });


        stage.setTitle("FYP Application");
        stage.setScene(scene);
        /*stage.initStyle(StageStyle.UNDECORATED);*/
        stage.show();
        stage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }

}

