package Demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import middleware.Middleware;
import middleware.RoleCheckMiddleware;
import middleware.ThrottlingMiddleware;
import middleware.UserExistsMiddleware;
import server.Server;

// Classe principal que demonstra um sistema de autenticação com middleware
public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    // Método para inicializar o servidor e o middleware
    private static void init() {
        server = new Server();
        // Registro de dois usuários no servidor
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // Criação da cadeia de middleware para validações
        Middleware middleware = Middleware.link(
            new ThrottlingMiddleware(2), // Limita tentativas de login
            new UserExistsMiddleware(server), // Verifica se o usuário existe
            new RoleCheckMiddleware() // Verifica as permissões do usuário
        );

        // Configura o middleware no servidor
        server.setMiddleware(middleware);
    }

    // Método principal que executa a aplicação
    public static void main(String[] args) throws IOException {
        init(); // Inicializa o servidor e o middleware

        boolean success;
        // Loop para solicitar as credenciais até que o login seja bem-sucedido
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password); // Tenta fazer login
        } while (!success); // Continua até o login ser bem-sucedido
    }
}