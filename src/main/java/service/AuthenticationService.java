package service;

public class AuthenticationService {
    
    public boolean autenticar(String email, String senha) {
        return email.equals("aluno@alu.uern.br") && senha.equals("1234");
    }

}
