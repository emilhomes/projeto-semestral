package model;

import java.time.LocalDate;


public class PrazoInstitucionalModel {

      private int idPrazo;
      private LocalDate dataInicio;
      private LocalDate dataFinal;
      private int idCordenador;
      private String descricao;

      public PrazoInstitucionalModel() {
      }

      public PrazoInstitucionalModel(LocalDate dataInicio,LocalDate dataFinal, String descricao, int idCordenador) {
            this.dataInicio = dataInicio;
            this.dataFinal = dataFinal;
            this.descricao = descricao;
            this.idCordenador = idCordenador;
      
      }

      public int getIdPrazo() {
            return idPrazo;
      }

      public void setIdPrazo(int idPrazo) {
            this.idPrazo = idPrazo;
      }
      public LocalDate getDataInicio() {
            return dataInicio;
      }
      public void setDataInicio(LocalDate dataInicio) {
            this.dataInicio = dataInicio;
      }

      public LocalDate getDataFinal() {
            return dataFinal;
      }
      public void setDataFinal(LocalDate dataFinal) {
            this.dataFinal = dataFinal;
      }
      public int getIdCordenador() {
            return idCordenador;
      }
      public void setIdCordenador(int idCordenador) {
            this.idCordenador = idCordenador;
      }
      public String getDescricao() {
            return descricao;
      }
      public void setDescricao(String descricao) {
            this.descricao = descricao;
      }

}
