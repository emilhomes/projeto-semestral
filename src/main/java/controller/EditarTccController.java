package controller;

import dao.TccDAO;
import model.TccModel;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;


public class EditarTccController {
    
    @FXML private TextField campoTitulo;
    @FXML private TextArea campoResumo;
    @FXML private Button btnSalvar;

    private TccModel tccAtual;

    @FXML
    public void initialize() {
        carregarTcc();

        
    }

    private void carregarTcc() {
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        TccDAO dao = new TccDAO();

        tccAtual = dao.buscarTccPorUsuarioId(usuario.getIdUsuario());

        if (tccAtual != null) {
            campoTitulo.setText(tccAtual.getTitulo());
            campoResumo.setText(tccAtual.getResumo());
        }
    }

  
      @FXML
    private void salvarAlteracoes(ActionEvent event) {
        if (tccAtual == null) {
            mostrarAlerta("Erro", "Nenhum TCC encontrado para atualizar.");
            return;
        }

        String titulo = campoTitulo.getText();
        String resumo = campoResumo.getText();

        if (titulo.isEmpty() || resumo.isEmpty() ) {
            mostrarAlerta("Campos vazios", "Preencha todos os campos antes de salvar.");
            return;
        }

        tccAtual.setTitulo(titulo);
        tccAtual.setResumo(resumo);

        TccDAO dao = new TccDAO();
        boolean sucesso = dao.atualizar(tccAtual);

        if (sucesso) {
            mostrarAlerta("Sucesso", "Alterações salvas com sucesso!");
        } else {
            mostrarAlerta("Erro", "Ocorreu um erro ao salvar o TCC.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void trocarTela(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            Stage atual = (Stage) campoTitulo.getScene().getWindow();
            atual.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPerfil() {
        trocarTela("tela-aluno/perfil.fxml");
    }

    @FXML
    private void abrirMeuTCC() {
        trocarTela("tela-aluno/com-tcc.fxml");
    }

    @FXML
    private void abrirCronograma() {
        trocarTela("tela-aluno/cronograma.fxml");
    }

}