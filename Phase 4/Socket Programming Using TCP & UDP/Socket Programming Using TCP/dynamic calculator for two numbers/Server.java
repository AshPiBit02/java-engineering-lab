import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 5001;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server Running.....");
            System.out.println("Waiting for connections....");

            Socket clientSocket = serverSocket.accept();

            System.out.println("Connection established with " + clientSocket.getInetAddress());

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            out.println(
                    "Server: Services(add for addition, sub for substraction, mul for multiplication , div for division) & end for exit");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (true) {
                String request = in.readLine();
                String[] parts = request.split(",");
                String operation = parts[0];
                float a = Float.parseFloat(parts[1]);
                float b = Float.parseFloat(parts[2]);
                switch (operation) {
                    case "add":
                        out.printf("Respond: ", a + b);
                        break;
                    case "sub":
                        out.println("Respond: " + (a - b));
                        break;
                    case "mul":
                        out.println("Respond: " + (a * b));
                        break;
                    case "div":
                        try {
                            float div = a / b;
                            out.println("Respond: " + div);
                        } catch (ArithmeticException e) {
                            out.println("Respond: cannot divide by zero!");
                        }
                        break;
                    case "end":
                        return;
                    default:
                        out.println("Respond: Invalid input or Invalid operation!!!");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
