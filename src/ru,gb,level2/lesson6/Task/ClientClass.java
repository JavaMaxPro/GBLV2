package Task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientClass {
    private static final int PORT_SERVER = 8289;
    private static final String HOST_SERVER = "localhost";
    private  Socket socketClient;
    private final String host;
    private final int port;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Scanner scanner;

    public ClientClass(String host , int port) throws IOException {
        this.host=host;
        this.port=port;
    }

    public ClientClass() throws IOException {
        this(HOST_SERVER,PORT_SERVER);
    }

    public   void openConnection() throws IOException {
        socketClient = new Socket(HOST_SERVER, PORT_SERVER);
        inputStream = new DataInputStream(socketClient.getInputStream());
        outputStream = new DataOutputStream(socketClient.getOutputStream());

        new Thread(() -> {
            while (true) {
                try {
                    String msgServer = inputStream.readUTF();
                    System.out.println("Client : " + msgServer);
                    if (msgServer.equalsIgnoreCase("/end")) {
                        closeConnection();
                        System.out.println("Соединение закрыто");
                        return;
                    }
                } catch (IOException e) {
                    System.out.println("Connection has been closed");
                }
            }
        }).start();
    }

    public void sendMsg() {
         new Thread(() -> {
            while (true) {
                scanner = new Scanner(System.in);
                String msgOut = scanner.nextLine();

                if (!msgOut.trim().isEmpty()) {
                    try {
                        outputStream.writeUTF(msgOut);
                        if(msgOut.equalsIgnoreCase("/end")) closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Message not send");
                    }
                }
            }
        }).start();

    }

    public  void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
