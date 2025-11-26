package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.UsuarioModel;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UsuarioDAO;

public class CadastroController implements Initializable {

    @FXML
    private TextField campoNome;

    @FXML
    private ComboBox<String> campoTipoUsuario; 

    @FXML
    private TextField campoEmail;

    @FXML
    private PasswordField campoSenha;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // adicionando itens do combobox
        campoTipoUsuario.getItems().addAll("Aluno", "Orientador", "Coordenador");
        
        campoTipoUsuario.setPromptText("Selecione o perfil"); 
    }


    @FXML
    void criarConta(ActionEvent event) {
System.out.println("BOTÃO CLICADO");

    String nome = campoNome.getText();
    String emailInstitucional = campoEmail.getText();
    String senha = campoSenha.getText();
    String tipoSelecionado = campoTipoUsuario.getValue();

    if (nome.isEmpty() || emailInstitucional.isEmpty() || senha.isEmpty() || tipoSelecionado == null) {
        System.out.println("Preencha todos os campos!");
        return;
    }

    UsuarioModel usuario = new UsuarioModel();
    usuario.setNome(nome);
    usuario.setEmailInstitucional(emailInstitucional);
    usuario.setSenha(senha);
    usuario.setTipoUsuario(tipoSelecionado);

    // Salvar no banco
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    usuarioDAO.inserir(usuario);
        
    
    
    }

    
        // if (tipoSelecionado.equals("Aluno")) {
        //     // carregarTela("DashboardAluno.fxml");
        // } else if (tipoSelecionado.equals("Orientador")) {
        //     // carregarTela("DashboardOrientador.fxml");
        // } else {
        //      // carregarTela("DashboardCoordenador.fxml");
        // }

    // --- AÇÃO DO LINK DE LOGIN ---
    @FXML
    void abrirTelaLogin(MouseEvent event) {
        System.out.println("Ir para tela de login...");
        // Lógica para trocar de cena aqui
    }
}