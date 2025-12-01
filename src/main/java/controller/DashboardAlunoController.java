package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.AlunoModel;
import model.TccModel;
import model.UsuarioModel;

import java.io.IOException;

import dao.AlunoDAO;
import dao.TccDAO;
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
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        int idUsuario = usuario.getIdUsuario();

        TccDAO tccDAO = new TccDAO();
        TccModel tcc = tccDAO.buscarTccPorUsuarioId(idUsuario);

        if (tcc != null) {
            carregarTela("tela-aluno/com-tcc.fxml");
        } else {
            carregarTela("tela-aluno/sem-tcc.fxml");
        }
    }

    @FXML
    private void abrirCronograma(ActionEvent event) {
        carregarTela("tela-aluno/cronograma-lista.fxml");
    }

    @FXML
    private void abrirEditarPerfil(ActionEvent event) {
        carregarTela("tela-aluno/editar-perfil.fxml");
    }

   
    @FXML
    private void carregarTela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));

            contentArea.getChildren().clear();  
            contentArea.getChildren().add(root); 

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + fxml);
        }
    }
}
