package com.example.fyp_application.Utils;

import javafx.scene.Node;
import javafx.stage.Stage;

public class WindowCommandsHandler {



    private WindowCommandsHandler(){

    }

    public static void minimizeApplication(Node node){

        Stage stage = (Stage) node.getScene().getWindow();
        if( stage != null){
            stage.setIconified(true);
        }
    }

    public static void exitApplication(Node node, boolean response){
        Stage stage = (Stage) node.getScene().getWindow();
        if(stage != null && response){
            stage.close();
        }

    }

}
