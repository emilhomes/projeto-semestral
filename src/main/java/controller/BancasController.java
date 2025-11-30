package controller;

import dao.BancaDAO;
import model.BancaModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BancasController implements Initializable {

    @FXML
    private DatePicker inputData;
    @FXML
    private TextArea inputMembros;

    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnNovaBanca;

    @FXML
    private TableView<BancaModel> tableBancas;

    @FXML
    private TableColumn<BancaModel, Integer> colIdBanca;
    @FXML
    private TableColumn<BancaModel, LocalDate> colData;

    @FXML
    private TableColumn<BancaModel, String> colMembros;

    @FXML
    private TableColumn<BancaModel, Void> colAcoes;

    private BancaDAO dao = new BancaDAO();
    private BancaModel bancaEmEdicao = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();

        try {
            carregarDados();
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarColunas() {

        colIdBanca.setCellValueFactory(new PropertyValueFactory<>("idBanca"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataDefesa"));

        colMembros.setCellValueFactory(new PropertyValueFactory<>("menbros"));

        adicionarBotoesAcao();
    }

    private void carregarDados() {
        tableBancas.setItems(FXCollections.observableArrayList(dao.listar()));
    }

    private void adicionarBotoesAcao() {
        Callback<TableColumn<BancaModel, Void>, TableCell<BancaModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox pane = new HBox(10, btnEditar, btnExcluir);

            {

                btnEditar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
                btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                pane.setStyle("-fx-alignment: CENTER;");

                btnEditar.setOnAction(event -> {
                    BancaModel banca = getTableView().getItems().get(getIndex());
                    preencherFormulario(banca);
                });

                btnExcluir.setOnAction(event -> {
                    BancaModel banca = getTableView().getItems().get(getIndex());
                    dao.deletar(banca.getIdBanca());
                    carregarDados();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void preencherFormulario(BancaModel banca) {
        this.bancaEmEdicao = banca;
        inputData.setValue(banca.getDataDefesa());
        inputMembros.setText(banca.getMenbros());
        btnSalvar.setText("Atualizar");
    }

    @FXML
    void salvarBanca(ActionEvent event) {
        if (inputData.getValue() == null || inputMembros.getText().isEmpty()) {
            mostrarAlerta("Aviso", "Preencha a Data e os Membros.");
            return;
        }

        try {
            BancaModel banca = (bancaEmEdicao != null) ? bancaEmEdicao : new BancaModel();
            banca.setDataDefesa(inputData.getValue());
            banca.setMenbros(inputMembros.getText());

            if (banca.getIdBanca() > 0) {
                dao.atualizar(banca);
                mostrarAlerta("Sucesso", "Banca atualizada!");
            } else {
                dao.inserir(banca);
                mostrarAlerta("Sucesso", "Banca cadastrada!");
            }

            limparCampos(null);
            carregarDados();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao salvar.");
        }
    }

    @FXML
    void limparCampos(ActionEvent event) {
        inputData.setValue(null);
        inputMembros.clear();
        bancaEmEdicao = null;
        btnSalvar.setText("Salvar");
    }

    @FXML
    void novaBanca(ActionEvent event) {
        limparCampos(null);
        inputData.requestFocus();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}