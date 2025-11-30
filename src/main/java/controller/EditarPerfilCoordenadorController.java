package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UsuarioModel;
import service.AuthenticationService;

public class EditarPerfilCoordenadorController implements Initializable {

    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoEmail;
    @FXML
    private TextField campoMatricula;
    @FXML
    private TextField campoDepartamento;

    private UsuarioModel usuarioLogado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLogado = AuthenticationService.getUsuarioLogado();

        if (usuarioLogado != null) {
            campoNome.setText(usuarioLogado.getNome());
            campoEmail.setText(usuarioLogado.getEmailInstitucional());

            campoMatricula.setText(String.valueOf(usuarioLogado.getIdUsuario()));

            campoMatricula.setDisable(true);

            campoDepartamento.setText("Ciência da Computação");
        }
    }

    @FXML
    public void salvar(ActionEvent event) {
        if (campoNome.getText().isEmpty() || campoEmail.getText().isEmpty()) {
            mostrarAlerta("Erro", "Nome e Email são obrigatórios");
            return;
        }

        try {
            usuarioLogado.setNome(campoNome.getText());
            usuarioLogado.setEmailInstitucional(campoEmail.getText());

            AuthenticationService.setUsuarioLogado(usuarioLogado);

            mostrarAlerta("Sucesso", "Dados salvos com sucesso!");

            voltarParaDashboard();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao salvar.");
        }
    }

    @FXML
    public void cancelar(ActionEvent event) {
        voltarParaDashboard();
    }

    private void voltarParaDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/tela-coordenador/dashboard-coordenador.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) campoNome.getScene().getWindow();
            stage.setScene(new Scene(root)); 
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para o dashboard.");
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}