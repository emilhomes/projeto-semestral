package controller;

import dao.AlunoDAO;
import dao.OrientadorDAO;
import dao.TccDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import model.AlunoModel;
import model.OrientadorModel;
import model.TccModel;
import model.UsuarioModel;
import service.AuthenticationService;

import java.io.IOException;
import java.time.LocalDate;

public class TccController {

    @FXML private TextField campoTitulo;
    @FXML private TextArea campoResumo;
    @FXML private ComboBox<String> campoEstado;
    @FXML private ComboBox<String> campoOrientador;

    private OrientadorDAO orientadorDAO = new OrientadorDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private TccDAO tccDAO = new TccDAO();

    @FXML
    public void initialize() {
        
        campoEstado.getItems().addAll("Em andamento", "Concluído", "Reprovado");
        campoEstado.setPromptText("Selecione o status");

        for (OrientadorModel o : orientadorDAO.listarOrientadores()) {
            campoOrientador.getItems().add(o.getNome());
        }
        campoOrientador.setPromptText("Selecione o orientador");
    }

    @FXML
    private void CriarTCC(ActionEvent event) {
        
        if (campoTitulo.getText().isEmpty() || campoResumo.getText().isEmpty() || 
            campoEstado.getValue() == null || campoOrientador.getValue() == null) {
            mostrarAlerta("Campos Obrigatórios", "Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }
       
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        if (usuario == null) {
            mostrarAlerta("Erro", "Nenhum usuário logado!", Alert.AlertType.ERROR);
            return;
        }

        try {
          
             AlunoModel aluno = alunoDAO.buscarPorUsuarioId(usuario.getIdUsuario());
            if (aluno == null) {
                 mostrarAlerta("Erro", "Cadastro de aluno incompleto.", Alert.AlertType.ERROR);
                 return;
             }

           
            String nomeOrientador = campoOrientador.getValue();
            OrientadorModel orientador = orientadorDAO.buscarPorNome(nomeOrientador);
            if (orientador == null) {
                mostrarAlerta("Erro", "Orientador não encontrado.", Alert.AlertType.ERROR);
                return;
            }

        
            TccModel tcc = new TccModel();
            tcc.setTitulo(campoTitulo.getText());
            tcc.setResumo(campoResumo.getText());
            tcc.setEstado(campoEstado.getValue());
            tcc.setDataCadastro(LocalDate.now());
            tcc.setIdAluno(aluno.getIdUsuario()); 
            tcc.setIdOrientador(orientador.getIdUsuario());
            
            tccDAO.inserir(tcc);
            
            mostrarAlerta("Sucesso", "TCC cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            
            
            navegarPara("tela-aluno/com-tcc.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao salvar o TCC: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    
    private void navegarPara(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlPath));
            Parent novaTela = loader.load();

         
            StackPane dashboardStack = (StackPane) campoTitulo.getScene().getRoot().lookup("#contentArea");
            
            if (dashboardStack != null) {
                dashboardStack.getChildren().clear();
                dashboardStack.getChildren().add(novaTela);
            } else {
                System.out.println("Erro: Não foi possível localizar o painel principal do dashboard.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
    
    
}