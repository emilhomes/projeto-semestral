package dao;

import model.UsuarioModel;
import utils.Criptografia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class UsuarioDAO {

      public int inserirRetornandoID(UsuarioModel usuario) {
            String sql = "INSERT INTO usuario (nome, emailInstitucional, tipoUsuario, senha) VALUES (?, ?, ?, ?)";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                  stmt.setString(1, usuario.getNome());
                  stmt.setString(2, usuario.getEmailInstitucional());
                  stmt.setString(3, usuario.getTipoUsuario());
                  stmt.setString(4, Criptografia.md5(usuario.getSenha()));

                  stmt.executeUpdate();

                  ResultSet rs = stmt.getGeneratedKeys();
                  if (rs.next()) {
                        return rs.getInt(1);
                  }

                  System.out.println("Usuário inserido, mas ID não retornado.");
                  return -1;

            } catch (Exception e) {
                  e.printStackTrace();
                  return -1;
            }
      }

      public UsuarioModel buscarPorUsuarioId(int idUsuario) {
            String sql = "SELECT * FROM usuario WHERE idUsuario = ?";
            UsuarioModel usuario = null;

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idUsuario);
                  ResultSet rs = stmt.executeQuery();

                  if (rs.next()) {
                        usuario = new UsuarioModel();
                        usuario.setNome(rs.getString("nome"));
                        usuario.setEmailInstitucional(rs.getString("emailInstitucional"));
                        usuario.setSenha(rs.getString("senha"));
                        usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return usuario;
      }

      public UsuarioModel buscarPorEmailESenha(String email, String senha) {
            String sql = "SELECT * FROM usuario WHERE emailInstitucional = ? AND senha = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, email);
                  stmt.setString(2, Criptografia.md5(senha));

                  ResultSet rs = stmt.executeQuery();

                  if (rs.next()) {
                        UsuarioModel usuario = new UsuarioModel();
                        usuario.setIdUsuario(rs.getInt("idUsuario"));
                        usuario.setNome(rs.getString("nome"));
                        usuario.setEmailInstitucional(rs.getString("emailInstitucional"));
                        usuario.setTipoUsuario(rs.getString("tipoUsuario"));
                        return usuario;
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      public List<UsuarioModel> listar() {
            List<UsuarioModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM usuario";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        UsuarioModel u = new UsuarioModel();
                        u.setIdUsuario(rs.getInt("idUsuario"));
                        u.setNome(rs.getString("nome"));
                        u.setEmailInstitucional(rs.getString("emailInstitucional"));
                        u.setTipoUsuario(rs.getString("tipoUsuario"));
                        u.setSenha(rs.getString("senha"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(UsuarioModel usuario) {
            String sql = "UPDATE usuario SET nome = ?, emailInstitucional = ? WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, usuario.getNome());
                  stmt.setString(2, usuario.getEmailInstitucional());
                  stmt.setInt(3, usuario.getIdUsuario());
                  stmt.executeUpdate();

                  System.out.println("Usuário atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int id) {
            String sql = "DELETE FROM usuario WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, id);
                  stmt.executeUpdate();
                  System.out.println("Usuário removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}
