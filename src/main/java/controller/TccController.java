package controller;

import java.time.LocalDate;

import dao.AlunoDAO;
import dao.OrientadorDAO;
import dao.TccDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
// import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.AlunoModel;
import model.OrientadorModel;
import model.TccModel;
import model.UsuarioModel;
import service.AuthenticationService;

public class TccController {
    @FXML
    private StackPane contentArea;
    @FXML
    private TextField campoTitulo;
    @FXML
    private TextArea campoResumo;
    @FXML
    private ComboBox<String> campoEstado;
    @FXML
    private ComboBox<String> campoOrientador;


    @FXML
    public void initialize() {

         campoEstado.getItems().addAll("Em andamento", "Concluído", "Reprovado");
         campoEstado.setPromptText("Selecione o status do TCC");

         OrientadorDAO dao = new OrientadorDAO();
         for (OrientadorModel o : dao.listarOrientadores()) {
         campoOrientador.getItems().add(o.getNome());
         campoOrientador.setPromptText("Selecione o orientador");
         }
    }

    @FXML
    private void CriarTCC(ActionEvent event) {
        System.out.println("BOTÃO CLICADO");
        carregarTela("tela-aluno/com-tcc.fxml");
        String titulo = campoTitulo.getText();
        String resumo = campoResumo.getText();
        String estado = campoEstado.getValue();
        String nomeOrientador = campoOrientador.getValue();

        if (titulo.isEmpty() || resumo.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos", Alert.AlertType.ERROR);
            return;
        }

        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        if (usuario == null) {
            mostrarAlerta("Erro", "Nenhum usuário logado!", Alert.AlertType.ERROR);
            return;
        }

        // Buscar aluno logado
        AlunoDAO alunoDAO = new AlunoDAO();
        AlunoModel aluno = alunoDAO.buscarPorUsuarioId(usuario.getIdUsuario());

        if (aluno == null) {
            mostrarAlerta("Erro", "Dados do aluno não encontrados!", Alert.AlertType.ERROR);
            return;
        }

        // Buscar orientador pelo NOME
        OrientadorDAO orientadorDAO = new OrientadorDAO();
        OrientadorModel orientador = orientadorDAO.buscarPorNome(nomeOrientador);

        if (orientador == null) {
            mostrarAlerta("Erro", "Orientador não encontrado!", Alert.AlertType.ERROR);
            return;
        }

        // Criar o TCC
        TccModel tcc = new TccModel();
        tcc.setTitulo(titulo);
        tcc.setResumo(resumo);
        tcc.setEstado(estado);
        tcc.setDataCadastro(LocalDate.now());
        tcc.setIdAluno(aluno.getIdUsuario());
        tcc.setIdOrientador(orientador.getIdUsuario());
        tcc.setIdBanca(1); // se ainda não tiver
        tcc.setIdVersao(1); // se ainda não tiver

        // Salvar no banco
        TccDAO dao = new TccDAO();
        boolean sucesso = dao.inserir(tcc); // inserir deve retornar boolean

        if (sucesso) {
            mostrarAlerta("Sucesso", "TCC cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();
        } else {
            mostrarAlerta("Erro", "Falha ao salvar o TCC.", Alert.AlertType.ERROR);
        }
        
    }

     @FXML
    private void carregarTela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            Stage stage = new Stage(); 
            stage.setScene(new Scene(root));
            stage.show();

            Stage atual = (Stage) contentArea.getScene().getWindow();
            atual.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // @FXML
    // private void carregarTela(String fxml) {
    //     try {
    //         Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));
    //         Stage stage = new Stage();
    //         stage.setScene(new Scene(root));
    //         stage.show();

    //         Stage atual = (Stage) contentArea.getScene().getWindow();
    //         atual.close();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    private void limparCampos() {
        campoTitulo.clear();
        campoResumo.clear();
        campoEstado.setValue(null);
        campoOrientador.setValue(null);
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.show();
    }
}
