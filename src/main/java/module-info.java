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
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;

    opens com.example.fyp_application to javafx.fxml;
    opens com.example.fyp_application.Controllers to javafx.fxml;
    opens com.example.fyp_application.Controllers.Admin to javafx.fxml;
    opens com.example.fyp_application.Controllers.Client to javafx.fxml;


    exports com.example.fyp_application;
    exports com.example.fyp_application.Controllers;
    exports com.example.fyp_application.Controllers.Admin;
    exports com.example.fyp_application.Controllers.Client;
    exports com.example.fyp_application.Model;

    exports com.example.fyp_application.Utils;
    opens com.example.fyp_application.Utils to javafx.fxml;
    exports com.example.fyp_application.Controllers.Admin.SupplierControllers;
    opens com.example.fyp_application.Controllers.Admin.SupplierControllers to javafx.fxml;


}