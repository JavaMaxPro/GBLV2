package ru.gb.java2.chat.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.gb.java2.chat.client.ClientChat;
import ru.gb.java2.chat.client.NetworkClient;

import java.io.IOException;
import java.util.function.Consumer;

public class AuthController {
    public static final String INVALID_CREDENTIALS = "Неккоретный ввод данных";
    public static final String CREDENTIALS_REQUIRED = "Логин и пароль должны быть указаны";

    public static final String AUTH_COMMAND = "/auth";
    public static final String AUTH_OK_COMMAND = "/authOk";
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
        if(login == null || login.isBlank() || password == null || password.isBlank()){
            clientChat.authErrorDialog(INVALID_CREDENTIALS, CREDENTIALS_REQUIRED);
            return;
        }

        String authCommandMessage = String.format("%s %s %s",AUTH_COMMAND,login,password);
        System.out.println(AUTH_COMMAND);
        try {
            network.sendMessage(authCommandMessage);
            System.out.println("Команда отправлена");
        } catch (IOException e) {
            clientChat.showNetworkErrorDialog("Ошибка передачи данных по сети", "Не удалось  отправить сообщение");
            e.printStackTrace();
        }
    }


    public void setClientChat(ClientChat clientChat) {
        this.clientChat = clientChat;
    }
    public void setNetwork(NetworkClient network) {
        this.network = network;
        network.waitMessages(new Consumer<String>() {
            @Override
            public void accept(String message) {
                if(message.startsWith(AUTH_OK_COMMAND)){
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
