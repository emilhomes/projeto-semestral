package dao;
import model.CoordenadorModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;
public class CoordenadorDAO {
      
      
      public void inserir(CoordenadorModel coordenador) {
            String sql = "INSERT INTO coordenador(curso, idUsuario) VALUES ( ?, ? )";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, coordenador.getCurso());
                  stmt.setInt(2, coordenador.getIdUsuario());
                  stmt.executeUpdate();


            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public List<CoordenadorModel> listar() {
            List<CoordenadorModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM coordenador";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        CoordenadorModel u = new CoordenadorModel();
                        u.setCurso(rs.getString("curso"));
                        u.setIdUsuario(rs.getInt("idUsuario"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(CoordenadorModel coordenador) {
            String sql = "UPDATE coordenador SET curso = ? WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, coordenador.getCurso());
                  stmt.setInt(2, coordenador.getIdUsuario());
                  stmt.executeUpdate();

                  System.out.println("Coordenador atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int idUsuario) {
            String sql = "DELETE FROM coordenador WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idUsuario);
                  stmt.executeUpdate();
                  System.out.println("Coordenador removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
