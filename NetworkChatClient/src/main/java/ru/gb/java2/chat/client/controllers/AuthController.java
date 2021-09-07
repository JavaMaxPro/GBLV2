package ru.gb.java2.chat.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.gb.java2.chat.client.ClientChat;
import ru.gb.java2.chat.client.NetworkClient;
import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.clientserver.CommandType;

import java.util.function.Consumer;

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

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if(login == null || login.isEmpty() || password == null || password.isEmpty()){
            clientChat.authErrorDialog(INVALID_CREDENTIALS, CREDENTIALS_REQUIRED);
            return;
        }
//        String authCommandMessage = String.format("%s %s %s",AUTH_COMMAND,login,password);
//        System.out.println(AUTH_COMMAND);
        //            network.sendMessage(authCommandMessage);
        network.sendCommand(Command.authCommand(login,password));
        System.out.println("Команда отправлена");
    }


    public void setClientChat(ClientChat clientChat) {
        this.clientChat = clientChat;
    }
    public void setNetwork(NetworkClient network) {
        this.network = network;
        network.waitMessages(new Consumer<Command>() {
            @Override
            public void accept(Command command) {
                if(command.getType()== CommandType.AUTH){
                    Thread.currentThread().interrupt();
                    Platform.runLater(()->{
                        clientChat.getAuthStage().close();
                    });
                }else {
                    Platform.runLater(()->{
                        clientChat.showNetworkErrorDialog(INVALID_CREDENTIALS,"Пользователя с таким логином и паролем не существет");
                    });
                }
            }
        });
    }

}
