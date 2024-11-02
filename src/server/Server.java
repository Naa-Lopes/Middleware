package server;

import middleware.Middleware;

import java.util.HashMap;
import java.util.Map;


//Essa e classe que faz a autenticação de email e senha dos usuarios, fazendo isso por meio de um hashmap
//Tudo isso passando pela classe de Middleware.

public class Server {
    private Map<String, String> users = new HashMap<>();
    private Middleware middleware;

    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    public boolean logIn(String email, String password) {
        // Chama o método check em vez de checkNext
        if (middleware.check(email, password)) { 
            System.out.println("Authorization has been successful!");
            return true;
        }
        return false;
    }

    public void register(String email, String password) {
        users.put(email, password);
    }

    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password);
    }
}