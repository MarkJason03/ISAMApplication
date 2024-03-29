package com.example.fyp_application.Utils;

import javafx.scene.Node;
import javafx.stage.Stage;

public class SharedButtonUtils {



    private SharedButtonUtils(){

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

    public static void closeMenu(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        if(stage != null){
            stage.close();
        }
    }

/*
    ///
    @FXML
    private void reloadTable(){
        Platform.runLater(this::loadTableData);
        lastUpdate_lbl.setText("Last Updated: " + DateTimeUtils.getCurrentTimeFormat());
    }
*/


}
