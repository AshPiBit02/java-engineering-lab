import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9876);
            System.out.println("Server waiting for connections....");

            Socket clienSocket = serverSocket.accept();
            System.out.println("Connection established with " + clienSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
            String clientMessage = in.readLine();
            System.out.println("Received from client: " + clientMessage);

            PrintWriter out = new PrintWriter(clienSocket.getOutputStream(), true);
            out.println("Hello, client dost!");

            in.close();
            out.close();
            clienSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
