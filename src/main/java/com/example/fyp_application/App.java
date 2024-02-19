package com.example.fyp_application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("/FXML/AdminSide/AdminDashboard.fxml")));
        Scene scene = new Scene(fxmlLoader. load());
        stage.setTitle("FYP Application");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }


    public static void main(String[] args) {
        launch(args);
    }

}

