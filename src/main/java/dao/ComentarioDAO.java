package dao;
import model.ComentarioModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class ComentarioDAO {
      
      public void inserir(ComentarioModel comentario) {
            String sql = "INSERT INTO comentario(usuario, conteudo, idTCC) VALUES (?, ?,?)";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, comentario.getUsuario());
                  stmt.setString(2, comentario.getConteudo());
                  stmt.setInt(3, comentario.getIdTCC());
                  stmt.executeUpdate();


            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public List<ComentarioModel> listarPorTcc(int idTCC) {
    List<ComentarioModel> lista = new ArrayList<>();
    String sql = "SELECT * FROM comentario WHERE idTCC = ?";

    try (Connection conn = ConexaoMySQL.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idTCC);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ComentarioModel c = new ComentarioModel();
            c.setIdComentario(rs.getInt("idComentario"));
            c.setConteudo(rs.getString("conteudo"));
            c.setUsuario(rs.getString("usuario"));
            c.setIdTCC(rs.getInt("idTCC"));
            lista.add(c);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

      public List<ComentarioModel> listar() {
            List<ComentarioModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM comentario";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        ComentarioModel u = new ComentarioModel();
                        u.setUsuario(rs.getString("usuario"));
                        u.setConteudo(rs.getString("conteudo"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(ComentarioModel comentario) {
            String sql = "UPDATE comentario SET usuario = ?, conteudo = ? WHERE idComentario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, comentario.getUsuario());
                  stmt.setString(2, comentario.getConteudo());
                  stmt.setInt(3, comentario.getIdComentario());
                  stmt.executeUpdate();

                  System.out.println("Comentario atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int idComentario) {
            String sql = "DELETE FROM comentario WHERE idComentario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idComentario);
                  stmt.executeUpdate();
                  System.out.println("Comentario removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
