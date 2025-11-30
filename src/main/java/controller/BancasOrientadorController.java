package controller;

import dao.BancaDAO;
import model.BancaModel;
import model.UsuarioModel;
import service.AuthenticationService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class BancasOrientadorController implements Initializable {

    @FXML private TableView<BancaModel> tabelaBancas;
    @FXML private TableColumn<BancaModel, Integer> colIdBanca;
    @FXML private TableColumn<BancaModel, LocalDate> colData;
    @FXML private TableColumn<BancaModel, String> colMembros;

    private BancaDAO bancaDAO = new BancaDAO();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarColunas();
        carregarDados();
    }

    private void configurarColunas() {
        colIdBanca.setCellValueFactory(new PropertyValueFactory<>("idBanca"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataDefesa"));
        colMembros.setCellValueFactory(new PropertyValueFactory<>("menbros"));
    }

    private void carregarDados() {
        UsuarioModel usuario = AuthenticationService.getUsuarioLogado();
        
        if (usuario != null) {
            try {
                // Chama aquele método especial que busca as bancas DOS ALUNOS desse orientador
                tabelaBancas.setItems(FXCollections.observableArrayList(
                    bancaDAO.listarPorOrientador(usuario.getIdUsuario())
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}