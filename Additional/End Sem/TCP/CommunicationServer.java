import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.ServerSocket;

public class CommunicationServer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int port = 5004;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("TCP Server started on port: " + port);
            System.out.println("Waiting for client to connect....");

            Socket socket = serverSocket.accept();
            System.out.println("Connected to " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String request;
            while ((request = in.readLine()) != null) {
                if (request.equalsIgnoreCase("end")) {
                    out.println("Server: Thankyou for using our service! Visit Again!");
                    break;
                }
                System.out.println("Client: " + request);
                System.out.print("Response: ");
                String response = sc.nextLine();
                out.println("Server: " + response);
            }
            System.out.println("Connection Closed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
