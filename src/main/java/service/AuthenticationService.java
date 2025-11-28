package service;

import dao.UsuarioDAO;
import model.UsuarioModel;

public class AuthenticationService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioModel autenticar(String email, String senha) {
        return usuarioDAO.buscarPorEmailESenha(email, senha);
    }

    private static UsuarioModel usuarioLogado;

    public static void setUsuarioLogado(UsuarioModel usuario) {
        usuarioLogado = usuario;
    }

    public static UsuarioModel getUsuarioLogado() {
        return usuarioLogado;
    }

}

