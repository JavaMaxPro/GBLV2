package ru.gb.java2.chat.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.gb.java2.chat.client.ClientChat;
import ru.gb.java2.chat.client.NetworkClient;
import ru.gb.java2.chat.client.ReadCommandListener;
import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.clientserver.CommandType;
import ru.gb.java2.chat.clientserver.commands.AuthOKCommandData;

public class AuthController {
    public static final String INVALID_CREDENTIALS = "Неккоретный ввод данных";
    public static final String CREDENTIALS_REQUIRED = "Логин и пароль должны быть указаны";

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authButton;

    private ClientChat clientChat;

    private NetworkClient network;
    private ReadCommandListener readCommandListener;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if(login == null || login.isBlank() || password == null || password.isBlank()){
            clientChat.authErrorDialog(INVALID_CREDENTIALS, CREDENTIALS_REQUIRED);
            return;
        }
        if(!connectToServer()){
            System.out.println("Сервер не подключен");
        }
//        String authCommandMessage = String.format("%s %s %s",AUTH_COMMAND,login,password);
//        System.out.println(AUTH_COMMAND);
        //            network.sendMessage(authCommandMessage);
        network.sendCommand(Command.authCommand(login,password));;
        System.out.println("Команда отправлена "+ login + password);
    }

    public boolean connectToServer(){
        NetworkClient networkClient = NetworkClient.getNetwork();
        return networkClient.isConnected() || networkClient.connect();
    }

    public void setClientChat(ClientChat clientChat) {
        this.clientChat = clientChat;
    }
//    public void setNetwork(NetworkClient network) {
//        this.network = network;
//        network.waitMessages(new Consumer<Command>() {
//            @Override
//            public void accept(Command command) {
//                if(command.getType()== CommandType.AUTH_OK){
//                    Thread.currentThread().interrupt();
//                    Platform.runLater(()->{
//                        clientChat.getAuthStage().close();
//                    });
//                }else {
//                    Platform.runLater(()->{
//                        clientChat.showNetworkErrorDialog(INVALID_CREDENTIALS,"Пользователя с таким логином и паролем не существет");
//                    });
//                }
//            }
//        });
//    }
public void setNetwork(NetworkClient network) {
    this.network = network;
    readCommandListener = network.addReadMessageListener(new ReadCommandListener() {
        @Override
        public void processReceivedCommand(Command command) {
            if (command.getType() == CommandType.AUTH_OK) {
                AuthOKCommandData data = (AuthOKCommandData) command.getData();
                String username = data.getUsername();
                Platform.runLater(() -> clientChat.getAuthStage().close());
            } else {
                Platform.runLater(()->{
                        clientChat.showNetworkErrorDialog(INVALID_CREDENTIALS,"Пользователя с таким логином и паролем не существет");
                    });
            }
        }
    });
}
}
