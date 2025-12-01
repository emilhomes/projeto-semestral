package controller;

import dao.AlunoDAO;
import dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable; // Importante implementar Initializable
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AlunoModel;
import model.UsuarioModel;
import service.AuthenticationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarPerfilController implements Initializable {

    @FXML private TextField inputNome;
    @FXML private TextField inputMatricula;
    @FXML private TextField inputEmail;
    @FXML private TextField inputCurso;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    
    private UsuarioModel usuario;
    private AlunoModel aluno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarUsuario();
    }

    private void carregarUsuario() {
        try {
            usuario = AuthenticationService.getUsuarioLogado();
            
            if (usuario != null) {
                // Busca dados complementares do aluno
                aluno = alunoDAO.buscarPorUsuarioId(usuario.getIdUsuario());

                // Preenche a tela
                inputNome.setText(usuario.getNome());
                inputEmail.setText(usuario.getEmailInstitucional());

                if (aluno != null) {
                    inputMatricula.setText(String.valueOf(aluno.getMatricula()));
                    inputCurso.setText(aluno.getCurso());
                }
            }
        } catch (Exception e) {
            mostrarErro("Erro ao carregar dados do aluno.", e);
        }
    }

    @FXML
    private void salvarPerfil(ActionEvent event) {
        try {
            // 1. Atualiza dados do Usuário (Nome/Email)
            usuario.setNome(inputNome.getText());
            usuario.setEmailInstitucional(inputEmail.getText());
            
            usuarioDAO.atualizar(usuario); 

            // 2. Atualiza dados do Aluno (Matrícula/Curso)
            if (aluno != null) {
                
                // --- CORREÇÃO AQUI ---
                // Convertemos o texto do campo para número inteiro
                try {
                    int novaMatricula = Integer.parseInt(inputMatricula.getText());
                    aluno.setMatricula(novaMatricula);
                } catch (NumberFormatException e) {
                    mostrarErro("A matrícula deve conter apenas números!", null);
                    return; // Para a execução se não for número
                }
                // ---------------------
                
                aluno.setCurso(inputCurso.getText());
                
                alunoDAO.atualizar(aluno);
            }

            // 3. Atualiza a sessão
            AuthenticationService.setUsuarioLogado(usuario);

            // 4. Sucesso
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Perfil atualizado com sucesso!");
            alert.showAndWait();

            voltarParaDashboard();

        } catch (Exception e) {
            mostrarErro("Erro ao salvar perfil.", e);
        }
    }

    @FXML
    private void cancelarEdicao(ActionEvent event) {
        voltarParaDashboard();
    }

    private void voltarParaDashboard() {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-aluno/dashboard-aluno.fxml"));
            Parent root = loader.load();
            
            
            Stage stage = (Stage) inputNome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true); 
            stage.show();
            
        } catch (IOException e) {
            mostrarErro("Erro ao voltar para o dashboard: ", e);
        }
    }

    private void mostrarErro(String msg, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(msg);
        if (e != null) alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}