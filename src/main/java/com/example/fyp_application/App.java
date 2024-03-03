package com.example.fyp_application;

import com.example.fyp_application.Views.ViewHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class App extends Application{

    private double xCoordinate = 0;
    private double yCoordinate = 0;


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource(ViewHandler.APP_LOGIN)));

        Scene scene = new Scene(fxmlLoader.load());

        scene.setOnMousePressed((MouseEvent event) -> {
            xCoordinate = event.getSceneX();
            yCoordinate = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xCoordinate);
            stage.setY(event.getScreenY() - yCoordinate);
        });


        stage.setTitle("FYP Application");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setResizable(false);


    }


/*    @Override
    public void start(Stage primaryStage) throws Exception {
            // Load and set the first scene
        FXMLLoader firstLoader = new FXMLLoader(getClass().getResource(ViewHandler.APP_LOGIN));
        Scene firstScene = new Scene(firstLoader.load());
        primaryStage.setScene(firstScene);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Set the drag and drop feature for the first scene
        firstScene.setOnMousePressed((MouseEvent event) ->{
                xCoordinate = event.getSceneX();
                yCoordinate= event.getSceneY();
            });

        firstScene.setOnMouseDragged((MouseEvent event) ->{
            primaryStage.setX(event.getScreenX() - xCoordinate);
            primaryStage.setY(event.getScreenY() - yCoordinate);
            });
        primaryStage.show();

        // Create and set up the second stage (window)
        Stage secondStage = new Stage();
        FXMLLoader secondLoader = new FXMLLoader(getClass().getResource(ViewHandler.APP_LOGIN));
        Scene secondScene = new Scene(secondLoader.load());
        secondStage.setScene(secondScene);
        // removes the header of the window
        secondStage.initStyle(StageStyle.UNDECORATED);
        // Set the drag and drop feature for the second scene
        secondScene.setOnMousePressed((MouseEvent event) ->{
                xCoordinate = event.getSceneX();
                yCoordinate = event.getSceneY();
            });

        secondScene.setOnMouseDragged((MouseEvent event) ->{
            secondStage.setX(event.getScreenX() - xCoordinate);
            secondStage.setY(event.getScreenY() - yCoordinate);
            });

        secondStage.show();}*/

    public static void main(String[] args) {
        launch(args);
    }

}

