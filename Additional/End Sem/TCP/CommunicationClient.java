import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CommunicationClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int port = 5004;
        String host = "localhost";
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to Server....");
            System.out.println("Connected to server on port " + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                System.out.print("Enter Request('end' to exit): ");
                String request = sc.nextLine();
                out.println(request);
                if (request.equalsIgnoreCase("end")) {
                    break;
                }
                String response = in.readLine();
                System.out.println("Server: " + response);
            }
            System.out.println("Connection Closed!");
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
