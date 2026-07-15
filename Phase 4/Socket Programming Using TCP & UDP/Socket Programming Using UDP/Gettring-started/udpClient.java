import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udpClient {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String message = "Hello Server, this is UDP!";

            byte[] data = message.getBytes();

            InetAddress serverAddress = InetAddress.getByName("localhost");

            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, 9000);

            socket.send(packet);
            System.out.println("Message sent: " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
