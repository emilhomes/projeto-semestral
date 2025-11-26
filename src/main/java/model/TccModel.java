package model;

import javax.xml.crypto.Data;

public class TccModel {

      private int idTcc;
      private String titulo;
      private String resumo;
      private String estado;
      private Data dataCadastro;
      private int idAluno;
      private int idBanca;
      private int idVersao;
      private int idOrientador;

      public TccModel() {
      }

      public TccModel(String titulo, String resumo, String estado, Data dataCadastro, int idAluno, int idBanca,
                  int idVersao, int idOrientador) {
            this.titulo = titulo;
            this.resumo = resumo;
            this.estado = estado;
            this.dataCadastro = dataCadastro;
            this.idAluno = idAluno;
            this.idBanca = idBanca;
            this.idVersao = idVersao;
            this.idOrientador = idOrientador;
      }

      public int getIdTcc() {
            return idTcc;
      }

      public void setIdTcc(int idTcc) {
            this.idTcc = idTcc;
      }

      public String getTitulo() {
            return titulo;
      }

      public void setTitulo(String titulo) {
            this.titulo = titulo;
      }

      public String getResumo() {
            return resumo;
      }

      public void setResumo(String resumo) {
            this.resumo = resumo;
      }

      public String getEstado() {
            return estado;
      }

      public void setEstado(String estado) {
            this.estado = estado;
      }

      public Data getDataCadastro() {
            return dataCadastro;
      }

      public void setDataCadastro(Data dataCadastro) {
            this.dataCadastro = dataCadastro;
      }

      public int getIdAluno() {
            return idAluno;
      }

      public void setIdAluno(int idAluno) {
            this.idAluno = idAluno;
      }

      public int getIdBanca() {
            return idBanca;
      }

      public void setIdBanca(int idBanca) {
            this.idBanca = idBanca;
      }

      public int getIdVersao() {
            return idVersao;
      }

      public void setIdVersao(int idVersao) {
            this.idVersao = idVersao;
      }

      public int getIdOrientador() {
            return idOrientador;
      }

      public void setIdOrientador(int idOrientador) {
            this.idOrientador = idOrientador;
      }

}
