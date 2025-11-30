package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.beans.property.SimpleStringProperty;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import dao.VersaoDocumentoDAO;
import model.VersaoDocumentoModel;
import model.TccModel;

public class VersoesTccController implements Initializable {
      @FXML
      private StackPane contentArea;

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
      private TableView<VersaoDocumentoModel> tabelaVersoes;
      @FXML
      private TableColumn<VersaoDocumentoModel, String> colArquivo;
      @FXML
      private TableColumn<VersaoDocumentoModel, String> colDataEnvio;
      @FXML
      private TableColumn<VersaoDocumentoModel, Void> colAcao;

      // Recebido da outra tela
      private static TccModel tccAtual;

      public static void setTccAtual(TccModel tcc) {
            tccAtual = tcc;
      }

      @Override
      public void initialize(URL location, ResourceBundle resources) {

            colArquivo.setCellValueFactory(
                        v -> new SimpleStringProperty(new File(v.getValue().getNomeArquivo()).getName()));

            colDataEnvio.setCellValueFactory(v -> new SimpleStringProperty(v.getValue().getDataEnvio().toString()));

            carregarVersoes();
            configurarColunaAcoes();
      }

      private void configurarColunaAcoes() {
            colAcao.setCellFactory(col -> new TableCell<>() {

                  private final Button btn = new Button("Baixar");

                  {
                        btn.setStyle(
                                    "-fx-background-color: #2C3E50; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 5;");
                        btn.setOnAction(event -> {
                              VersaoDocumentoModel versao = getTableView().getItems().get(getIndex());
                              baixarArquivo(versao);
                        });
                  }

                  private void baixarArquivo(VersaoDocumentoModel versao) {
                        try {
                              File origem = new File(versao.getNomeArquivo());

                              if (!origem.exists()) {
                                    System.out.println("Arquivo não encontrado: " + origem.getAbsolutePath());
                                    return;
                              }

                              FileChooser salvarDialogo = new FileChooser();
                              salvarDialogo.setInitialFileName(new File(versao.getNomeArquivo()).getName());

                              File destino = salvarDialogo.showSaveDialog(null);

                              if (destino != null) {
                                    Files.copy(origem.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    System.out.println("Arquivo salvo em: " + destino.getAbsolutePath());
                              }
                        } catch (Exception e) {
                              e.printStackTrace();
                        }
                  }

                  @Override
                  protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                              setGraphic(null);
                        } else {
                              setGraphic(btn);
                        }
                  }
            });
      }

      private void carregarVersoes() {
            if (tccAtual == null) {
                  System.out.println("tccAtual está nulo! A tela não recebeu o TCC.");
                  return;
            }

            VersaoDocumentoDAO dao = new VersaoDocumentoDAO();
            ObservableList<VersaoDocumentoModel> lista = FXCollections.observableArrayList(
                        dao.listarVersoesPorTcc(tccAtual.getIdTCC()));

            tabelaVersoes.setItems(lista);
      }
}
