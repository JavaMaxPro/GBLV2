package ru.gb.java2.chat.server.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private MyServer server;
    private final Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public ClientHandler(MyServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());
        new Thread(() -> {
            try {
                server.subscribe(this);
                readMessages();
            } catch (IOException e) {
                System.err.println("Failed to process message from client");
//                e.printStackTrace();
            } finally {
                try {
                    server.unsubscribe(this);
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection");
//                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void closeConnection() throws IOException {
        clientSocket.close();
        ;
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = inputStream.readUTF().trim();
            System.out.println("message " + message);

            if (message.startsWith("/end")) {
                return;
            } else {
                processMessage(message);
            }

        }

    }

    private void processMessage(String message) throws IOException {
        server.broadcastMessage(message, this);
    }

    public void sendMessage(String message) throws IOException {
        outputStream.writeUTF(message);
    }
}
