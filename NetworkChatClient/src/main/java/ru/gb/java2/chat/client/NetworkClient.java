package ru.gb.java2.chat.client;

import ru.gb.java2.chat.clientserver.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class NetworkClient {

    public static final int SERVER_PORT = 8189;
    public static final String SERVER_HOST = "localhost";

    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream socketInput;
    private ObjectOutputStream socketOutput;

    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NetworkClient() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socketInput = new ObjectInputStream(socket.getInputStream());
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to establish connection");
            return false;
        }
    }

//    public void sendMessage(String message) throws IOException {
//        try {
//            socketOutput.writeUTF(message);
//        } catch (IOException e) {
//            System.err.println("Failed send message to server");
//            throw e;
//        }
//    }


    public void sendCommand(Command command) {
        try {
            socketOutput.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed send message to server");
        }
    }

    public void waitMessages(Consumer<Command> messageHandler) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    Command command = readCommand();
                    messageHandler.accept(command);
                } catch (IOException e) {
//                    e.printStackTrace();
                    System.err.println("Failed to read message from server");
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) socketInput.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to read Command class");
            e.printStackTrace();
        }

        return command;
    }
}
