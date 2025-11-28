package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.AlunoModel;
import model.UsuarioModel;

import java.io.IOException;

import dao.AlunoDAO;
import service.AuthenticationService;

public class DashboardAlunoController {

    @FXML
    private Label lblNome;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblMatricula;

    @FXML
    private Label lblCurso;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {

        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        AlunoDAO dao = new AlunoDAO();
        AlunoModel aluno = dao.buscarPorUsuarioId(usuario.getIdUsuario());

        lblNome.setText(usuario.getNome());
        lblEmail.setText(usuario.getEmailInstitucional());
        lblMatricula.setText(String.valueOf(aluno.getMatricula()));
        lblCurso.setText(aluno.getCurso());
    }


    @FXML
    private void abrirPerfil(ActionEvent event) {
        carregarTela("tela-aluno/perfil.fxml");
    }

    @FXML
    private void abrirMeuTCC(ActionEvent event) {
        System.out.println("BOTÃO CLICADO");
        carregarTela("tela-aluno/sem-tcc.fxml");
    }

    @FXML
    private void abrirCronograma(ActionEvent event) {
        try {
        // 1. Carrega o arquivo da LISTA (que tem a tabela)
        // Verifique se o caminho "/view/tela-aluno/cronograma-lista.fxml" está correto
        Parent fxml = FXMLLoader.load(getClass().getResource("/view/tela-aluno/cronograma-lista.fxml"));
        
        // 2. Limpa o centro atual do Dashboard
        contentArea.getChildren().clear();
        
        // 3. Adiciona a tela de lista no lugar
        contentArea.getChildren().add(fxml);
        
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar o cronograma.");
        }
    }

    @FXML
    private void carregarTela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            Stage stage = new Stage(); // cria nova janela
            stage.setScene(new Scene(root));
            stage.show();

            // Fechar a janela atual
            Stage atual = (Stage) contentArea.getScene().getWindow();
            atual.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}