package dao;

import model.TccModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexao.ConexaoMySQL;

public class TccDAO {

      public boolean inserir(TccModel tcc) {
            String sql = "INSERT INTO tcc(titulo, resumo, estado, dataCadastro, idAluno, idBanca, idVersao, idOrientador) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, tcc.getTitulo());
                  stmt.setString(2, tcc.getResumo());
                  stmt.setString(3, tcc.getEstado());
                  stmt.setDate(4, java.sql.Date.valueOf(tcc.getDataCadastro()));
                  stmt.setInt(5, tcc.getIdAluno());
                  stmt.setInt(6, tcc.getIdBanca());
                  stmt.setInt(7, tcc.getIdVersao());
                  stmt.setInt(8, tcc.getIdOrientador());

                  stmt.executeUpdate();
                  return true;
            } catch (Exception e) {
                  e.printStackTrace();
                  return false;
            }
      }

      public List<TccModel> listar() {
            List<TccModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM tcc";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        TccModel u = new TccModel();
                        u.setTitulo(rs.getString("titulo"));
                        u.setResumo(rs.getString("resumo"));
                        u.setEstado(rs.getString("estado"));
                        Date dataCadastro = rs.getDate("dataCadastro");
                        if (dataCadastro != null)
                              u.setDataCadastro(dataCadastro.toLocalDate());
                        u.setIdAluno(rs.getInt("idAluno"));
                        u.setIdBanca(rs.getInt("idBanca"));
                        u.setIdOrientador(rs.getInt("idOrientador"));
                        u.setIdVersao(rs.getInt("idVersao"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(TccModel tcc) {
            String sql = "UPDATE tcc SET titulo = ?, resumo = ?, estado = ?, dataCadastro = ?, idAluno = ?, idBanca = ?, idVersao = ?, idOrientador  WHERE idTCC = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, tcc.getTitulo());
                  stmt.setString(2, tcc.getResumo());
                  stmt.setString(3, tcc.getEstado());
                  stmt.setDate(4, java.sql.Date.valueOf(tcc.getDataCadastro()));
                  stmt.setInt(5, tcc.getIdAluno());
                  stmt.setInt(6, tcc.getIdBanca());
                  stmt.setInt(7, tcc.getIdOrientador());
                  stmt.setInt(8, tcc.getIdVersao());
                  stmt.setInt(9, tcc.getIdTCC());
                  stmt.executeUpdate();

                  System.out.println("TCC atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int idTCC) {
            String sql = "DELETE FROM tcc WHERE idTCC = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idTCC);
                  stmt.executeUpdate();
                  System.out.println("TCC removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
