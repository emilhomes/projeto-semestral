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

    // --- CAMPOS DO FORMULÁRIO ---
    @FXML private DatePicker inputData;
    @FXML private TextArea inputMembros;
    
    @FXML private Button btnSalvar;
    @FXML private Button btnLimpar;
    @FXML private Button btnNovaBanca;

    // --- TABELA ---
    @FXML private TableView<BancaModel> tableBancas;
    
    @FXML private TableColumn<BancaModel, Integer> colIdBanca;
    @FXML private TableColumn<BancaModel, LocalDate> colData;
    
    // ESTA É A COLUNA QUE FALTAVA MOSTRAR
    @FXML private TableColumn<BancaModel, String> colMembros;
    
    @FXML private TableColumn<BancaModel, Void> colAcoes;

    // Dependências
    private BancaDAO dao = new BancaDAO();
    private BancaModel bancaEmEdicao = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        
        // Try-Catch para garantir que erro de banco não trave a tela
        try {
            carregarDados();
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void configurarColunas() {
        // Vincula as colunas com os atributos do BancaModel
        colIdBanca.setCellValueFactory(new PropertyValueFactory<>("idBanca"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataDefesa")); 
        
        // IMPORTANTE: O nome aqui ("menbros") deve ser IGUAL ao do BancaModel
        colMembros.setCellValueFactory(new PropertyValueFactory<>("menbros")); 

        // Configura os botões
        adicionarBotoesAcao();
    }

    private void carregarDados() {
        tableBancas.setItems(FXCollections.observableArrayList(dao.listar()));
    }

    // --- LÓGICA DOS BOTÕES (EDITAR / EXCLUIR) ---
    private void adicionarBotoesAcao() {
        Callback<TableColumn<BancaModel, Void>, TableCell<BancaModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox pane = new HBox(10, btnEditar, btnExcluir);

            {
                // Estilo
                btnEditar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
                btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                pane.setStyle("-fx-alignment: CENTER;");

                // Ação Editar
                btnEditar.setOnAction(event -> {
                    BancaModel banca = getTableView().getItems().get(getIndex());
                    preencherFormulario(banca);
                });

                // Ação Excluir
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

    // --- RESTANTE DAS AÇÕES (Igual ao seu código) ---

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