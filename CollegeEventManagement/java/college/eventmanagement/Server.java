

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 8080;
    private final AuthService authService = new AuthService();

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/auth/register", this::handleRegister);
        server.createContext("/auth/login", this::handleLogin);
        server.start();
        System.out.println("Auth server running on http://localhost:" + PORT);
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> body = readForm(exchange);
            boolean ok = authService.registerStudent(
                    body.getOrDefault("name", ""),
                    body.getOrDefault("rollNumber", ""),
                    body.getOrDefault("department", ""),
                    Integer.parseInt(body.getOrDefault("year", "0")),
                    body.getOrDefault("email", ""),
                    body.getOrDefault("phone", ""),
                    body.getOrDefault("password", "")
            );
            sendJson(exchange, 200, ok ? "{\"success\":true}" : "{\"success\":false,\"message\":\"Registration failed\"}");
        } catch (SQLException ex) {
            sendJson(exchange, 500, "{\"success\":false,\"message\":\"" + escape(ex.getMessage()) + "\"}");
        } catch (Exception ex) {
            sendJson(exchange, 400, "{\"success\":false,\"message\":\"Invalid request\"}");
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        try {
            Map<String, String> body = readForm(exchange);
            boolean ok = authService.loginStudent(body.getOrDefault("email", ""), body.getOrDefault("password", ""));
            sendJson(exchange, 200, ok ? "{\"success\":true}" : "{\"success\":false,\"message\":\"Invalid credentials\"}");
        } catch (SQLException ex) {
            sendJson(exchange, 500, "{\"success\":false,\"message\":\"" + escape(ex.getMessage()) + "\"}");
        } catch (Exception ex) {
            sendJson(exchange, 400, "{\"success\":false,\"message\":\"Invalid request\"}");
        }
    }

    private Map<String, String> readForm(HttpExchange exchange) throws IOException {
        Map<String, String> values = new HashMap<>();
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }
        String[] pairs = body.toString().split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                values.put(java.net.URLDecoder.decode(kv[0], StandardCharsets.UTF_8), java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }
        return values;
    }

    private void sendJson(HttpExchange exchange, int status, String payload) throws IOException {
        byte[] bytes = payload.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private String escape(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
