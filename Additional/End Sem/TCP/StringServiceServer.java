import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class StringServiceServer {
    public static void main(String[] args) {
        int port = 5000;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Server start on port: " + port);
            System.out.println("Waiting for Client...");

            Socket socket = serverSocket.accept();

            System.out.println("Client connected: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Client: " + line);
                if (line.equalsIgnoreCase("end")) {
                    out.println("Good Night!");
                    break;
                }
                out.println("Server: " + line.toUpperCase());
            }
            socket.close();
            System.out.println("Connection closed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
