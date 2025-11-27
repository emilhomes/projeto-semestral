package service;

import dao.UsuarioDAO;
import model.UsuarioModel;

public class AuthenticationService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioModel autenticar(String email, String senha) {
        return usuarioDAO.buscarPorEmailESenha(email, senha);
    }
}

