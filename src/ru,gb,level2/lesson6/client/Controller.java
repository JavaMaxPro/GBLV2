package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
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
        if(message.isEmpty()){
            messageTextArea.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = new String(String.valueOf(userList.getSelectionModel().getSelectedItems()));
            chatTextArea.appendText(sender + " : ");
        }

        try {
            message = sender != null ? String.join(": ", sender,message) : message;
            networkClient.sendMessage(message);
        } catch (IOException e) {
//            e.printStackTrace();
            application.showNetworkErrorDialog("Ошибка передачи данных по сети", "Не удалось  отправить сообщение");
        }
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

        this.networkClient = networkClient;
        networkClient.waitMessages(message -> Platform.runLater(() -> {
            Controller.this.appendMessageToChat("Server", message);
        }));
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }
}
