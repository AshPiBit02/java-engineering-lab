import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server_theResponder {
    public static void main(String[] args) {
        int port = 5003;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server Status: Running");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with " + clientSocket.getLocalPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {
                out.println("Respond: " + request.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
