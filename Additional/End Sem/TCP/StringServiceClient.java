import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class StringServiceClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = "localhost";
        int port = 5000;
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to server...");
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                System.out.print("Input string to get uppercase('end' to close connection): ");
                String input = sc.nextLine();

                out.println(input);
                String response = in.readLine();
                System.out.println("Server: " + response);

                if (input.equalsIgnoreCase("end")) {
                    break;
                }
            }
            System.out.println()
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }
}