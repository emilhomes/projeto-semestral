package controller;

import dao.AtividadeDAO;
import dao.BancaDAO;
import dao.PrazoInstitucionalDAO;
import model.AtividadeModel;
import model.BancaModel;
import model.PrazoInstitucionalModel;
import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class CronogramaListaController implements Initializable {

    @FXML private TableView<AtividadeModel> tabelaAtividades;
    @FXML private TableColumn<AtividadeModel, String> colDescricao;
    @FXML private TableColumn<AtividadeModel, LocalDate> colDataFim;
    @FXML private TableColumn<AtividadeModel, String> colEstado;
    @FXML private TableColumn<AtividadeModel, Void> colAcoes;

    // Instancia os DAOs
    private AtividadeDAO atividadeDAO = new AtividadeDAO();
    private BancaDAO bancaDAO = new BancaDAO();
    private PrazoInstitucionalDAO prazoDAO = new PrazoInstitucionalDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        try {
            carregarDadosUnificados(); // Busca tudo (pessoal + institucional)
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados do cronograma:");
            e.printStackTrace();
        }
    }

    private void configurarColunas() {
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("titulo")); 
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        adicionarBotoesDeAcao();
    }

    public void carregarDadosUnificados() {
        List<AtividadeModel> listaFinal = new ArrayList<>();

        // 1. ATIVIDADES PESSOAIS
        List<AtividadeModel> atividades = atividadeDAO.listar();
        for (AtividadeModel atv : atividades) {
            atv.setTipo("PESSOAL");
            if (atv.getTitulo() == null || atv.getTitulo().equals("Sem Título")) {
                 // Fallback para não ficar vazio se o banco antigo tinha só descrição
                 atv.setTitulo(atv.getDescricao());
            }
            listaFinal.add(atv);
        }

        // 2. PRAZOS INSTITUCIONAIS
        try {
            List<PrazoInstitucionalModel> prazos = prazoDAO.listar();
            for (PrazoInstitucionalModel p : prazos) {
                AtividadeModel conv = new AtividadeModel();
                conv.setTitulo("PRAZO: " + p.getNome()); // Prefixo para identificar
                conv.setDataFim(p.getDataFinal());
                conv.setEstado("Obrigatório");
                conv.setTipo("INSTITUCIONAL");
                listaFinal.add(conv);
            }
        } catch (Exception e) { System.out.println("Erro ao listar prazos (tabela existe?)"); }

        // 3. BANCAS
        try {
            List<BancaModel> bancas = bancaDAO.listar();
            for (BancaModel b : bancas) {
                AtividadeModel conv = new AtividadeModel();
                conv.setTitulo("BANCA (Defesa)");
                conv.setDescricao(b.getMenbros()); // Pode usar isso se quiser ver detalhes no tooltip
                conv.setDataFim(b.getDataDefesa());
                conv.setEstado("Agendada");
                conv.setTipo("BANCA");
                listaFinal.add(conv);
            }
        } catch (Exception e) { System.out.println("Erro ao listar bancas (tabela existe?)"); }

        // Ordena por data
        listaFinal.sort(Comparator.comparing(AtividadeModel::getDataFim, Comparator.nullsLast(Comparator.naturalOrder())));

        tabelaAtividades.setItems(FXCollections.observableArrayList(listaFinal));
    }

    private void adicionarBotoesDeAcao() {
        Callback<TableColumn<AtividadeModel, Void>, TableCell<AtividadeModel, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<AtividadeModel, Void> call(final TableColumn<AtividadeModel, Void> param) {
                return new TableCell<>() {
                    private final Button btnEditar = new Button("Editar");
                    private final Button btnExcluir = new Button("Excluir");
                    private final HBox pane = new HBox(10, btnEditar, btnExcluir);

                    {
                        btnEditar.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-cursor: hand;");
                        btnExcluir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");
                        pane.setStyle("-fx-alignment: CENTER;");

                        btnEditar.setOnAction(event -> {
                            AtividadeModel atv = getTableView().getItems().get(getIndex());
                            editarAtividade(atv);
                        });

                        btnExcluir.setOnAction(event -> {
                            AtividadeModel atv = getTableView().getItems().get(getIndex());
                            excluirAtividade(atv);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // SÓ MOSTRA OS BOTÕES SE FOR ATIVIDADE PESSOAL
                            AtividadeModel atual = getTableView().getItems().get(getIndex());
                            if ("PESSOAL".equals(atual.getTipo())) {
                                setGraphic(pane);
                            } else {
                                setGraphic(null); // Esconde botões para Prazos e Bancas
                            }
                        }
                    }
                };
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    // --- MÉTODOS DE AÇÃO (CRUD) ---

    private void excluirAtividade(AtividadeModel atividade) {
        if ("PESSOAL".equals(atividade.getTipo())) {
            atividadeDAO.deletar(atividade.getIdAtividade());
            carregarDadosUnificados();
        }
    }

    private void editarAtividade(AtividadeModel atividade) {
        if ("PESSOAL".equals(atividade.getTipo())) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/adicionar-atividade.fxml"));
                Parent formulario = loader.load();
                AtividadeController controller = loader.getController();
                controller.setAtividade(atividade); 
                navegarPara(formulario);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void novaAtividade(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/adicionar-atividade.fxml"));
            Parent formulario = loader.load();
            navegarPara(formulario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
}