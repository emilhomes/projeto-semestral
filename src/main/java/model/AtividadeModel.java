package model;

import javax.xml.crypto.Data;

public class AtividadeModel {

      private int idAtividade;
      private String descricao;
      private Data dataInicio;
      private Data dataFim;
      private String estado;

      public AtividadeModel() {
      }

      public AtividadeModel(String descricao, Data dataInicio, Data dataFim, String estado) {
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

      public Data getDataInicio() {
            return dataInicio;
      }

      public void setDataInicio(Data dataInicio) {
            this.dataInicio = dataInicio;
      }

      public Data getDataFim() {
            return dataFim;
      }

      public void setDataFim(Data dataFim) {
            this.dataFim = dataFim;
      }

      public String getEstado() {
            return estado;
      }

      public void setEstado(String estado) {
            this.estado = estado;
      }
}
