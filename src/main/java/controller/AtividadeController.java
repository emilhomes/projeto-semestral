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
import model.AtividadeModel;

public class AtividadeController {
    @FXML 
    private TextField campoTituloAtividade;

    @FXML
    private TextArea campoDescricaoAtividade;

    @FXML
    private DatePicker campoDataLimite;

    @FXML
    private Button btnSalvarAtividade;

    @FXML
    private Button btnCancelar;

    //Instância DAO
    private AtividadeDAO dao = new AtividadeDAO();

    @FXML
    void salvar(ActionEvent event) {
        //Validação
        if(campoTituloAtividade.getText().isEmpty() || campoDataLimite.getValue() == null) {
            mostrarAlerta("Campos Obrigatórios", "Por favor, preencha o Título e a Data Limite.");
            return;
        }

        try {
            //Criar objeto
            AtividadeModel novaAtividade = new AtividadeModel();

            novaAtividade.setTitulo(campoTituloAtividade.getText());
            novaAtividade.setDescricao(campoDescricaoAtividade.getText());
            
            novaAtividade.setDataInicio(LocalDate.now()); 
            novaAtividade.setDataFim(campoDataLimite.getValue());
            novaAtividade.setEstado("Pendente"); 

            dao.inserir(novaAtividade);

            mostrarAlerta("Sucesso", "Atividade adicionada ao cronograma!");
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
            // Carrega o arquivo da LISTA (Tabela)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/cronograma-lista.fxml"));
            Parent listaView = loader.load();

            BorderPane dashboardRoot = (BorderPane) campoTituloAtividade.getScene().getRoot();
            
            dashboardRoot.setCenter(listaView);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para o cronograma: Verifique o caminho do FXML.");
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
