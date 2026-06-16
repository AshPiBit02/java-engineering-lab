import java.io.IOException;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();

            byte[] sendData = "Hello, server dost!".getBytes();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 9876;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket);
            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received from server jeu: " + serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
