module com.example.fyp_application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires jdk.httpserver;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign2;

    requires javafx.graphics;

    requires javafx.base;


    requires eu.hansolo.tilesfx;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires MaterialFX;
    requires com.jfoenix;
    requires de.mkammerer.argon2;
    requires org.apache.commons.codec;
    requires java.mail;


    opens com.example.fyp_application to javafx.fxml;
    opens com.example.fyp_application.Controllers.Shared to javafx.fxml;

    opens com.example.fyp_application.Utils to javafx.fxml;
    opens com.example.fyp_application.Controllers.Admin.SupplierManagementControllers to javafx.fxml;



    exports com.example.fyp_application;

    exports com.example.fyp_application.Model;
    exports com.example.fyp_application.Utils;
    exports com.example.fyp_application.Controllers.Shared;
    exports com.example.fyp_application.Controllers.Admin.SupplierManagementControllers;
    exports com.example.fyp_application.Controllers.Admin.DashboardControllers;



    opens com.example.fyp_application.Controllers.Admin.DashboardControllers to javafx.fxml;

    exports com.example.fyp_application.Controllers.Client.ClientProfileManagementControllers;
    opens com.example.fyp_application.Controllers.Client.ClientProfileManagementControllers to javafx.fxml;

    exports com.example.fyp_application.Controllers.Admin.ProfileManagementController;
    opens com.example.fyp_application.Controllers.Admin.ProfileManagementController to javafx.fxml;

    exports com.example.fyp_application.Controllers.Admin.UserManagementControllers;
    opens com.example.fyp_application.Controllers.Admin.UserManagementControllers to javafx.fxml;

    exports com.example.fyp_application.Controllers.Admin.NavigationController;
    opens com.example.fyp_application.Controllers.Admin.NavigationController to javafx.fxml;

    exports com.example.fyp_application.Controllers.Client.NavigationController;
    opens com.example.fyp_application.Controllers.Client.NavigationController to javafx.fxml;

    exports com.example.fyp_application.Controllers.Client.DashboardControllers;
    opens com.example.fyp_application.Controllers.Client.DashboardControllers to javafx.fxml;

    exports com.example.fyp_application.Controllers.Admin.RequestManagementControllers;
    opens com.example.fyp_application.Controllers.Admin.RequestManagementControllers to javafx.fxml;

    exports com.example.fyp_application.Controllers.Client.ClientRequestControllers;
    opens com.example.fyp_application.Controllers.Client.ClientRequestControllers to javafx.fxml;

    exports com.example.fyp_application.Controllers.Admin.AssetManagementControllers;
    opens com.example.fyp_application.Controllers.Admin.AssetManagementControllers to javafx.fxml;

}