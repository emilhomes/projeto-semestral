package dao;

import model.PrazoInstitucionalModel;
import conexao.ConexaoMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrazoInstitucionalDAO {

    public void inserir(PrazoInstitucionalModel prazo) {

        String sql = "INSERT INTO prazoinstitucional (dataInicio, dataFinal, descricao, idCoordenador) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (prazo.getDataInicio() != null)
                stmt.setDate(1, Date.valueOf(prazo.getDataInicio()));
            else
                stmt.setNull(1, Types.DATE);

            if (prazo.getDataFinal() != null)
                stmt.setDate(2, Date.valueOf(prazo.getDataFinal()));
            else
                stmt.setNull(2, Types.DATE);

            stmt.setString(3, prazo.getDescricaoBanco());

            stmt.setInt(4, prazo.getIdCordenador());

            stmt.executeUpdate();

            System.out.println("salvo no banco");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PrazoInstitucionalModel> listar() {
        List<PrazoInstitucionalModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM prazoinstitucional";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PrazoInstitucionalModel p = new PrazoInstitucionalModel();
                p.setIdPrazo(rs.getInt("idPrazo"));

                Date dtInicio = rs.getDate("dataInicio");
                if (dtInicio != null)
                    p.setDataInicio(dtInicio.toLocalDate());

                Date dtFim = rs.getDate("dataFinal");
                if (dtFim != null)
                    p.setDataFinal(dtFim.toLocalDate());

                p.setDescricaoBanco(rs.getString("descricao"));

                p.setIdCordenador(rs.getInt("idCoordenador"));

                lista.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void atualizar(PrazoInstitucionalModel prazo) {
        String sql = "UPDATE prazoinstitucional SET dataFinal=?, descricao=? WHERE idPrazo=?";

        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (prazo.getDataFinal() != null)
                stmt.setDate(1, Date.valueOf(prazo.getDataFinal()));
            else
                stmt.setNull(1, Types.DATE);

            stmt.setString(2, prazo.getDescricaoBanco());

            stmt.setInt(3, prazo.getIdPrazo());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletar(int idPrazo) {
        String sql = "DELETE FROM prazoinstitucional WHERE idPrazo=?";
        try (Connection conn = ConexaoMySQL.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPrazo);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}