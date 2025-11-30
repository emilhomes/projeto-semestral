package controller;

import dao.PrazoInstitucionalDAO;
import model.PrazoInstitucionalModel;
import model.UsuarioModel;
import service.AuthenticationService;
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

public class PrazoInstitucionalController implements Initializable {

  
    @FXML private TextField inputNome;
    @FXML private DatePicker inputData; 
    @FXML private TextArea inputDescricao;
    
    @FXML private Button btnSalvar;
    @FXML private Button btnLimpar;
    @FXML private Button btnNovoPrazo;

    @FXML private TableView<PrazoInstitucionalModel> tablePrazos;
    
    @FXML private TableColumn<PrazoInstitucionalModel, String> colNome;
    @FXML private TableColumn<PrazoInstitucionalModel, LocalDate> colData;
    @FXML private TableColumn<PrazoInstitucionalModel, String> colDescricao;
    @FXML private TableColumn<PrazoInstitucionalModel, Void> colAcoes;

    private PrazoInstitucionalDAO dao = new PrazoInstitucionalDAO();
    private PrazoInstitucionalModel prazoEmEdicao = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        
        try {
            carregarDados();
        } catch (Exception e) {
            System.err.println("Erro ao carregar prazos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    private void configurarColunas() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataFinal")); 
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        adicionarBotoesAcao();
    }

    private void carregarDados() {
        tablePrazos.setItems(FXCollections.observableArrayList(dao.listar()));
    }


    @FXML
    void salvarPrazo(ActionEvent event) {
        if (inputNome.getText().isEmpty() || inputData.getValue() == null) {
            mostrarAlerta("Campos Obrigatórios", "Por favor, preencha o Nome e a Data Limite.");
            return;
        }

        try {
            PrazoInstitucionalModel prazo = (prazoEmEdicao != null) ? prazoEmEdicao : new PrazoInstitucionalModel();
            
            prazo.setNome(inputNome.getText());
            prazo.setDataFinal(inputData.getValue());
            prazo.setDescricao(inputDescricao.getText());
            
            if (prazo.getIdPrazo() == 0) {
                prazo.setDataInicio(LocalDate.now());
            }

            UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
            if(usuario != null) {
                prazo.setIdCordenador(usuario.getIdUsuario());
            } else {
                prazo.setIdCordenador(1); 
            }

            if (prazo.getIdPrazo() > 0) {
                dao.atualizar(prazo);
                mostrarAlerta("Sucesso", "Prazo atualizado!");
            } else {
                dao.inserir(prazo);
                mostrarAlerta("Sucesso", "Prazo criado!");
            }

            limparCampos(null);
            carregarDados();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao salvar no banco.");
        }
    }

    @FXML
    void limparCampos(ActionEvent event) {
        inputNome.clear();
        inputData.setValue(null);
        inputDescricao.clear();
        
        prazoEmEdicao = null;
        btnSalvar.setText("Salvar");
    }

    @FXML
    void novoPrazo(ActionEvent event) {
        limparCampos(null);
        inputNome.requestFocus();
    }

    private void adicionarBotoesAcao() {
        Callback<TableColumn<PrazoInstitucionalModel, Void>, TableCell<PrazoInstitucionalModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnExcluir = new Button("Excluir");
            private final HBox pane = new HBox(10, btnEditar, btnExcluir);

            {
                
                btnEditar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
                btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                pane.setStyle("-fx-alignment: CENTER;");

                btnEditar.setOnAction(event -> {
                    PrazoInstitucionalModel p = getTableView().getItems().get(getIndex());
                    preencherFormulario(p);
                });

                btnExcluir.setOnAction(event -> {
                    PrazoInstitucionalModel p = getTableView().getItems().get(getIndex());
                    dao.deletar(p.getIdPrazo());
                    carregarDados();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void preencherFormulario(PrazoInstitucionalModel p) {
        this.prazoEmEdicao = p;
        
        inputNome.setText(p.getNome());
        inputData.setValue(p.getDataFinal());
        inputDescricao.setText(p.getDescricao());
        
        btnSalvar.setText("Atualizar");
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}