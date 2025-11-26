package dao;

import model.UsuarioModel;
import utils.Criptografia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class UsuarioDAO {

      public void inserir(UsuarioModel usuario) {
            String sql = "INSERT INTO usuario (nome, email, tipo, senha) VALUES (?, ?, ?, ? )";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, usuario.getNome());
                  stmt.setString(2, usuario.getEmail());
                  stmt.setString(3, usuario.getTipo());
                  stmt.setString(4, Criptografia.md5(usuario.getSenha()));
                  stmt.executeUpdate();

                  System.out.println("Usuário inserido com sucesso!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
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
                        u.setEmail(rs.getString("email"));
                        u.setTipo(rs.getString("tipo"));
                        u.setSenha(rs.getString("senha"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(UsuarioModel usuario) {
            String sql = "UPDATE usuario SET nome = ?, email = ?, tipo = ?, senha = ? WHERE idUsuario = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, usuario.getNome());
                  stmt.setString(2, usuario.getEmail());
                  stmt.setString(3, usuario.getTipo());
                  stmt.setString(4, usuario.getSenha());
                  stmt.setInt(5, usuario.getIdUsuario());
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
