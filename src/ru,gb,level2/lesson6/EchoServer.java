import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static final int PORT = 8189;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server has been started, waiting for new connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client is connetcted");

            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            processClientConnection(input, output);

        } catch (IOException e) {
            System.err.println("Failed to bind port " + PORT);
            e.printStackTrace();
        }
    }

    private static void processClientConnection(DataInputStream input, DataOutputStream output) {
        while (true) {
            try {
                String message = input.readUTF();
                System.out.println("Received message " + message);
                if (message.equals("/end")) {
                    break;
                }
                output.writeUTF("Эхо: " + message);
            } catch (IOException e) {
                System.out.println("Connection has been closed");
//                    e.printStackTrace();
                break;
            }
        }
    }
}
