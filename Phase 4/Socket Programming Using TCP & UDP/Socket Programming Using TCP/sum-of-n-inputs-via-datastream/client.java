import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int port = 5005;
        String host = "localhost";
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connecting to Server......");
            try {
                Thread.sleep(500);
                System.out.println("Connected to server");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            System.out.print("Enter number of integer to sent for addition to server: ");
            int bufferSize = Integer.parseInt(sc.nextLine());
            out.writeInt(bufferSize);
            int i = 1;
            while (bufferSize > 0) {
                System.out.print("Enter Integer" + i + ": ");
                out.writeInt(Integer.parseInt(sc.nextLine()));
                i++;
                bufferSize--;
            }
            System.out.println();
            System.out.println("Respond: " + in.readInt());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
