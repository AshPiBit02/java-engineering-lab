import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;

public class client_theRequester {
    public static void main(String[] args) {
        int port = 5003;
        String host = "localhost";
        Scanner sc = new Scanner(System.in);

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected with Server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                System.out.print("Request: ");
                String request = sc.nextLine();
                if (request.equalsIgnoreCase("end")) {
                    System.out.println("Client connection closed");
                    return;
                }

                out.println(request);
                String respond = in.readLine();
                System.out.println("Respond: " + respond);
                System.out.println("-".repeat(60));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
