package model;

import java.time.LocalDate;

public class VersaoDocumentoModel {

      private int idVersao;
      private int idTCC;
      private String nomeArquivo;
      private LocalDate dataEnvio;
      private int idComentario;
      private String textoComentario;

      public VersaoDocumentoModel() {
      }

      public VersaoDocumentoModel(String nomeArquivo, LocalDate dataEnvio, int idComentario) {
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

      public LocalDate getDataEnvio() {
            return dataEnvio;
      }

      public void setDataEnvio(LocalDate dataEnvio) {
            this.dataEnvio = dataEnvio;
      }

      public int getIdComentario() {
            return idComentario;
      }

      public void setIdComentario(int idComentario) {
            this.idComentario = idComentario;
      }
      public int getIdTCC() {
        return idTCC;
    }

    public void setIdTCC(int idTCC) {
        this.idTCC = idTCC;
    }

    public void setTextoComentario(String textoComentario) {
      this.textoComentario = textoComentario;
    }

    public String getTextoComentario() {
      return textoComentario;
    }

}