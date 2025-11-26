package model;

public class CronogramaModel {

      private int idCronogrma;
      private int idAtividade;
      private int idOrientador;
      private int idAluno;
      private int idPrazo;

      public CronogramaModel() {
      }

      public CronogramaModel(int idAluno, int idOrientador, int idAtividade, int idPrazo) {
            this.idAluno = idAluno;
            this.idOrientador = idOrientador;
            this.idAtividade = idAtividade;
            this.idPrazo = idPrazo;

      }

      public int getIdCronograma() {
            return idCronogrma;
      }

      public void setIdCronograma(int idCronograma) {
            this.idCronogrma = idCronograma;
      }

      public int getIdAtividade() {
            return idAtividade;
      }

      public void setIdAtividade(int idAtividade) {
            this.idAtividade = idAtividade;
      }

      public int getIdOrientador() {
            return idOrientador;
      }

      public void setIdOrientador(int idOrientador) {
            this.idOrientador = idOrientador;
      }

      public int getIdAluno() {
            return idAluno;
      }

      public void setIdAluno(int idAluno) {
            this.idAluno = idAluno;
      }

      public int getIdPrazo() {
            return idPrazo;
      }

      public void setIdPrazo(int idPrazo) {
            this.idPrazo = idPrazo;
      }

}