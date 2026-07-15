import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class servcler {
    public static void main(String[] args) {
        int port = 5001;
        Scanner sc = new Scanner(System.in);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            try {
                for (int i = 3; i >= 0; i--) {
                    Thread.sleep(750);
                    System.out.printf("\rStarting TCP Server in %d", i);
                }
                System.out.printf("\r%s", "");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("TCP server started on port: " + port);
            System.out.println("Waiting for client...");

            Socket socket = serverSocket.accept();

            System.out.println("Client connected: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Client Resquest: " + line);
                System.out.print("Respond: ");
                String respond = sc.nextLine();

                if (line.equalsIgnoreCase("end")) {
                    System.out.print("Bye Message to Client: ");
                    String byeMsg = sc.nextLine();
                    out.println("Server: " + byeMsg);
                    break;
                }
                out.println("Serve: " + respond);
            }
            socket.close();
            System.out.println("Connection Closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
