import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class IntServiceClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int port = 5001;
        String host = "localhost";
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to server....");
            System.out.println("Connected to server on port " + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                System.out.print("Enter string to get its reverse('end' to exit): ");
                String request = sc.nextLine();
                if (request.equalsIgnoreCase("end")) {
                    System.out.println("Closing connection...");
                    return;
                }
                System.out.println("Client: " + request);
                out.println(request);
                String response = in.readLine();
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Connection Closed!");

    }
}
