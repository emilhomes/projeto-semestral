package controller;

import dao.VersaoDocumentoDAO;
import model.TccModel;
import model.VersaoDocumentoModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DetalhesTccController implements Initializable {

    @FXML private Label lblTituloTcc;
    @FXML private Label lblNomeAluno;
    
    @FXML private TableView<VersaoDocumentoModel> tabelaVersoes;
    @FXML private TableColumn<VersaoDocumentoModel, String> colVersao;
    @FXML private TableColumn<VersaoDocumentoModel, LocalDate> colData;
    
    @FXML private TextArea txtComentario;

    private TccModel tccSelecionado;
    private VersaoDocumentoDAO versaoDAO = new VersaoDocumentoDAO();
    private VersaoDocumentoModel versaoSelecionada; // Guarda qual linha foi clicada

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarTabela();
        
        // Começa com o campo de texto bloqueado (só libera ao clicar numa versão)
        txtComentario.setDisable(true);
    }

    private void configurarTabela() {
        colVersao.setCellValueFactory(new PropertyValueFactory<>("nomeArquivo"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataEnvio"));

        // Isso faz o texto do comentário aparecer quando clica na linha
        tabelaVersoes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                versaoSelecionada = newVal;
                carregarComentarioNaTela(newVal);
            }
        });
    }

    // Método chamado pela tela anterior (Lista) para passar o TCC
    public void setTcc(TccModel tcc) {
        this.tccSelecionado = tcc;
        
        // Atualiza o cabeçalho
        lblTituloTcc.setText(tcc.getTitulo());
        // Se tiver nomeAluno no TccModel, use. Senão use o ID.
        if (tcc.getNomeAluno() != null) {
            lblNomeAluno.setText("Aluno: " + tcc.getNomeAluno());
        } else {
            lblNomeAluno.setText("Aluno ID: " + tcc.getIdAluno());
        }
        
        atualizarListaVersoes();
    }

    private void atualizarListaVersoes() {
        if (tccSelecionado != null) {
            tabelaVersoes.setItems(FXCollections.observableArrayList(
                versaoDAO.listarPorTcc(tccSelecionado.getIdTCC())
            ));
        }
    }

    private void carregarComentarioNaTela(VersaoDocumentoModel versao) {
        txtComentario.setDisable(false); // Libera a digitação
        
        // Pega o texto temporário que o DAO preencheu
        if (versao.getTextoComentario() != null) {
            txtComentario.setText(versao.getTextoComentario());
        } else {
            txtComentario.clear(); // Se não tem comentário, limpa o campo
        }
    }

    @FXML
    void salvarComentario(ActionEvent event) {
        if (versaoSelecionada != null) {
            // 1. Atualiza o objeto na memória com o que foi digitado
            versaoSelecionada.setTextoComentario(txtComentario.getText());
            
            // 2. Manda o DAO salvar no banco (ele decide se cria ou atualiza)
            try {
                versaoDAO.salvarComentario(versaoSelecionada);
                mostrarAlerta("Sucesso", "Feedback salvo com sucesso!");
            } catch (Exception e) {
                mostrarAlerta("Erro", "Falha ao salvar comentário.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            // Carrega de volta a lista de TCCs
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-orientador/tccs-orientados.fxml"));
            Parent root = loader.load();
            
            // Navegação segura via StackPane
            StackPane dashboardStack = (StackPane) lblTituloTcc.getScene().getRoot().lookup("#contentArea");
            if (dashboardStack != null) {
                dashboardStack.getChildren().clear();
                dashboardStack.getChildren().add(root);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}