package controller;

import dao.TccDAO;
import model.TccModel;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TccsOrientadosController implements Initializable {

    @FXML private TableView<TccModel> tabelaTccs;
    @FXML private TableColumn<TccModel, String> colAluno;
    @FXML private TableColumn<TccModel, String> colTitulo;
    @FXML private TableColumn<TccModel, String> colStatus;
    
    @FXML private TableColumn<TccModel, Void> colAcoes;

    private TccDAO tccDAO = new TccDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        try {
            carregarDados();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarColunas() {
        colAluno.setCellValueFactory(new PropertyValueFactory<>("nomeAluno"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("estado"));

        adicionarBotaoDetalhes();
    }

    private void adicionarBotaoDetalhes() {
        Callback<TableColumn<TccModel, Void>, TableCell<TccModel, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnDetalhes = new Button("Ver Detalhes");

            {
                
                btnDetalhes.setStyle("-fx-background-color: #1E3A5F; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
                
                
                btnDetalhes.setOnAction(event -> {
                    TccModel tccSelecionado = getTableView().getItems().get(getIndex());
                    abrirTelaDetalhes(tccSelecionado);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnDetalhes);
                }
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void abrirTelaDetalhes(TccModel tcc) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-orientador/detalhes-tcc.fxml"));
            Parent root = loader.load();

          
            DetalhesTccController controller = loader.getController();
            controller.setTcc(tcc); 

            
            StackPane dashboardStack = (StackPane) tabelaTccs.getScene().getRoot().lookup("#contentArea");
            
            if (dashboardStack != null) {
                dashboardStack.getChildren().clear();
                dashboardStack.getChildren().add(root);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao abrir detalhes do TCC.");
        }
    }

    private void carregarDados() {
        UsuarioModel orientadorLogado = AuthenticationService.getUsuarioLogado();
        
        System.out.println("=== DEBUG TCCs ORIENTADOS ===");

        if (orientadorLogado != null) {
            System.out.println("Orientador Logado: " + orientadorLogado.getNome() + " (ID: " + orientadorLogado.getIdUsuario() + ")");
            
            try {
                // Chama o DAO
                List<TccModel> lista = tccDAO.listarPorOrientador(orientadorLogado.getIdUsuario());
                
                System.out.println("Quantidade de TCCs encontrados: " + lista.size());
                for(TccModel tcc : lista) {
                    System.out.println(" - TCC ID: " + tcc.getIdTCC());
                    System.out.println("   Título: " + tcc.getTitulo());
                    System.out.println("   Nome Aluno: " + tcc.getNomeAluno()); 
                }

                tabelaTccs.setItems(FXCollections.observableArrayList(lista));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ERRO: Nenhum usuário logado.");
        }
        System.out.println("=============================");
    }
}