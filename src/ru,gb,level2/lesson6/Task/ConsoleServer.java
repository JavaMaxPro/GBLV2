package Task;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {

    public static final int PORT = 8289;
    private static Socket socketClient;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен ожидаем соединения");

            socketClient = serverSocket.accept();
            System.out.println("Клиент подкючен");

            inputStream = new DataInputStream(socketClient.getInputStream());
            outputStream = new DataOutputStream(socketClient.getOutputStream());

            processConnection(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processConnection(DataInputStream inputStream, DataOutputStream outputStream) {
        Thread threadIn = new Thread(() -> {
            while (true) {
                try {
                    String msg = inputStream.readUTF();
                    System.out.println("Client : " + msg);
                    if (msg.equalsIgnoreCase("/end")) {
                        socketClient.close();
                        return;
                    }
                } catch (IOException e) {
                    try {
                        socketClient=serverSocket.accept();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.out.println("Connection has been closed");
                }
            }
        });

        Thread threadOut = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String msgOut = scanner.nextLine();

                if(!msgOut.trim().isEmpty()) {
                    try {
                        outputStream.writeUTF(msgOut);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Message not send");
                    }
                }
            }
        });

        threadIn.start();
        threadOut.start();

    }

}
