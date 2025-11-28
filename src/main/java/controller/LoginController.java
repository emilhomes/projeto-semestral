package controller;

import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {

    @FXML
    private TextField campoEmail;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private Label labelErro;

    private final AuthenticationService authService = new AuthenticationService();

    @FXML
    private void fazerLogin(ActionEvent actionEvent) {

        System.out.println("BOTÃO CLICADO");
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        UsuarioModel usuario = authService.autenticar(email, senha);

        AuthenticationService.setUsuarioLogado(usuario);


        if (usuario != null) {
            String tipo = usuario.getTipoUsuario();

            String fxmlDestino = "";

            switch (tipo) {
                case "Aluno":
                    fxmlDestino = "/view/tela-aluno/dashboard-aluno.fxml";
                    break;

                case "Orientador":
                    fxmlDestino = "/view/tela-orientador/dashboard-orientador.fxml";
                    break;

                case "Coordenador":
                    fxmlDestino = "/view/tela-coordenador/dashboard-coordenador.fxml";
                    break;

                default:
                    labelErro.setText("Tipo de usuário inválido!");
                    labelErro.setVisible(true);
                    return;
            }

            try {
                Parent root = FXMLLoader.load(getClass().getResource(fxmlDestino));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            labelErro.setText("E-mail ou senha inválidos!");
            labelErro.setVisible(true);
        }
    }

    @FXML
    private void onAbrirCadastro(javafx.scene.input.MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/cadastro.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
