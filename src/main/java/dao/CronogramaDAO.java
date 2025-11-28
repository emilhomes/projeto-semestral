package dao;

import model.CronogramaModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class CronogramaDAO {

      public void inserir(CronogramaModel cronograma) {
            String sql = "INSERT INTO cronograma(idAtividade, idOrientador, idAluno, idPrazo) VALUES (?, ?, ? , ?)";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setInt(1, cronograma.getIdAtividade());
                  stmt.setInt(2, cronograma.getIdOrientador());   
                  stmt.setInt(3, cronograma.getIdAluno());
                  stmt.setInt(4, cronograma.getIdPrazo());
                  stmt.executeUpdate();

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public List<CronogramaModel> listar() {
            List<CronogramaModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM cronograma";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        CronogramaModel u = new CronogramaModel();
                        u.setIdAluno(rs.getInt("idAluno"));
                        u.setIdAtividade(rs.getInt("idAtividade"));
                        u.setIdOrientador(rs.getInt("idOrientador"));
                        u.setIdPrazo(rs.getInt("idPrazo"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(CronogramaModel cronograma) {
            String sql = "UPDATE cronograma SET idAluno = ?, idAtividade = ?, idOrientador = ?, idPrazo = ? WHERE idCronograma = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {
                  stmt.setInt(1, cronograma.getIdAtividade());
                  stmt.setInt(2, cronograma.getIdAluno());
                  stmt.setInt(3, cronograma.getIdOrientador());
                  stmt.setInt(4, cronograma.getIdPrazo());
                  stmt.setInt(5, cronograma.getIdCronograma());
                  stmt.executeUpdate();

                  System.out.println("Cronograma atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int idCronograma) {
            String sql = "DELETE FROM cronograma WHERE idCronograma = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idCronograma);
                  stmt.executeUpdate();
                  System.out.println("Cronograma removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
