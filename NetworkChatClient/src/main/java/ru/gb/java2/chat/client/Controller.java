package ru.gb.java2.chat.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.clientserver.CommandType;
import ru.gb.java2.chat.clientserver.commands.ClientMessageCommandData;
import ru.gb.java2.chat.clientserver.commands.PrivateMessageCommandData;
import ru.gb.java2.chat.clientserver.commands.PublicMessageCommandData;
import ru.gb.java2.chat.clientserver.commands.UpdateUsersListCommandData;

import java.util.Date;

public class Controller {


    @FXML
    public ListView userList;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button sendMessageButton;

    private NetworkClient networkClient;
    private ClientChat application;

    @FXML
    public void sendMessage() {
        String message = messageTextArea.getText().trim();
        if (message.isEmpty()) {
            messageTextArea.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = new String(String.valueOf(userList.getSelectionModel().getSelectedItems()));
            chatTextArea.appendText(sender + " : ");
        }

        if (sender != null) {
            networkClient.getInstance().sendCommand(Command.privateMessageCommand(sender, message));
        } else {
            networkClient.getInstance().sendCommand(Command.publicMessageCommand(message));
        }
        // networkClient.sendMessage(message);
        appendMessageToChat("Я", message);
    }

    private void appendMessageToChat(String sender, String message) {
        chatTextArea.appendText(String.valueOf(new Date()));
        chatTextArea.appendText(System.lineSeparator());
        if (sender != null) {
            chatTextArea.appendText(sender + " : ");
        }
        chatTextArea.appendText(message);
        chatTextArea.appendText(System.lineSeparator());
        chatTextArea.appendText(System.lineSeparator());
        messageTextArea.clear();
    }

    @FXML
    public void sendMessageAreaP(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            keyEvent.consume();
            if (keyEvent.isShiftDown()) {
                messageTextArea.appendText(System.lineSeparator());
            } else {
                sendMessage();
            }
        }
    }

    public void setNetworkClient(NetworkClient networkClient) {

        NetworkClient.getInstance().addReadMessageListener(new ReadCommandListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    Platform.runLater(() -> ChatController.this.appendMessageToChat(data.getSender(), data.getMessage()));
                } else if (command.getType() == CommandType.UPDATE_USERS_LIST) {
                    UpdateUsersListCommandData data = (UpdateUsersListCommandData) command.getData();
                    updateUsersList(data.getUsers());
                }
            }
        });
    }

    private void commandMessageToChat(Command command) {
        String sender = null;
        String message ;
        switch (command.getType()){
            case PRIVATE_MESSAGE: {
                PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                sender = data.getReceiver();
                message = data.getMessage();
                appendMessageToChat(sender, message);
                break;
            }
            case PUBLIC_MESSAGE: {
                PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                message= data.getMessage();
                appendMessageToChat(sender,message);
                break;
            }
        }
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }
}
