package ru.gb.java2.chat.server.chat;

import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.clientserver.CommandType;
import ru.gb.java2.chat.clientserver.commands.AuthCommandData;
import ru.gb.java2.chat.clientserver.commands.PrivateMessageCommandData;
import ru.gb.java2.chat.clientserver.commands.PublicMessageCommandData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {

    private final MyServer server;
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public String getUsername() {
        return username;
    }

    private String username;

    public ClientHandler(MyServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                System.err.println("" +
                        " to process message from client");
                e.printStackTrace();
            } finally {
                try {
                    server.unsubscribe(this);
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void authentication() throws IOException {
        while (true) {
            System.out.println("тут");
            Command command = readCommand();
            if (command == null) {
                System.out.println("auth null");
                continue;
            }
            System.out.println(command.getType()+"аутефикация");
            if(command.getType() == CommandType.AUTH){
                AuthCommandData data = (AuthCommandData) command.getData();
                String login = data.getLogin();
                System.out.println(login);
                String password =data.getPassword();
                System.out.println(password);

                String username = server.getAuthService().getUsernameByLoginAndaPassword(login, password);
                if (username == null) {
                    sendCommand(Command.errorCommand("Неккоректные логин и пароль!"));
                    System.out.println("Неккоректные логин и пароль!");
                } else {
                    if (server.isUsernameBusy(username)) {
                        sendCommand(Command.errorCommand("Такой юзер уже существует!"));
                        System.out.println("Такой юзер уже существует!");
                    } else {
                        this.username = username;
                        sendCommand(Command.authOKCommand(username));
                        System.out.println("AUTH_OK_COMMAND");
                        server.subscribe(username, this);
                        return;
                    }
                }
            }
        }

    }



    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to read Command class");
            e.printStackTrace();
        }
        System.out.println(command.getType()+" зашел в команд");
        return command;
    }

    private void closeConnection() throws IOException {
        clientSocket.close();
        ;
    }

    private void readMessages() throws IOException {
        while (true) {
            System.out.println("жду команду ");
            Command command = readCommand();
            System.out.println("Читаю команду " + command.getType());
            if (command == null) {
                continue;
            }

            switch (command.getType()){
                case END :
                    return;
                case PRIVATE_MESSAGE:{
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String recipient = data.getReceiver();
                    String privateMessage = data.getMessage();
                    server.sendPrivateMessage(this,recipient,privateMessage);
                    break;
                }
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                    processMessage(data.getMessage());
                    break;
                }
            }
        }
    }

    private void processMessage(String message) throws IOException {
        server.broadcastMessage(message, this);
    }


    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }
}
