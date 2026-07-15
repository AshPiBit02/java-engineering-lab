import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        int port = 5005;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server Status: Running");
            System.out.println("Waiting for client to connect");

            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Connection established with " + clientSocket.getLocalAddress());
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

                int IntegerStreamSize = in.readInt();
                int sumRespond = 0;
                while (IntegerStreamSize > 0) {
                    sumRespond += in.readInt();
                    IntegerStreamSize--;
                }
                out.writeInt(sumRespond);
                System.out.println("Connection Closed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
