package dao;

import model.AtividadeModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexao.ConexaoMySQL;

public class AtividadeDAO {

      public void inserir(AtividadeModel atividade) {
            String sql = "INSERT INTO atividade(descricao, dataInicio, dataFim, estado) VALUES (?, ?, ?, ? )";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, atividade.getDescricao());
                  stmt.setDate(2, java.sql.Date.valueOf(atividade.getDataInicio()));
                  stmt.setDate(3, java.sql.Date.valueOf(atividade.getDataFim()));
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
                        u.setDescricao(rs.getString("descricao"));
                        Date dataInicio = rs.getDate("dataInicio");
                        if (dataInicio != null)
                              u.setDataInicio(dataInicio.toLocalDate());

                        Date dataFim = rs.getDate("dataFim");
                        if (dataFim != null)
                              u.setDataFim(dataFim.toLocalDate());

                        u.setEstado(rs.getString("estado"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(AtividadeModel atividade) {
            String sql = "UPDATE atividade SET descricao = ?, dataInicio = ?, dataFim = ?, estado = ?  WHERE idAtividade = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, atividade.getDescricao());
                  stmt.setDate(2, java.sql.Date.valueOf(atividade.getDataInicio()));
                  stmt.setDate(3, java.sql.Date.valueOf(atividade.getDataFim()));
                  stmt.setString(4, atividade.getEstado());
                  stmt.setInt(5, atividade.getIdAtividade());
                  stmt.executeUpdate();

                  System.out.println("Atividade atualizado!");

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
                  System.out.println("Atividade removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
