package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.AlunoModel;
import model.CoordenadorModel;
import model.OrientadorModel;
import model.UsuarioModel;

import java.net.URL;
import java.util.ResourceBundle;

import dao.AlunoDAO;
import dao.CoordenadorDAO;
import dao.OrientadorDAO;
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

    @FXML
    private Label labelAreaPesquisa;

    @FXML
    private TextField campoAreaPesquisa;

    @FXML
    private TextField campoMatricula;

    @FXML
    private TextField campoDepartamento;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
        campoTipoUsuario.getItems().addAll("Aluno", "Orientador", "Coordenador");
        campoTipoUsuario.setPromptText("Selecione o perfil");

        
        labelAreaPesquisa.setVisible(false);
        campoAreaPesquisa.setVisible(false);

        
        campoTipoUsuario.valueProperty().addListener((obs, oldV, newV) -> {
            boolean isOrientador = "Orientador".equals(newV);

            labelAreaPesquisa.setVisible(isOrientador);
            campoAreaPesquisa.setVisible(isOrientador);

            if (!isOrientador) {
                campoAreaPesquisa.clear();
            }
        });
    }

    @FXML
    void criarConta(ActionEvent event) {
        System.out.println("BOTÃO CLICADO");

        String nome = campoNome.getText();
        String emailInstitucional = campoEmail.getText();
        String senha = campoSenha.getText();
        String tipoSelecionado = campoTipoUsuario.getValue();
        int matricula = Integer.parseInt(campoMatricula.getText());
        String departamento = campoDepartamento.getText();
        String areaPesquisa = campoAreaPesquisa.getText();

        if (nome.isEmpty() || emailInstitucional.isEmpty() || senha.isEmpty() || tipoSelecionado == null) {
            System.out.println("Preencha todos os campos!");
            return;
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNome(nome);
        usuario.setEmailInstitucional(emailInstitucional);
        usuario.setSenha(senha);
        usuario.setTipoUsuario(tipoSelecionado);

       
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        int idUsuario = usuarioDAO.inserirRetornandoID(usuario);
        System.out.println("Usuário cadastrado com sucesso!");

        if (tipoSelecionado.equals("Aluno")) {
            AlunoModel aluno = new AlunoModel();
            aluno.setMatricula(matricula);
            aluno.setTcc(null);
            aluno.setCurso(departamento);
            aluno.setIdUsuario(idUsuario);

           
            AlunoDAO alunoDAO = new AlunoDAO();
            alunoDAO.inserir(aluno);
            System.out.println("Aluno cadastrado com sucesso!");
        }

        if (tipoSelecionado.equals("Orientador")) {
            OrientadorModel orientador = new OrientadorModel();
            orientador.setAreaPesquisa(areaPesquisa);
            orientador.setEstado("Disponivel");
            orientador.setIdUsuario(idUsuario);

            
            OrientadorDAO orientadorDAO = new OrientadorDAO();
            orientadorDAO.inserir(orientador);
            System.out.println("Orientador cadastrado com sucesso!");
        }

         if (tipoSelecionado.equals("Coordenador")) {
         CoordenadorModel coordenador = new CoordenadorModel();
         
         coordenador.setCurso(departamento);
         coordenador.setIdUsuario(idUsuario);

         
         CoordenadorDAO coordenadorDAO = new CoordenadorDAO();
         coordenadorDAO.inserir(coordenador);
         System.out.println("Cordenador cadastrado com sucesso!");
         }

    
    if(tipoSelecionado.equals("Aluno"))

    {
        carregarTela("tela-aluno/dashboard-aluno.fxml", event);

    }else if(tipoSelecionado.equals("Orientador"))
    {
        carregarTela("tela-orientador/dashboard-orientador.fxml", event);

    }else if(tipoSelecionado.equals("Coordenador"))
    {
        carregarTela("tela-coordenador/dashboard-coordenador.fxml", event);

    }else
    {
        System.out.println("Tipo de usuário inválido!");
    }
    }

  private void carregarTela(String fxml,ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/view/" + fxml));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
   
    @FXML
    void abrirTelaLogin(MouseEvent event) {
        System.out.println("Ir para tela de login...");
  
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}