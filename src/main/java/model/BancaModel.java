package model;


import java.time.LocalDate;


public class BancaModel {

      private int idBanca;
      private LocalDate dataDefesa;
      private String menbros;
      private int idOrientador;

      public BancaModel() {
      }

      public BancaModel(LocalDate dataDefesa, String menbros, int idOrientador) {
            this.dataDefesa = dataDefesa;
            this.menbros = menbros;
            this.idOrientador = idOrientador;
      }

      public int getIdBanca() {
            return idBanca;
      }

      public void setIdBanca(int idBanca) {
            this.idBanca = idBanca;
      }

      public LocalDate getDataDefesa() {
            return dataDefesa;
      }

      public void setDataDefesa(LocalDate dataDefesa) {
            this.dataDefesa = dataDefesa;
      }

      public String getMenbros() {
            return menbros;
      }

      public void setMenbros(String menbros) {
            this.menbros = menbros;
      }

      public int getIdOrientador() {
            return idOrientador;
      }

      public void setIdOrientador(int idOrientador) {
            this.idOrientador = idOrientador;
      }

}
