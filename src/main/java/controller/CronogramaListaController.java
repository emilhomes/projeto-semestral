package controller;

import dao.AtividadeDAO;
import model.AtividadeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CronogramaListaController implements Initializable {

    // Vínculos com a Tabela do FXML
    @FXML private TableView<AtividadeModel> tabelaAtividades;
    @FXML private TableColumn<AtividadeModel, String> colDescricao;
    @FXML private TableColumn<AtividadeModel, LocalDate> colDataFim;
    @FXML private TableColumn<AtividadeModel, String> colEstado;
    
    // NOVA COLUNA
    @FXML private TableColumn<AtividadeModel, Void> colAcoes;

    private AtividadeDAO atividadeDAO = new AtividadeDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        
        try {
            carregarDados();
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados do banco:");
            e.printStackTrace();
        }
    }

    private void configurarColunas() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("titulo")); 
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        // Configura os botões na coluna de ações
        adicionarBotoesDeAcao();
    }

    // --- LÓGICA DOS BOTÕES NA TABELA ---
    private void adicionarBotoesDeAcao() {
        Callback<TableColumn<AtividadeModel, Void>, TableCell<AtividadeModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<AtividadeModel, Void> call(final TableColumn<AtividadeModel, Void> param) {
                return new TableCell<>() {

                    private final Button btnEditar = new Button("Editar");
                    private final Button btnExcluir = new Button("Excluir");
                    private final HBox pane = new HBox(10, btnEditar, btnExcluir);

                    {
                        // Estilização básica via código (opcional)
                        btnEditar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
                        btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                        pane.setStyle("-fx-alignment: CENTER;");

                        // Ação Editar
                        btnEditar.setOnAction(event -> {
                            AtividadeModel atividade = getTableView().getItems().get(getIndex());
                            editarAtividade(atividade);
                        });

                        // Ação Excluir
                        btnExcluir.setOnAction(event -> {
                            AtividadeModel atividade = getTableView().getItems().get(getIndex());
                            excluirAtividade(atividade);
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
            }
        };

        colAcoes.setCellFactory(cellFactory);
    }

    // --- AÇÕES DO CRUD ---

    private void excluirAtividade(AtividadeModel atividade) {
        // Remove do banco
        atividadeDAO.deletar(atividade.getIdAtividade());
        // Atualiza a tabela visualmente
        carregarDados();
    }

    private void editarAtividade(AtividadeModel atividade) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/adicionar-atividade.fxml"));
            Parent formulario = loader.load();

            // Pega o controller do formulário e passa os dados para ele
            AtividadeController controller = loader.getController();
            controller.setAtividade(atividade); 
            // OBS: Certifique-se de ter criado o método setAtividade no AtividadeController!

            // Navegação (troca a tela)
            navegarPara(formulario);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir edição.");
        }
    }

    @FXML
    void novaAtividade(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/adicionar-atividade.fxml"));
            Parent formulario = loader.load();
            
            // Navegação
            navegarPara(formulario);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir formulário de nova atividade.");
        }
    }

    // Método auxiliar para não repetir código de navegação
    private void navegarPara(Parent novaTela) {
        StackPane dashboardStack = (StackPane) tabelaAtividades.getScene().getRoot().lookup("#contentArea");
        
        if (dashboardStack != null) {
            dashboardStack.getChildren().clear();
            dashboardStack.getChildren().add(novaTela);
        } else {
            Parent root = tabelaAtividades.getScene().getRoot();
            if(root instanceof StackPane){
                 ((StackPane) root).getChildren().clear();
                 ((StackPane) root).getChildren().add(novaTela);
            }
        }
    }

    public void carregarDados() {
        List<AtividadeModel> listaDoBanco = atividadeDAO.listar();
        ObservableList<AtividadeModel> listaVisual = FXCollections.observableArrayList(listaDoBanco);
        tabelaAtividades.setItems(listaVisual);
    }
}