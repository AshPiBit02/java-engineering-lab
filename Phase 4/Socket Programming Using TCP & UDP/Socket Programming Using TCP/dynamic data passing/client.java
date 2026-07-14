import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        String host = "localhost";
        int port = 5001;
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to server...");
            try {
                Thread.sleep(rand.nextInt(500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Connected to Server");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            boolean sendMessage = true;
            while (sendMessage) {
                System.out.println("Message to Client: ");
                String message = sc.nextLine();
                out.println(message);

                String response = in.readLine();
                System.out.println("Received: " + response);
                if (message.equalsIgnoreCase("end")) {
                    sendMessage = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
