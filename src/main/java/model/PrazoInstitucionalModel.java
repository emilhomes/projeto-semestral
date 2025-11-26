package model;

import javax.xml.crypto.Data;

public class PrazoInstitucionalModel {

      private int idPrazo;
      private Data dataInicio;
      private Data dataFinal;
      private int idCordenador;
      private String descricao;

      public PrazoInstitucionalModel() {
      }

      public PrazoInstitucionalModel(Data dataInicio,Data dataFinal, String descricao, int idCordenador) {
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
      public Data getDataInicio() {
            return dataInicio;
      }
      public void setDataInicio(Data dataInicio) {
            this.dataInicio = dataInicio;
      }

      public Data getDataFinal() {
            return dataFinal;
      }
      public void setDataFinal(Data dataFinal) {
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
