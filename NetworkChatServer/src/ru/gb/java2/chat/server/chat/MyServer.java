package ru.gb.java2.chat.server.chat;

import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.server.chat.auth.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();
    private final Map<String,ClientHandler> clientsUser = new HashMap<>();


    private AuthService authService;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has been started");
            authService = new AuthService();

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
    //    clients.add(clientHandler);
        clientHandler.handle();
    }

    public void broadcastMessage(String message, ClientHandler sender) throws IOException {
        // client.sendMessage(message);
            for (ClientHandler client : clients)
                if (client != sender) {
                    client.sendCommand(Command.clientMessageCommand(sender.getUsername(),message));
                    System.out.println(client);
                }
    }

    public synchronized boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender && client.getUsername().equals(recipient)) {
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), privateMessage));
                break;
            }
        }
    }

    public void subscribe(String username, ClientHandler clientHandler) {
        clients.add(clientHandler);
        clientsUser.put(username,clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }


}
