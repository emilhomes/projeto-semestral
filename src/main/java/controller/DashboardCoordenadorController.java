package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.UsuarioModel;
import service.AuthenticationService;

public class DashboardCoordenadorController {
    
    @FXML
    private Label lblNome;

    @FXML
    private Label lblEmail;

    @FXML 
    private Label lblMatricula;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();

        if(usuario != null) {
            lblNome.setText(usuario.getNome());
            lblEmail.setText(usuario.getEmailInstitucional());
            lblMatricula.setText("Matrícula: " + usuario.getIdUsuario());
        }
    }
    
    @FXML
    void abrirPerfil(ActionEvent event) {
        carregarTela("tela-coordenador/dashboard-coordenador.fxml");
    }

    @FXML
    void abrirEditarPerfil(ActionEvent event) {
        carregarTela("tela-coordenador/editar-dados-coordenador.fxml");
    }

    @FXML
    void abrirPrazos(ActionEvent event) {
        carregarTela("tela-coordenador/prazos-institucionais.fxml");
    }

    @FXML
    void abrirBanca(ActionEvent event) { 
        carregarTela("tela-coordenador/banca-crud.fxml");
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
            Parent novaTela = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(novaTela);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar tela: " + fxml);
        }
    }
}