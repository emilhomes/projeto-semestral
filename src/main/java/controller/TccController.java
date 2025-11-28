package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TccController {
    @FXML
    public void initialize() {

    }

    @FXML
    private void CadastrarTCC(ActionEvent event) {
        System.out.println("BOTÃO CLICADO");
        carregarTela("tela-aluno/cadastro-tcc.fxml");
    }

    @FXML
    private StackPane contentArea;

    @FXML
    private void carregarTela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage atual = (Stage) contentArea.getScene().getWindow();
            atual.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
