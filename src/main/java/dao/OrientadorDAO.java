package dao;
import model.OrientadorModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;
public class OrientadorDAO {
     
      
      public void inserir(OrientadorModel orientador) {
            String sql = "INSERT INTO orientador(areaPesquisa, estado, idUsuario) VALUES (?, ?, ? )";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, orientador.getAreaPesquisa());
                  stmt.setString(2, orientador.getEstado());
                  stmt.setInt(3, orientador.getIdUsuario());
                  stmt.executeUpdate();

                  System.out.println("Orientador inserido com sucesso!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public List<OrientadorModel> listar() {
            List<OrientadorModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM orientador";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        OrientadorModel u = new OrientadorModel();
                        u.setAreaPesquisa(rs.getString("areaPesquisa"));
                        u.setEstado(rs.getString("estado"));
                        u.setIdUsuario(rs.getInt("idUsuario"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(OrientadorModel orientador) {
            String sql = "UPDATE orientador SET areaPesquisa = ?, estado = ? WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, orientador.getAreaPesquisa());
                  stmt.setInt(2, orientador.getIdUsuario());
                  stmt.setString(3, orientador.getEstado());
                  stmt.executeUpdate();

                  System.out.println("orientador atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int matricula) {
            String sql = "DELETE FROM orientador WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, matricula);
                  stmt.executeUpdate();
                  System.out.println("orientador removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
