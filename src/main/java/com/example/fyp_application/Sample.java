package com.example.fyp_application;

import com.example.fyp_application.Controllers.Admin.DashboardControllers.OverviewStatisticsController;
import com.example.fyp_application.Views.ViewConstants;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Sample extends Application {

   private double xCoordinate;
    private double yCoordinate;
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource(ViewConstants.ADMIN_VIEW_STATISTICS)));

        Scene scene = new Scene(fxmlLoader.load());
        //scene.getStylesheets().add(getClass().getResource(ViewConstants.STYLESHEET).toExternalForm());
        scene.setOnMousePressed((MouseEvent event) -> {
            xCoordinate = event.getSceneX();
            yCoordinate = event.getSceneY();
        });

        scene.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xCoordinate);
            stage.setY(event.getScreenY() - yCoordinate);
        });


        stage.getIcons().add(new Image(getClass().getResourceAsStream(ViewConstants.APP_ICON)));
        stage.setTitle("FYP Application");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setResizable(false);


    }

    public static void main(String[] args) {
        launch(args);
    }
}
