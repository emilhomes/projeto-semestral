package controller;

import dao.TccDAO;
import dao.ComentarioDAO;
import dao.OrientadorDAO;
import dao.VersaoDocumentoDAO;
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
import model.ComentarioModel;
import model.TccModel;
import model.UsuarioModel;
import model.VersaoDocumentoModel;
import service.AuthenticationService;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
      private Label lblResumo;
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

      private VersaoDocumentoModel versaoAtual;

      private TccModel tccAtual;
      private UsuarioModel usuario;
      @FXML
      private StackPane contentArea;

      @FXML
      public void initialize() {
            usuario = AuthenticationService.getUsuarioLogado();
            carregarTcc();
            carregarComentarios();
      }

      private void carregarTcc() {
            TccDAO tccDAO = new TccDAO();
            tccAtual = tccDAO.buscarTccComOrientador(usuario.getIdUsuario());
            if (tccAtual != null) {
                  lblTitulo.setText(tccAtual.getTitulo());
                  lblStatus.setText(tccAtual.getEstado());
                  lblResumo.setText(tccAtual.getResumo());
                  lblOrientador.setText(tccAtual.getNomeOrientador());
                  labelDataAtual.setText("Enviado em: " + tccAtual.getDataCadastro());

            }
      }

      @FXML
      private void abrirPerfil(ActionEvent event) {
            carregarTela("tela-aluno/dashboard-aluno.fxml");
      }

      @FXML
      private void abrirMeuTCC(ActionEvent event) {
            carregarTela("tela-aluno/com-tcc.fxml");
      }

      @FXML
      private void abrirCronograma(ActionEvent event) {
            carregarTela("tela-aluno/cronograma-lista.fxml");
      }

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
                  new TccDAO().atualizarOrientador(tccAtual);
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
                  new TccDAO().atualizarEstado(tccAtual);
                  lblStatus.setText(status);
            });
      }

      public void setVersaoAtual(VersaoDocumentoModel versao) {
            this.versaoAtual = versao;

            if (versao != null) {
                  labelDataAtual.setText("Enviado em: " + versao.getDataEnvio());
            }
      }

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
                  setVersaoAtual(versao);
                  labelDataAtual.setText("Enviado em: " + versao.getDataEnvio());
            }
      }

      @FXML
      private void verVersoes(ActionEvent event) {
            VersoesTccController.setTccAtual(tccAtual);
            carregarTela("tela-aluno/versoes_tcc.fxml");
      }

      @FXML
      private void baixarVersaoAtual(ActionEvent event) {
            if (versaoAtual == null) {
                  System.out.println("versaoAtual está nulo!");
                  return;
            }

            if (versaoAtual.getNomeArquivo() == null) {
                  System.out.println("Nome do arquivo da versão atual está nulo!");
                  return;
            }
            FileChooser salvarDialogo = new FileChooser();
            salvarDialogo.setInitialFileName(new File(versaoAtual.getNomeArquivo()).getName());
            File destino = salvarDialogo.showSaveDialog(null);

            if (destino != null) {
                  try {
                        File origem = new File(versaoAtual.getNomeArquivo());
                        Files.copy(origem.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Arquivo salvo em: " + destino.getAbsolutePath());
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
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

    
       @FXML
       private void enviarComentario() {
       String texto = campoComentario.getText();
       if (texto.isEmpty()) return;

       ComentarioModel comentario = new ComentarioModel();
       comentario.setIdTCC(tccAtual.getIdTCC());
       comentario.setConteudo(texto);
       comentario.setUsuario(usuario.getNome());
       new ComentarioDAO().inserir(comentario);

       campoComentario.clear();
       carregarComentarios();
       }

       private void carregarComentarios() {
       listaComentarios.getChildren().clear();
       ComentarioDAO dao = new ComentarioDAO();
       List<ComentarioModel> comentarios = dao.listarPorTcc(tccAtual.getIdTCC());

       for (ComentarioModel c : comentarios) {
       Label lbl = new Label(c.getUsuario() + ": " + c.getConteudo());
       lbl.setStyle("-fx-background-color: #2C3E50;; -fx-padding: 6;-fx-border-radius: 6; -fx-background-radius: 6;");
       listaComentarios.getChildren().add(lbl);
       }
       }
}