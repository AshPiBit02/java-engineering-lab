import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        int port = 5001;
        String host = "localhost";
        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to Server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String services = in.readLine();
            System.out.println(services);
            while (true) {
                System.out.print("Client Request: ");
                String request = sc.nextLine();

                if (request.equalsIgnoreCase("end")) {
                    System.out.println("Connection Closed.");
                    break;
                }
                out.println(request);

                String respond = in.readLine();
                System.out.println(respond);
                System.out.println("-".repeat(70));
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
