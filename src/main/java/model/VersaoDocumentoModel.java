package model;

import javax.xml.crypto.Data;

public class VersaoDocumentoModel {

      private int idVersao;
      private String nomeArquivo;
      private Data dataEnvio;
      private int idComentario;

      public VersaoDocumentoModel() {
      }

      public VersaoDocumentoModel(String nomeArquivo, Data dataEnvio, int idComentario) {
            this.nomeArquivo = nomeArquivo;
            this.dataEnvio = dataEnvio;
            this.idComentario = idComentario;
      }

      public int getIdVersao() {
            return idVersao;
      }

      public void setIdVersao(int idVersao) {
            this.idVersao = idVersao;
      }

      public String getNomeArquivo() {
            return nomeArquivo;
      }

      public void setNomeArquivo(String nomeArquivo) {
            this.nomeArquivo = nomeArquivo;
      }

      public Data getDataEnvio() {
            return dataEnvio;
      }

      public void setDataEnvio(Data dataEnvio) {
            this.dataEnvio = dataEnvio;
      }

      public int getIdComentario() {
            return idComentario;
      }

      public void setIdComentario(int idComentario) {
            this.idComentario = idComentario;
      }

}