module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;

    opens controller to javafx.fxml;
    opens model to javafx.fxml;
    opens service to javafx.fxml;
    opens utils to javafx.fxml;
    opens conexao to javafx.fxml;
    opens main to javafx.fxml;

    exports controller;
    exports model;
    exports service;
    exports utils;
    exports conexao;
    exports main;
}
