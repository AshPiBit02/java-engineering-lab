import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.rmi.ssl.SslRMIClientSocketFactory;

public class LenServiceServer {
    public static void main(String[] args) {
        int port = 5002;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server starting on port " + port);
            System.out.println("Server waiting for client connection....");

            Socket socket = serverSocket.accept();
            System.out.pritnln("Server connect to " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {
                if (request.equalsIgnoreCase("end")) {
                    System.out.println("Closing Connection....");
                    return;
                }
                System.out.println("Request: " + request);
                String response = String.valueOf(len(request));
                out.println(response);
                System.out.println("Response: " + response);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Connection closed!");
    }

}
