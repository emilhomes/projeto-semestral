package model;

import java.time.LocalDateTime;

public class ComentarioModel {

    private int idComentario;
    private String usuario;
    private String conteudo;
    private int idTCC;
    private LocalDateTime data;

    public ComentarioModel() {
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getIdTCC() {
        return idTCC;
    }

    public void setIdTCC(int idTCC) {
        this.idTCC = idTCC;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}