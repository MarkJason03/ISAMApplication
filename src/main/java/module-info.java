module com.example.fyp_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.fyp_application to javafx.fxml;
    opens com.example.fyp_application.Controllers to javafx.fxml;
    opens com.example.fyp_application.Controllers.Admin to javafx.fxml;
    opens com.example.fyp_application.Controllers.Client to javafx.fxml;


    exports com.example.fyp_application;
    exports com.example.fyp_application.Controllers;
    exports com.example.fyp_application.Controllers.Admin;
    exports com.example.fyp_application.Controllers.Client;
    exports com.example.fyp_application.Model;
    exports com.example.fyp_application.Views;


}