import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenerClients {
    public static void main(String[] args) {
        int port = 9003;
        try (DatagramSocket socket = new DatagramSocket(null)) {
            socket.setReuseAddress(true);
            socket.bind(new java.net.InetSocketAddress(port));

            System.out.println("Client listening for broadcasts on port " + port);
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String word = new String(packet.getData(), 0, packet.getLength());

                if (word.equalsIgnoreCase("END")) {
                    System.out.println("End of broadcast.");
                    break;
                }
                System.out.println("Heard: " + word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
