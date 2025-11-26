package model;

// import javax.xml.crypto.Data;
import java.time.LocalDate;

public class AtividadeModel {

      private int idAtividade;
      private String descricao;
      private LocalDate dataInicio;
      private LocalDate dataFim;
      private String estado;

      public AtividadeModel() {
      }

      public AtividadeModel(String descricao, LocalDate dataInicio, LocalDate dataFim, String estado) {
            this.descricao = descricao;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.estado = estado;
      }

      public int getIdAtividade() {
            return idAtividade;
      }

      public void setIdAtividade(int idAtividade) {
            this.idAtividade = idAtividade;
      }

      public String getDescricao() {
            return descricao;
      }

      public void setDescricao(String descricao) {
            this.descricao = descricao;
      }

      public LocalDate getDataInicio() {
            return dataInicio;
      }

      public void setDataInicio(LocalDate dataInicio) {
            this.dataInicio = dataInicio;
      }

      public LocalDate getDataFim() {
            return dataFim;
      }

      public void setDataFim(LocalDate dataFim) {
            this.dataFim = dataFim;
      }

      public String getEstado() {
            return estado;
      }

      public void setEstado(String estado) {
            this.estado = estado;
      }
}
