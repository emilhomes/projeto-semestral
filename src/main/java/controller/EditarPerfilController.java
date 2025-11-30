package controller;

import java.io.IOException;

import dao.AlunoDAO;
import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.AlunoModel;
import model.UsuarioModel;
import service.AuthenticationService;

public class EditarPerfilController {

      @FXML
      private TextField inputNome;
      @FXML
      private TextField inputMatricula;
      @FXML
      private TextField inputEmail;
      @FXML
      private TextField inputCurso;

      private UsuarioDAO usuarioDAO = new UsuarioDAO();
      private UsuarioModel usuario;
      private AlunoModel aluno;
      private AlunoDAO alunoDAO = new AlunoDAO();

      @FXML
      public void initialize() {
            carregarUsuario();
      }

      private void carregarUsuario() {
            try {
                  int idUsuario = AuthenticationService.getUsuarioLogado().getIdUsuario();

                  usuario = usuarioDAO.buscarPorUsuarioId(idUsuario);
                  aluno = alunoDAO.buscarPorUsuarioId(idUsuario);

                  inputNome.setText(usuario.getNome());
                  inputEmail.setText(usuario.getEmailInstitucional());

                  inputMatricula.setText(String.valueOf(aluno.getMatricula()));
                  inputCurso.setText(aluno.getCurso());

            } catch (Exception e) {
                  mostrarErro("Erro ao carregar dados do aluno.", e);
            }
      }

      @FXML
      private void salvarPerfil() {
            try {
                  usuario.setNome(inputNome.getText());
                  usuario.setEmailInstitucional(inputEmail.getText());
                  usuarioDAO.atualizar(usuario);

                  inputMatricula.setText(String.valueOf(aluno.getMatricula()));
                  aluno.setCurso(inputCurso.getText());
                  alunoDAO.atualizar(aluno);

                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Sucesso");
                  alert.setHeaderText("Perfil atualizado com sucesso!");
                  alert.showAndWait();

            } catch (Exception e) {
                  mostrarErro("Erro ao salvar perfil.", e);
            }
      }

      @FXML
      private void abrirPerfil() {
            abrirTela("/view/perfil.fxml");
      }

      @FXML
      private void abrirMeuTCC() {
            abrirTela("/view/tcc.fxml");
      }

      @FXML
      private void abrirCronograma() {
            abrirTela("/view/cronograma.fxml");
      }

      private void abrirTela(String caminhoFXML) {
            try {
                  Parent root = FXMLLoader.load(getClass().getResource(caminhoFXML));
                  Stage stage = (Stage) inputNome.getScene().getWindow();
                  stage.setScene(new Scene(root));
            } catch (IOException e) {
                  mostrarErro("Erro ao abrir tela: " + caminhoFXML, e);
            }
      }

      private void mostrarErro(String msg, Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(msg);

            if (e != null)
                  alert.setContentText(e.getMessage());

            alert.showAndWait();
      }

}
