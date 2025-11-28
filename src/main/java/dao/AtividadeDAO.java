package dao;

import model.AtividadeModel;
import conexao.ConexaoMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDAO {

    public void inserir(AtividadeModel atividade) {
        String sql = "INSERT INTO atividade(descricao, dataIncio, dataFim, estado) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 1. Usa o método especial que junta Título + Descrição
            stmt.setString(1, atividade.getDescricaoBanco());

            // 2. Tratamento para evitar erro se a data for nula
            if (atividade.getDataInicio() != null) {
                stmt.setDate(2, java.sql.Date.valueOf(atividade.getDataInicio()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            if (atividade.getDataFim() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(atividade.getDataFim()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, atividade.getEstado());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AtividadeModel> listar() {
        List<AtividadeModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM atividade";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AtividadeModel u = new AtividadeModel();
                
                // Pega o ID (Importante para Edição/Exclusão)
                u.setIdAtividade(rs.getInt("idAtividade"));

                // 1. Usa o método especial que SEPARA Título e Descrição
                u.setDescricaoBanco(rs.getString("descricao"));

                // 2. Pega a data usando o nome da coluna do seu banco ('dataIncio')
                Date dataInicio = rs.getDate("dataIncio");
                if (dataInicio != null) {
                    u.setDataInicio(dataInicio.toLocalDate());
                }

                Date dataFim = rs.getDate("dataFim");
                if (dataFim != null) {
                    u.setDataFim(dataFim.toLocalDate());
                }

                u.setEstado(rs.getString("estado"));
                lista.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(AtividadeModel atividade) {
        // SQL ajustado para atualizar usando o ID
        String sql = "UPDATE atividade SET descricao = ?, dataIncio = ?, dataFim = ?, estado = ? WHERE idAtividade = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, atividade.getDescricaoBanco());

            if (atividade.getDataInicio() != null) {
                stmt.setDate(2, java.sql.Date.valueOf(atividade.getDataInicio()));
            } else {
                stmt.setNull(2, java.sql.Types.DATE);
            }

            if (atividade.getDataFim() != null) {
                stmt.setDate(3, java.sql.Date.valueOf(atividade.getDataFim()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }

            stmt.setString(4, atividade.getEstado());
            
            // Define o ID para saber qual linha atualizar
            stmt.setInt(5, atividade.getIdAtividade());

            stmt.executeUpdate();
            System.out.println("Atividade atualizada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int idAtividade) {
        String sql = "DELETE FROM atividade WHERE idAtividade = ?";

        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAtividade);
            stmt.executeUpdate();
            System.out.println("Atividade removida!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}