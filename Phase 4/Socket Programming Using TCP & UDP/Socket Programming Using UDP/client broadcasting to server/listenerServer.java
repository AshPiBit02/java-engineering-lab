import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class listenerServer {
    public static void main(String[] args) {
        int port = 9001;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server listening on port " + port);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String word = new String(packet.getData(), 0, packet.getLength());
                if (word.equalsIgnoreCase("END")) {
                    System.out.println("End signal received. Stoppign server.");
                    break;
                }
                System.out.println("Received word: " + word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
