package controller;

import dao.OrientadorDAO;
import model.OrientadorModel;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PerfilOrientadorController implements Initializable {

    @FXML private Label lblNome;
    @FXML private Label lblEmail;
    @FXML private Label lblArea;
    @FXML private Label lblDepartamento;

    
    private OrientadorDAO orientadorDAO = new OrientadorDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();

        if (usuario != null) {
            
            lblNome.setText(usuario.getNome());
            lblEmail.setText(usuario.getEmailInstitucional());

            try {
                OrientadorModel orientador = orientadorDAO.buscarPorUsuarioId(usuario.getIdUsuario());
                
                if (orientador != null) {
                    
                    lblArea.setText(orientador.getAreaPesquisa());
                    
                } else {
                    lblArea.setText("Área não cadastrada");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void editarPerfil(ActionEvent event) {
        System.out.println("Botão editar clicado");
    }
}