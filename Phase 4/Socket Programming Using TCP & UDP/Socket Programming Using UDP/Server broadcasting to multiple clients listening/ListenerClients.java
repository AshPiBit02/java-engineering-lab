import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class ListenerClients {
    public static void main(String[] args) throws InterruptedException {
        int port = 9003;
        Thread[] clients = new Thread[7];
        for (int i = 0; i < 7; i++) {
            clients[i] = new Thread(() -> {
                try (DatagramSocket socket = new DatagramSocket(null)) {
                    socket.setReuseAddress(true);
                    socket.bind(new java.net.InetSocketAddress(port));

                    System.out.println(Thread.currentThread().getName() + " listening for broadcasts on port " + port);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);

                        String word = new String(packet.getData(), 0, packet.getLength());

                        if (word.equalsIgnoreCase("END")) {
                            break;
                        }
                        System.out.println(Thread.currentThread().getName() + " heard: " + word);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Client1XX" + i);
        }

        for (Thread client : clients) {
            client.start();
        }
        for (Thread client : clients) {
            client.join();
        }
        System.out.println("End of broadcast.");

    }
}
