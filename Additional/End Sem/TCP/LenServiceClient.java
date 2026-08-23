import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class LenServiceClient {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String host = "localhost";
        int port = 5002;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Client connecting to Server....");
            System.out.println("Client connected to server at port " + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Enter string to get its length('end' to close connection)");
            while (true) {
                System.out.print("Client: ");
                String request = sc.nextLine();
                if (request.equalsIgnoreCase("end")) {
                    System.out.println("Closing Connection....");
                    return;
                }
                out.println(request);
                String response = in.readLine();
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Client disconnect from server!");
    }
}
