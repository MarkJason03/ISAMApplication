package com.example.fyp_application.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageSupplierController implements Initializable {

    @FXML
    private Button addUser_btn;

    @FXML
    private Button addUser_btn1;

    @FXML
    private Button deleteUser_btn;

    @FXML
    private Button deleteUser_btn1;

    @FXML
    private Button editUser_btn;

    @FXML
    private Button editUser_btn1;

    @FXML
    private Label lastUpdate_lbl;

    @FXML
    private Label lastUpdate_lbl1;

    @FXML
    private Button reload_btn;

    @FXML
    private Button reload_btn1;

    @FXML
    private TextField searchBar_TF;

    @FXML
    private TextField searchBar_TF1;

    @FXML
    private Button search_btn;

    @FXML
    private Button search_btn1;

    @FXML
    private TextField supAddress_TF;

    @FXML
    private TextField supAddress_TF1;

    @FXML
    private TextField supContact_TF;

    @FXML
    private TextField supContact_TF1;

    @FXML
    private TextField supEmail_TF;

    @FXML
    private TextField supEmail_TF1;

    @FXML
    private TextField supID_TF;

    @FXML
    private TextField supID_TF1;

    @FXML
    private TextField supName_TF;

    @FXML
    private TextField supName_TF1;

    @FXML
    private TableView<?> supTableView;

    @FXML
    private TableColumn<?, ?> supTable_col_ID;

    @FXML
    private TableColumn<?, ?> supTable_col_ID1;

    @FXML
    private TableColumn<?, ?> supTable_col_supAddress;

    @FXML
    private TableColumn<?, ?> supTable_col_supAddress1;

    @FXML
    private TableColumn<?, ?> supTable_col_supContact;

    @FXML
    private TableColumn<?, ?> supTable_col_supContact1;

    @FXML
    private TableColumn<?, ?> supTable_col_supEmail;

    @FXML
    private TableColumn<?, ?> supTable_col_supEmail1;

    @FXML
    private TableColumn<?, ?> supTable_col_supName;

    @FXML
    private TableColumn<?, ?> supTable_col_supName1;

    @FXML
    private Label userCounter_lbl;

    @FXML
    private Label userInactiveCounter_lbl;

    @FXML
    private TableView<?> userTableView1;




    public void initialize(URL url, ResourceBundle rb){
        // TODO
    }

}
