package br.com.projetosemestral.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

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
        String tipoSelecionado = campoTipoUsuario.getValue();

        if (tipoSelecionado == null) {
            System.out.println("Por favor, selecione um tipo de usuário!");
            return;
        }

        System.out.println("Criando conta para: " + tipoSelecionado);

        // aqui colocar a lógica para cadastro de usuário
        // Exemplo: usuarioDAO.salvar(nome, email, senha, tipoSelecionado);
        
        if (tipoSelecionado.equals("Aluno")) {
            // carregarTela("DashboardAluno.fxml");
        } else if (tipoSelecionado.equals("Orientador")) {
            // carregarTela("DashboardOrientador.fxml");
        } else {
             // carregarTela("DashboardCoordenador.fxml");
        }
    }

    // --- AÇÃO DO LINK DE LOGIN ---
    @FXML
    void abrirTelaLogin(MouseEvent event) {
        System.out.println("Ir para tela de login...");
        // Lógica para trocar de cena aqui
    }
}