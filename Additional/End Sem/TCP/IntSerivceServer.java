import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IntSerivceServer {
    public static void main(String[] args) {
        int port = 5001;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP server start at port " + port);
            System.out.println("Waiting for client to connect....");

            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {
                if (request.equalsIgnoreCase("end")) {
                    System.out.println("Server closing connection....");
                }
                System.out.println("Client: " + request);
                String response = new StringBuilder(request).reverse().toString();
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Connection closed!");
    }

}
