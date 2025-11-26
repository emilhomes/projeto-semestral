package dao;
import model.AlunoModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import conexao.ConexaoMySQL;

public class AlunoDAO {
      
      public void inserir(AlunoModel aluno) {
            String sql = "INSERT INTO aluno(matricula, curso, idUsuario) VALUES (?, ?, ? )";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, aluno.getMatricula());
                  stmt.setString(2, aluno.getCurso());
                  stmt.setInt(3, aluno.getIdUsuario());
                  stmt.executeUpdate();

                  System.out.println("Aluno inserido com sucesso!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public List<AlunoModel> listar() {
            List<AlunoModel> lista = new ArrayList<>();
            String sql = "SELECT * FROM aluno";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery()) {

                  while (rs.next()) {
                        AlunoModel u = new AlunoModel();
                        u.setMatricula(rs.getInt("matricula"));
                        u.setCurso(rs.getString("curso"));
                        u.setIdUsuario(rs.getInt("idUsuario"));
                        lista.add(u);
                  }

            } catch (Exception e) {
                  e.printStackTrace();
            }

            return lista;
      }

      public void atualizar(AlunoModel aluno) {
            String sql = "UPDATE aluno SET curso = ?, idUsuario = ? WHERE matricula = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setString(1, aluno.getCurso());
                  stmt.setInt(2, aluno.getIdUsuario());
                  stmt.setInt(3, aluno.getMatricula());
                  stmt.executeUpdate();

                  System.out.println("Aluno atualizado!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }

      public void deletar(int matricula) {
            String sql = "DELETE FROM aluno WHERE matricula = ?";

            try (Connection conn = ConexaoMySQL.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sql)) {

                  stmt.setInt(1, matricula);
                  stmt.executeUpdate();
                  System.out.println("Aluno removido!");

            } catch (Exception e) {
                  e.printStackTrace();
            }
      }
}