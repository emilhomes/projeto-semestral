package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class DashboardAlunoController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        carregarTela("/view/tela-aluno/perfil.fxml");
    }

    @FXML
    private void abrirPerfil() {
        carregarTela("/view/tela-aluno/perfil.fxml");
    }

    @FXML
    private void abrirMeuTCC() {
        carregarTela("/view/tela-aluno/sem-tcc-cadastrado.fxml");
    }

    @FXML
    private void abrirCronograma() {
        carregarTela("/view/tela-aluno/cronograma.fxml");
    }

    private void carregarTela(String root) {
        try {
            Parent screen  = FXMLLoader.load(getClass().getResource(root));
            contentArea.getChildren().setAll(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}