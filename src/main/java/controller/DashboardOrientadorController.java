package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardOrientadorController {

    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        abrirPerfil(null); // Carrega a home ao iniciar
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