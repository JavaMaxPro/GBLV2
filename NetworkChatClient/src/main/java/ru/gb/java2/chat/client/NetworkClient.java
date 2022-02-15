package ru.gb.java2.chat.client;

import ru.gb.java2.chat.clientserver.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkClient {
    public static NetworkClient INSTANCE;

    public static final int SERVER_PORT = 8182;
    public static final String SERVER_HOST = "localhost";


    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream socketInput;
    private ObjectOutputStream socketOutput;

    private List<ReadCommandListener> listeners = new CopyOnWriteArrayList<>();
    private Thread readMessageProcess;
    private boolean connected;


    public NetworkClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NetworkClient() {
        this(SERVER_HOST, SERVER_PORT);
    }

    public static NetworkClient getNetwork() {
        return  NetworkClient.getInstance();
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socketOutput = new ObjectOutputStream(socket.getOutputStream());
            socketInput = new ObjectInputStream(socket.getInputStream());
            readMessageProcess = startReadMessageProcess();
            connected=true;
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

    public static NetworkClient getInstance(){
        if(INSTANCE == null){
            INSTANCE = new NetworkClient();
        }
        return INSTANCE;
    }
    public void sendCommand(Command command) {
        try {
            socketOutput.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed send message to server");
        }
    }

//    public void waitMessages(Consumer<Command> messageHandler) {
//        Thread thread = new Thread(() -> {
//            while (true) {
//                try {
//                    if (Thread.currentThread().isInterrupted()) {
//                        break;
//                    }
//                    Command command = readCommand();
//                    messageHandler.accept(command);
//                } catch (IOException e) {
////                    e.printStackTrace();
//                    System.err.println("Failed to read message from server");
//                    break;
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//    }

    private Thread startReadMessageProcess() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                    Command command = readCommand();
                    if (command == null) {
                        continue;
                    }
                    for (ReadCommandListener messageListener : listeners) {
                        messageListener.processReceivedCommand(command);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to read message from server");
                    close();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
        return thread;
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
    public ReadCommandListener addReadMessageListener(ReadCommandListener listener) {
        listeners.add(listener);
        return listener;
    }

    public boolean isConnected() {
        return connected;
    }
}
