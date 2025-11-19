package br.com.projetosemestral.controller;

import javafx.scene.control.Label;

import br.com.projetosemestral.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LoginController {
    
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Label labelErro;

    private final AuthenticationService authService = new AuthenticationService();

    @FXML 
    private void fazerLogin(ActionEvent actionEvent) {
        
        String email = campoEmail.getText();
        String senha = campoSenha.getText();

        if(authService.autenticar(email, senha)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard-aluno.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard do Aluno");
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
