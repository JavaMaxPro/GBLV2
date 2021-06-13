package ru.gb.java2.chat.server.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has been started");

            while (true) {
                waitAndProcessNewClientConnection(serverSocket);
            }

        } catch (IOException e) {
            System.err.println("Failed t bind port " + port);
            e.printStackTrace();
        }
    }

    private void waitAndProcessNewClientConnection(ServerSocket serverSocket) throws IOException {
        System.out.println("Waiting for new client connection");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connect");
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
//        clients.add(clientHandler);
        clientHandler.handle();
    }

    public void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
    }
    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
    }


}
