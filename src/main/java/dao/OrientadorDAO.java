package dao;

import model.OrientadorModel;
// import model.UsuarioModel;
import model.TccModel;

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

      public OrientadorModel buscarPorNome(String nome) {
            String sql = " SELECT  o.idUsuario, u.nome FROM orientador o   INNER JOIN usuario u ON u.idUsuario = o.idUsuario   WHERE u.nome = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, nome);
                  ResultSet rs = stmt.executeQuery();

                  if (rs.next()) {
                        OrientadorModel o = new OrientadorModel();
                        o.setIdUsuario(rs.getInt("idUsuario"));
                        o.setNome(rs.getString("nome"));
                        return o;
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      public TccModel buscarTccComOrientador(int idUsuario) {
            String sql = "SELECT t.*, u.nome AS nomeOrientador " +
                        "FROM tcc t " +
                        "INNER JOIN usuario u ON t.idOrientador = u.idUsuario " +
                        "WHERE t.idAluno = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idUsuario);
                  ResultSet rs = stmt.executeQuery();

                  if (rs.next()) {
                        TccModel tcc = new TccModel();
                        tcc.setIdTcc(rs.getInt("idTcc"));
                        tcc.setTitulo(rs.getString("titulo"));
                        tcc.setResumo(rs.getString("resumo"));
                        tcc.setEstado(rs.getString("estado"));
                        tcc.setDataCadastro(rs.getDate("dataCadastro").toLocalDate());
                        tcc.setIdAluno(rs.getInt("idAluno"));
                        tcc.setIdOrientador(rs.getInt("idOrientador"));
                        tcc.setIdBanca(rs.getInt("idBanca"));
                        tcc.setIdVersao(rs.getInt("idVersao"));

                        // armazenar o nome do orientador no próprio TccModel
                        tcc.setNomeOrientador(rs.getString("nomeOrientador"));

                        return tcc;
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      public OrientadorModel buscarPorUsuarioId(int idUsuario) {
            String sql = "SELECT * FROM orientador WHERE idUsuario = ?";
            OrientadorModel orientador = null;

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, idUsuario);
                  ResultSet rs = stmt.executeQuery();

                  if (rs.next()) {
                        orientador = new OrientadorModel();
                        orientador.setAreaPesquisa(rs.getString("areaPesquisa"));
                        orientador.setEstado(rs.getString("estado"));
                        orientador.setIdUsuario(rs.getInt("idUsuario"));
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return orientador;
      }

      public List<OrientadorModel> listarOrientadores() {
            List<OrientadorModel> lista = new ArrayList<>();

            String sql = "SELECT o.idUsuario, u.nome " +
                        "FROM orientador o " +
                        "INNER JOIN usuario u ON u.idUsuario = o.idUsuario";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        OrientadorModel o = new OrientadorModel();
                        o.setIdUsuario(rs.getInt("idUsuario"));
                        o.setNome(rs.getString("nome")); // <-- ESSENCIAL

                        lista.add(o);
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
                  stmt.setString(2, orientador.getEstado()); // Estado é o segundo ?
                  stmt.setInt(3, orientador.getIdUsuario()); // ID (WHERE) é o terceiro ?
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
