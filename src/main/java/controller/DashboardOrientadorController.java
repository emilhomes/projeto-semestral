package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardOrientadorController {

    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        abrirPerfil(null); 
    }

    @FXML
    void abrirPerfil(ActionEvent event) {
        carregarTela("tela-orientador/home-orientador.fxml");
    }

    @FXML
    void abrirTCCsOrientados(ActionEvent event) {
        carregarTela("tela-orientador/tccs-orientados.fxml");
    }

    @FXML
    void abrirBancas(ActionEvent event) {
        carregarTela("tela-orientador/bancas-orientador.fxml");
    }
    @FXML
    void sair(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            stage.setMaximized(true);

            stage.setTitle("Cadastro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
            Parent root = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}