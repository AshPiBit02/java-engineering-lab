import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        int port = 5004;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server Status: Running");
            System.out.println("Waiting for connection.....");
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Connection established with " + clientSocket.getInetAddress());

                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());

                int request;
                while ((request = in.readInt()) != 0) {
                    int respond = request * request;
                    out.writeInt(respond);
                    out.flush();
                }
                System.out.println("Client closed the connection!");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}