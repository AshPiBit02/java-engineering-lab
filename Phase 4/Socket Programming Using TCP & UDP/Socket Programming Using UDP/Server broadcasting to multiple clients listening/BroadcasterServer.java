import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcasterServer {
    public static void main(String[] args) {
        int port = 9003;
        try (DatagramSocket socket = new DatagramSocket(null)) {
            socket.setReuseAddress(true);
            socket.bind(new java.net.InetSocketAddress(port));
            socket.setBroadcast(true);
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");

            String message = "This is just a demo that demonstrate the broadcasting of data packets from server to client listeners.";
            String[] words = message.split(" ");

            System.out.println("Server Broadcasting to port " + port + ".....");

            for (String word : words) {
                byte[] data = word.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, broadcastAddress, port);
                socket.send(packet);
                System.out.println("Broadcast: " + word);
                Thread.sleep(900);
            }
            byte[] endData = "END".getBytes();
            DatagramPacket endPacket = new DatagramPacket(endData, endData.length, broadcastAddress, port);
            socket.send(endPacket);
            System.out.println("Broadcast: END");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
