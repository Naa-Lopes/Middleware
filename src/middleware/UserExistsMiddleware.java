package middleware;

import server.Server;

// Ela verifica se o email de um usuario est� registrado e se a senha � v�lida, caso contr�rio, exibe mensagens de erro e retorna false.
//Se as verifica��es passarem, chama o pr�ximo middleware na cadeia.

public class UserExistsMiddleware extends Middleware {
    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return checkNext(email, password);
    }
}