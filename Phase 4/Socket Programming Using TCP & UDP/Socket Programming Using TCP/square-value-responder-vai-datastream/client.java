import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        int port = 5004;
        String host = "localhost";
        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to Server...");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.print("Request: ");
                int request = Integer.parseInt(sc.next());
                out.writeInt(request);
                out.flush();
                if (request == 0) {
                    System.out.println("Connection closed.");
                    break;
                }
                try {
                    Thread.sleep(500);
                    System.out.println("Server Computing....");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int respond = in.readInt();
                System.out.println("Respoond: " + respond);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
