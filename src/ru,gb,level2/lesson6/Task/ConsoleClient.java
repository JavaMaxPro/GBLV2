package Task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {

    public static final int PORT_SERVER = 8289;
    public static final String HOST_SERVER = "localhost";
    private static Socket socketClient;

    public static void main(String[] args) {
        try {
            connection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connection() throws IOException {
        socketClient = new Socket(HOST_SERVER, PORT_SERVER);
        DataInputStream inputStream = new DataInputStream(socketClient.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(socketClient.getOutputStream());

        processConnectionServer(inputStream, outputStream);
    }

    private static void processConnectionServer(DataInputStream inputStream, DataOutputStream outputStream) {
        Thread threadIn = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String msgServer = inputStream.readUTF();
                        System.out.println("Client : " + msgServer);
                        if (msgServer.equals("/end")) {
                            break;
                        }
                    } catch (IOException e) {
                        close();
                        System.out.println("Connection has been closed");
                    }
                }
            }
        });

        Thread threadOut = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Scanner scanner = new Scanner(System.in);
                    String msgOut = scanner.nextLine();

                    if (!msgOut.trim().isEmpty()) {
                        try {
                            outputStream.writeUTF(msgOut);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Message not send");
                        }
                    }
                }
            }
        });

        threadIn.start();
        threadOut.start();

    }

    public static void close() {
        try {
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
