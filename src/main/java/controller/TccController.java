package controller;

import dao.AlunoDAO;
import dao.OrientadorDAO;
import dao.TccDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import model.AlunoModel;
import model.OrientadorModel;
import model.TccModel;
import model.UsuarioModel;
import service.AuthenticationService;

import java.io.IOException;
import java.time.LocalDate;

public class TccController {

    @FXML private TextField campoTitulo;
    @FXML private TextArea campoResumo;
    @FXML private ComboBox<String> campoEstado;
    @FXML private ComboBox<String> campoOrientador;

    // DAOs
    private OrientadorDAO orientadorDAO = new OrientadorDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    private TccDAO tccDAO = new TccDAO();

    @FXML
    public void initialize() {
        // Inicializa os combos
        campoEstado.getItems().addAll("Em andamento", "Concluído", "Reprovado");
        campoEstado.setPromptText("Selecione o status");

        // Preenche o combo de orientadores buscando do banco
        for (OrientadorModel o : orientadorDAO.listarOrientadores()) {
            campoOrientador.getItems().add(o.getNome());
        }
        campoOrientador.setPromptText("Selecione o orientador");
    }

    @FXML
    private void CriarTCC(ActionEvent event) {
        // 1. Validação dos Campos
        if (campoTitulo.getText().isEmpty() || campoResumo.getText().isEmpty() || 
            campoEstado.getValue() == null || campoOrientador.getValue() == null) {
            mostrarAlerta("Campos Obrigatórios", "Por favor, preencha todos os campos.", Alert.AlertType.WARNING);
            return;
        }

        // 2. Obter Usuário Logado
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        if (usuario == null) {
            mostrarAlerta("Erro", "Nenhum usuário logado!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // 3. Buscar dados do Aluno (pelo ID do usuário logado)
            AlunoModel aluno = alunoDAO.buscarPorUsuarioId(usuario.getIdUsuario());
            if (aluno == null) {
                mostrarAlerta("Erro", "Cadastro de aluno incompleto.", Alert.AlertType.ERROR);
                return;
            }

            // 4. Buscar dados do Orientador (pelo Nome selecionado)
            String nomeOrientador = campoOrientador.getValue();
            OrientadorModel orientador = orientadorDAO.buscarPorNome(nomeOrientador);
            if (orientador == null) {
                mostrarAlerta("Erro", "Orientador não encontrado.", Alert.AlertType.ERROR);
                return;
            }

            // 5. Criar o Modelo TCC
            TccModel tcc = new TccModel();
            tcc.setTitulo(campoTitulo.getText());
            tcc.setResumo(campoResumo.getText());
            tcc.setEstado(campoEstado.getValue());
            tcc.setDataCadastro(LocalDate.now());
            
            // IDs de relacionamento
            tcc.setIdAluno(aluno.getIdUsuario()); // Conforme sua regra de banco
            tcc.setIdOrientador(orientador.getIdUsuario());
            
            // Valores padrão para novos TCCs
            tcc.setIdBanca(0); // ou tratar null no DAO
            tcc.setIdVersao(0); // ou tratar null no DAO

            // 6. Salvar no Banco
            tccDAO.inserir(tcc);
            
            mostrarAlerta("Sucesso", "TCC cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            
            // 7. Navegar para a tela de visualização (Com TCC)
            navegarPara("tela-aluno/com-tcc.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao salvar o TCC: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Método de navegação corrigido (Mantém o Dashboard)
    private void navegarPara(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlPath));
            Parent novaTela = loader.load();

            // Busca o StackPane do Dashboard pai
            StackPane dashboardStack = (StackPane) campoTitulo.getScene().getRoot().lookup("#contentArea");
            
            if (dashboardStack != null) {
                dashboardStack.getChildren().clear();
                dashboardStack.getChildren().add(novaTela);
            } else {
                System.out.println("Erro: Não foi possível localizar o painel principal do dashboard.");
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }
    
    // Removi os métodos abrirPerfil/abrirCronograma pois o Menu Lateral do Dashboard já cuida disso.
}