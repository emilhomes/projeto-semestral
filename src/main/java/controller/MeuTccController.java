package controller;

import dao.TccDAO;
import dao.OrientadorDAO;
import dao.VersaoDocumentoDAO;
import dao.ComentarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.TccModel;
import model.UsuarioModel;
import model.VersaoDocumentoModel;
import model.ComentarioModel;
import service.AuthenticationService;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class MeuTccController {

      @FXML
      private Label lblTitulo;
      @FXML
      private Label lblOrientador;
      @FXML
      private Label lblStatus;
      @FXML
      private ProgressBar barraProgresso;
      @FXML
      private Label labelProgresso;
      @FXML
      private Label labelDataAtual;
      @FXML
      private VBox listaComentarios;
      @FXML
      private TextArea campoComentario;

      @FXML
      private Button btnEditarTCC;
      @FXML
      private Button btnExcluirTCC;
      @FXML
      private Button btnAlterarOrientador;
      @FXML
      private Button btnAlterarStatus;
      @FXML
      private Button btnUpload;
      @FXML
      private Button btnVersoes;
      @FXML
      private Button btnBaixarAtual;
      @FXML
      private Button btnEnviarComentario;

      private TccModel tccAtual;
      private UsuarioModel usuario;
      @FXML
      private StackPane contentArea;

      @FXML
      public void initialize() {
            usuario = AuthenticationService.getUsuarioLogado();
            carregarTcc();
            // carregarComentarios();
      }

      // ===========================
      // CARREGAR DADOS DO TCC
      // ===========================
      private void carregarTcc() {
            TccDAO tccDAO = new TccDAO();
            tccAtual = tccDAO.buscarTccComOrientador(usuario.getIdUsuario()); // JOIN para pegar nome do orientador

            if (tccAtual != null) {
                  lblTitulo.setText(tccAtual.getTitulo());
                  lblStatus.setText(tccAtual.getEstado());
                  lblOrientador.setText(tccAtual.getNomeOrientador());
                  labelDataAtual.setText("Enviado em: " + tccAtual.getDataCadastro());
            }
      }

      // ===========================
      // BOTÕES CRUD
      // ===========================
      @FXML
      private void editarTCC(ActionEvent event) {
            carregarTela("tela-aluno/editar-tcc.fxml");
      }

      @FXML
      private void excluirTCC(ActionEvent event) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                        "Deseja realmente excluir o TCC?", ButtonType.YES, ButtonType.NO);
            alerta.showAndWait().ifPresent(res -> {
                  if (res == ButtonType.YES) {
                        new TccDAO().deletar(tccAtual.getIdTCC());
                        lblTitulo.setText("TCC excluído");
                        lblStatus.setText("-");
                        lblOrientador.setText("-");
                  }
            });
      }

      @FXML
      private void alterarOrientador(ActionEvent event) {
            TextInputDialog dialog = new TextInputDialog(tccAtual.getNomeOrientador());
            dialog.setTitle("Alterar Orientador");
            dialog.setHeaderText("Digite o novo nome do orientador:");
            dialog.showAndWait().ifPresent(nome -> {
                  OrientadorDAO dao = new OrientadorDAO();
                  tccAtual.setIdOrientador(dao.buscarPorNome(nome).getIdUsuario());
                  tccAtual.setNomeOrientador(nome);
                  new TccDAO().atualizar(tccAtual);
                  lblOrientador.setText(nome);
            });
      }

      @FXML
      private void alterarStatus(ActionEvent event) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(tccAtual.getEstado(),
                        "Em andamento", "Concluído", "Reprovado");
            dialog.setTitle("Alterar Status");
            dialog.setHeaderText("Selecione o novo status do TCC:");
            dialog.showAndWait().ifPresent(status -> {
                  tccAtual.setEstado(status);
                  new TccDAO().atualizar(tccAtual);
                  lblStatus.setText(status);
            });
      }

      // ===========================
      // UPLOAD DE VERSÃO
      // ===========================
      @FXML
      private void enviarNovaVersao(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            File arquivo = fileChooser.showOpenDialog(null);
            if (arquivo != null) {
                  VersaoDocumentoModel versao = new VersaoDocumentoModel();
                  versao.setIdTCC(tccAtual.getIdTCC());
                  versao.setDataEnvio(LocalDate.now());
                  versao.setNomeArquivo(arquivo.getAbsolutePath());
                  new VersaoDocumentoDAO().inserir(versao);

                  labelDataAtual.setText("Enviado em: " + versao.getDataEnvio());
            }
      }

      @FXML
      private void verVersoes(ActionEvent event) {
            // Aqui você poderia abrir uma nova janela com todas as versões do TCC
            System.out.println("Abrir tela de versões");
      }

      @FXML
      private void baixarVersaoAtual(ActionEvent event) {
            // Implementar download do arquivo da versão atual
            System.out.println("Baixando versão atual...");
      }

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

      // ===========================
      // COMENTÁRIOS
      // ===========================
      // @FXML
      // private void enviarComentario() {
      // String texto = campoComentario.getText();
      // if (texto.isEmpty()) return;

      // ComentarioModel comentario = new ComentarioModel();
      // comentario.setIdTcc(tccAtual.getIdTcc());
      // comentario.setComentario(texto);
      // comentario.setData(LocalDate.now());
      // comentario.setAutor(usuario.getNome());
      // new ComentarioDAO().inserir(comentario);

      // campoComentario.clear();
      // carregarComentarios();
      // }

      // private void carregarComentarios() {
      // listaComentarios.getChildren().clear();
      // ComentarioDAO dao = new ComentarioDAO();
      // List<ComentarioModel> comentarios = dao.listarPorTcc(tccAtual.getIdTcc());

      // for (ComentarioModel c : comentarios) {
      // Label lbl = new Label(c.getAutor() + ": " + c.getComentario());
      // lbl.setStyle("-fx-background-color: #E3F2FD; -fx-padding: 6;
      // -fx-border-radius: 6; -fx-background-radius: 6;");
      // listaComentarios.getChildren().add(lbl);
      // }
      // }
}

// public void initialize() {

// UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
// AlunoDAO dao = new AlunoDAO();
// AlunoModel aluno = dao.buscarPorUsuarioId(usuario.getIdUsuario());
// TccDAO tccdao = new TccDAO();
// TccModel tcc = tccdao.buscarTccPorUsuarioId(usuario.getIdUsuario());

// lblTitulo.setText(tcc.getTitulo());
// lblStatus.setText(tcc.getEstado());
// lblOrientador.setText(tcc.getNomeOrientador());
// // lblCurso.setText(aluno.getCurso());

// }
