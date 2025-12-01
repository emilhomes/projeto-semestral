package controller;

import dao.ComentarioDAO;
import model.ComentarioModel;
import model.TccModel;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class DetalhesTccController implements Initializable {

    @FXML private Label lblTituloTcc;
    @FXML private Label lblNomeAluno;
    
    
    @FXML private VBox listaComentarios; 
    @FXML private TextArea txtComentario;

    private TccModel tccSelecionado;
    private ComentarioDAO comentarioDAO = new ComentarioDAO();
    private UsuarioModel usuarioLogado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        
        this.usuarioLogado = AuthenticationService.getUsuarioLogado();
    }

    
    public void setTcc(TccModel tcc) {
        this.tccSelecionado = tcc;
        
        lblTituloTcc.setText(tcc.getTitulo());
        
        if (tcc.getNomeAluno() != null) {
            lblNomeAluno.setText("Aluno: " + tcc.getNomeAluno());
        } else {
            lblNomeAluno.setText("Aluno ID: " + tcc.getIdAluno());
        }
        
        
        carregarComentarios();
    }

    private void carregarComentarios() {
        if (tccSelecionado == null) return;

       
        listaComentarios.getChildren().clear();
        
      
        List<ComentarioModel> lista = comentarioDAO.listarPorTcc(tccSelecionado.getIdTCC());

       
        for (ComentarioModel c : lista) {
            adicionarBalao(c);
        }
    }

    private void adicionarBalao(ComentarioModel c) {
        VBox balao = new VBox(5);
        balao.setPadding(new Insets(10));
        
       
        if (usuarioLogado != null && c.getUsuario().equals(usuarioLogado.getNome())) {
           
            balao.setStyle("-fx-background-color: #E3F2FD; -fx-background-radius: 8; -fx-border-color: #90CAF9; -fx-border-radius: 8;");
        } else {
           
            balao.setStyle("-fx-background-color: white; -fx-background-radius: 8; -fx-border-color: #CCCCCC; -fx-border-radius: 8;");
        }

        
        String dataStr = (c.getData() != null) ? c.getData().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")) : "";
        Label autorData = new Label(c.getUsuario() + " - " + dataStr);
        autorData.setFont(Font.font("System", FontWeight.BOLD, 12));
        
        
        Label texto = new Label(c.getConteudo());
        texto.setWrapText(true);

        balao.getChildren().addAll(autorData, texto);
        listaComentarios.getChildren().add(balao);
    }

    @FXML
    void enviarComentario(ActionEvent event) {
        
        if (tccSelecionado != null && !txtComentario.getText().isEmpty()) {
            try {
                ComentarioModel novo = new ComentarioModel();
                novo.setIdTCC(tccSelecionado.getIdTCC());
                
                
                String nomeAutor = (usuarioLogado != null) ? usuarioLogado.getNome() : "Anônimo";
                novo.setUsuario(nomeAutor);
                
                novo.setConteudo(txtComentario.getText());
                novo.setData(LocalDateTime.now());

               
                comentarioDAO.inserir(novo);
                
                
                txtComentario.clear();
                carregarComentarios(); 

            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Falha ao enviar comentário.");
            }
        }
    }

    @FXML
    void voltar(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela-orientador/tccs-orientados.fxml"));
            Parent root = loader.load();
            
            StackPane dashboardStack = (StackPane) lblTituloTcc.getScene().getRoot().lookup("#contentArea");
            if (dashboardStack != null) {
                dashboardStack.getChildren().clear();
                dashboardStack.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
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