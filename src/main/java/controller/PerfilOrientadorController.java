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

    // Precisamos do DAO para buscar os dados específicos
    private OrientadorDAO orientadorDAO = new OrientadorDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();

        if (usuario != null) {
            // 1. Preenche dados do Usuário (Genérico)
            lblNome.setText(usuario.getNome());
            lblEmail.setText(usuario.getEmailInstitucional());

            // 2. Busca dados do Orientador (Específico)
            try {
                OrientadorModel orientador = orientadorDAO.buscarPorUsuarioId(usuario.getIdUsuario());
                
                if (orientador != null) {
                    // AQUI ESTAVA FALTANDO: Atualiza a label da tela
                    lblArea.setText(orientador.getAreaPesquisa());
                    
                    // Se quiser usar o estado ou outro campo para departamento:
                    // lblDepartamento.setText(orientador.getEstado());
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
        // Lógica de editar...
        System.out.println("Botão editar clicado");
    }
}