package controller;

import dao.OrientadorDAO;
import dao.UsuarioDAO; 
import model.OrientadorModel;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarPerfilOrientadorController implements Initializable {

    @FXML private TextField campoNome;
    @FXML private TextField campoEmail;
    @FXML private TextField campoArea;
    @FXML private PasswordField campoNovaSenha;

    private UsuarioModel usuarioLogado;
    private OrientadorDAO orientadorDAO = new OrientadorDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO(); 

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioLogado = AuthenticationService.getUsuarioLogado();

        if (usuarioLogado != null) {
           
            campoNome.setText(usuarioLogado.getNome());
            campoEmail.setText(usuarioLogado.getEmailInstitucional());

            
            try {
                OrientadorModel orientador = orientadorDAO.buscarPorUsuarioId(usuarioLogado.getIdUsuario());
                if (orientador != null) {
                    campoArea.setText(orientador.getAreaPesquisa());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void salvar(ActionEvent event) {
        if (campoNome.getText().isEmpty() || campoEmail.getText().isEmpty()) {
            mostrarAlerta("Erro", "Nome e E-mail são obrigatórios.");
            return;
        }

        try {
            
            usuarioLogado.setNome(campoNome.getText());
            usuarioLogado.setEmailInstitucional(campoEmail.getText());
            
            if (!campoNovaSenha.getText().isEmpty()) {
                usuarioLogado.setSenha(campoNovaSenha.getText());
            }
            
            usuarioDAO.atualizar(usuarioLogado); 

            
            OrientadorModel orientador = new OrientadorModel();
            orientador.setIdUsuario(usuarioLogado.getIdUsuario());
            orientador.setAreaPesquisa(campoArea.getText());
            orientador.setEstado("Ativo"); 

            orientadorDAO.atualizar(orientador); 

           
            AuthenticationService.setUsuarioLogado(usuarioLogado);

            mostrarAlerta("Sucesso", "Perfil atualizado!");
            voltarParaDashboard();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao salvar alterações.");
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        voltarParaDashboard();
    }

    private void voltarParaDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-orientador/dashboard-orientador.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) campoNome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao voltar para o dashboard.");
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