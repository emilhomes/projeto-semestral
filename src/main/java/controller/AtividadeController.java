package controller;

import java.io.IOException;
import java.time.LocalDate;

import dao.AtividadeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane; // Importante para navegação
import model.AtividadeModel;

public class AtividadeController {
    
    @FXML private TextField campoTituloAtividade;
    @FXML private TextArea campoDescricaoAtividade;
    @FXML private DatePicker campoDataLimite;
    @FXML private Button btnSalvarAtividade;
    @FXML private Button btnCancelar;

    private AtividadeDAO dao = new AtividadeDAO();
    
    // Variável para controlar se é edição (se não for null, é edição)
    private AtividadeModel atividadeAtual;

    /**
     * Este método é chamado pelo CronogramaListaController quando clicamos em "Editar".
     * Ele preenche os campos com os dados existentes.
     */
    public void setAtividade(AtividadeModel atividade) {
        this.atividadeAtual = atividade;

        if (atividade != null) {
            campoTituloAtividade.setText(atividade.getTitulo());
            campoDescricaoAtividade.setText(atividade.getDescricao());
            campoDataLimite.setValue(atividade.getDataFim());
            
            // Muda o texto do botão para indicar edição
            btnSalvarAtividade.setText("Salvar Alterações");
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        // Validação
        if(campoTituloAtividade.getText().isEmpty() || campoDataLimite.getValue() == null) {
            mostrarAlerta("Campos Obrigatórios", "Por favor, preencha o Título e a Data Limite.");
            return;
        }

        try {
            // Verifica se estamos criando um NOVO ou editando um EXISTENTE
            boolean isEdicao = (this.atividadeAtual != null);
            
            // Se for edição, usa o objeto existente (que tem o ID). Se novo, cria um zero.
            AtividadeModel model = isEdicao ? this.atividadeAtual : new AtividadeModel();

            // Atualiza os dados do objeto com o que está nos campos
            model.setTitulo(campoTituloAtividade.getText());
            model.setDescricao(campoDescricaoAtividade.getText());
            model.setDataFim(campoDataLimite.getValue());
            
            // Se for novo cadastro, define data de início hoje e status pendente
            if (!isEdicao) {
                model.setDataInicio(LocalDate.now());
                model.setEstado("Pendente");
            }
            // (Na edição, mantemos a data de início e o status originais)

            // Salva no Banco
            if (isEdicao) {
                dao.atualizar(model);
                mostrarAlerta("Sucesso", "Atividade atualizada com sucesso!");
            } else {
                dao.inserir(model);
                mostrarAlerta("Sucesso", "Atividade criada com sucesso!");
            }

            voltarParaCronograma();

        } catch(Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Ocorreu um erro ao salvar a atividade.");
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        voltarParaCronograma();
    }

    private void voltarParaCronograma() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/cronograma-lista.fxml"));
            Parent listaView = loader.load();

            // Navegação Segura: Procura o StackPane do Dashboard
            // (Mesma lógica que usamos no CronogramaListaController)
            StackPane dashboardStack = (StackPane) campoTituloAtividade.getScene().getRoot().lookup("#contentArea");
            
            if (dashboardStack != null) {
                dashboardStack.getChildren().clear();
                dashboardStack.getChildren().add(listaView);
            } else {
                // Fallback caso não ache o StackPane (código antigo)
                Parent root = campoTituloAtividade.getScene().getRoot();
                if (root instanceof BorderPane) {
                    ((BorderPane) root).setCenter(listaView);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para o cronograma.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}