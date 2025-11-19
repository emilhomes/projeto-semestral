package br.com.projetosemestral.model;

public class Usuario {
    
    private int idUsuario;
    private String nome;
    private String emailInstitucional;
    private String senha;
    private TipoUsuario tipoUsuario;

    public Usuario(int id, String n, String e, String s, TipoUsuario tp) {
        this.nome = n;
        this.emailInstitucional = e;
        this.senha = s;
    }

    public int getId() {
        return this.idUsuario;
    }

    public void setNome(String n) {
        this.nome = n;
    } 

    public String getNome() {
        return nome;
    }

    public void setEmail(String e) {
        this.emailInstitucional = e;
    }

    public String getEmail() {
        return emailInstitucional;
    }

    public void setSenha(String s) {
        this.senha = s;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setTipoUsuario(TipoUsuario tp) {
        this.tipoUsuario = tp;
    }

    public TipoUsuario geTipoUsuario() {
        return tipoUsuario;
    }

    //método para autenticar credenciais
    public boolean autenticarCredenciais(String emailDigitado, String senhaDigitada) {
        if(this.emailInstitucional.equals(emailDigitado) && this.senha.equals(senhaDigitada)) {
            return true;
        } else {
            return false;
        }
    }

    public void atualizarPerfil(String novoNome, String novaSenha) {
        this.nome = novoNome;
        this.senha = novaSenha;
    }

    public void visualizarNotificacoes() {
        System.out.println("Notificações ainda vão ser implementadas.");
    }
}