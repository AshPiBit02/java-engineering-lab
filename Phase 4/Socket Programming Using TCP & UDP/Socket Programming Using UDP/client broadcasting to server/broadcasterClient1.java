import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class broadcasterClient1 {
    public static void main(String[] args) {
        int port = 9001;
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            String message = "This is a stream of individual words sent one by one by client1";
            String[] words = message.split(" ");
            for (String word : words) {
                byte[] data = word.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
                socket.send(packet);
                System.out.println("Sent: " + word);
                Thread.sleep(750);
            }

            byte[] endData = "END".getBytes();
            DatagramPacket endPacket = new DatagramPacket(endData, endData.length, serverAddress, port);
            socket.send(endPacket);
            System.out.println("Sent: END");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
