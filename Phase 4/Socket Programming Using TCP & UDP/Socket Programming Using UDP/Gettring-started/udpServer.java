import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udpServer {
    public static void main(String[] args) {
        int port = 900;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server is listening on port " + port);

            byte[] buffer = new byte[1024];

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + message);

            System.out.println("Sender IP: " + packet.getAddress());

            System.out.println("Sender Port: " + packet.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}