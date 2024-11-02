package Demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import middleware.Middleware;
import middleware.RoleCheckMiddleware;
import middleware.ThrottlingMiddleware;
import middleware.UserExistsMiddleware;
import server.Server;

// Classe principal que demonstra um sistema de autentica��o com middleware
public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    // M�todo para inicializar o servidor e o middleware
    private static void init() {
        server = new Server();
        // Registro de dois usu�rios no servidor
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // Cria��o da cadeia de middleware para valida��es
        Middleware middleware = Middleware.link(
            new ThrottlingMiddleware(2), // Limita tentativas de login
            new UserExistsMiddleware(server), // Verifica se o usu�rio existe
            new RoleCheckMiddleware() // Verifica as permiss�es do usu�rio
        );

        // Configura o middleware no servidor
        server.setMiddleware(middleware);
    }

    // M�todo principal que executa a aplica��o
    public static void main(String[] args) throws IOException {
        init(); // Inicializa o servidor e o middleware

        boolean success;
        // Loop para solicitar as credenciais at� que o login seja bem-sucedido
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password); // Tenta fazer login
        } while (!success); // Continua at� o login ser bem-sucedido
    }
}